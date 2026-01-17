package com.qianjisan.core.aspect;

import com.qianjisan.core.config.CoreRateLimitProperties;

import com.qianjisan.core.annotation.RateLimit;
import com.qianjisan.core.enums.LimitType;
import com.qianjisan.core.exception.RateLimitException;
import com.qianjisan.core.utils.IpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 限流切面
 * 基于 Redis + Lua 脚本实现分布式限�?
 *
 * @author Diego
 * @since 2024-11-21
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisScript<Long> limitScript;
    private final CoreRateLimitProperties rateLimitProperties;

    @Before("@annotation(rateLimit)")
    public void doBefore(JoinPoint point, RateLimit rateLimit) {
        // 检查是否启用限流功�?
        if (!rateLimitProperties.getEnabled()) {
            log.debug("限流功能已禁�?);
            return;
        }

        // 解析限流参数
        LimitParams params = parseLimitParams(rateLimit);
        int time = params.getTime();
        int count = params.getCount();
        String message = params.getMessage();
        String key = getCombineKey(rateLimit, point);

        try {
            List<String> keys = Collections.singletonList(key);
            Long number = redisTemplate.execute(limitScript, keys, count, time);

            if (number == null || number.intValue() > count) {
                log.warn("限流触发，key={}, 限制次数={}, 时间窗口={}�?, key, count, time);
                throw new RateLimitException(message);
            }

            log.debug("限流校验通过，key={}, 当前次数={}, 限制次数={}", key, number, count);
        } catch (RateLimitException e) {
            throw e;
        } catch (Exception e) {
            log.error("限流异常，key={}", key, e);
            throw new RuntimeException("服务器限流异常，请稍候再�?);
        }
    }

    /**
     * 组合限流key
     */
    private String getCombineKey(RateLimit rateLimit, JoinPoint point) {
        StringBuilder key = new StringBuilder(rateLimit.key());

        // 添加方法�?
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        key.append(targetClass.getName()).append(":").append(method.getName()).append(":");

        // 根据限流类型添加后缀
        LimitType limitType = rateLimit.limitType();
        switch (limitType) {
            case IP:
                key.append(getIpAddress());
                break;
            case USER:
                key.append(getUserId());
                break;
            case DEFAULT:
            default:
                // 默认全局限流
                break;
        }

        return key.toString();
    }

    /**
     * 获取请求IP
     */
    private String getIpAddress() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            return IpUtils.getIpAddr(request);
        }
        return "unknown";
    }

    /**
     * 获取用户ID
     */
    private String getUserId() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            Object userId = request.getAttribute("userId");
            if (userId != null) {
                return userId.toString();
            }
        }
        return "anonymous";
    }

    /**
     * 获取当前请求
     */
    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 解析限流参数
     * 优先级：configKey > 注解参数 > 配置文件默认�?
     */
    private LimitParams parseLimitParams(RateLimit rateLimit) {
        String configKey = rateLimit.configKey();
        int time = rateLimit.time();
        int count = rateLimit.count();
        String message = rateLimit.message();
        LimitType limitType = rateLimit.limitType();

        // 如果指定了configKey，从配置文件的special部分读取
        if (configKey != null && !configKey.isEmpty()) {
            CoreRateLimitProperties.LimitConfig specialConfig = rateLimitProperties.getSpecialConfig(configKey);
            if (specialConfig != null) {
                return new LimitParams(
                    specialConfig.getTime(),
                    specialConfig.getCount(),
                    specialConfig.getMessage()
                );
            } else {
                log.warn("未找到配置key: {}, 将使用注解参数或默认配置", configKey);
            }
        }

        // 根据limitType获取对应的默认配�?
        CoreRateLimitProperties.LimitConfig defaultConfig = getDefaultConfigByType(limitType);

        // 如果注解中未指定参数（值为-1或空字符串），则使用配置文件中的默认�?
        if (time == -1) {
            time = defaultConfig.getTime();
        }
        if (count == -1) {
            count = defaultConfig.getCount();
        }
        if (message == null || message.isEmpty()) {
            message = defaultConfig.getMessage();
        }

        return new LimitParams(time, count, message);
    }

    /**
     * 根据限流类型获取默认配置
     */
    private CoreRateLimitProperties.LimitConfig getDefaultConfigByType(LimitType limitType) {
        switch (limitType) {
            case IP:
                return rateLimitProperties.getIp();
            case USER:
                return rateLimitProperties.getUser();
            case DEFAULT:
                return rateLimitProperties.getDefaultConfig();
            default:
                return rateLimitProperties.getDefaultConfig();
        }
    }

    /**
     * 限流参数内部�?
     */
    private static class LimitParams {
        private final int time;
        private final int count;
        private final String message;

        public LimitParams(int time, int count, String message) {
            this.time = time;
            this.count = count;
            this.message = message;
        }

        public int getTime() {
            return time;
        }

        public int getCount() {
            return count;
        }

        public String getMessage() {
            return message;
        }
    }
}
