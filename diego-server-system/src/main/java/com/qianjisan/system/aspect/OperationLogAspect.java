package com.qianjisan.system.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qianjisan.core.utils.JwtUtil;
import com.qianjisan.system.entity.SysOperationLog;
import com.qianjisan.system.service.ISysOperationLogService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作日志AOP切面
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final ISysOperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    /**
     * 定义切点：拦截所有Controller层的方法
     */
    @Pointcut("execution(* com.qianjisan..*.controller..*.*(..))")
    public void operationLog() {
    }

    /**
     * 环绕通知：记录操作日志
     */
    @Around("operationLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        log.info("========== AOP拦截到请求 ==========");
        log.info("目标方法: {}", joinPoint.getSignature());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.warn("无法获取RequestAttributes，跳过日志记录");
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String requestUri = request.getRequestURI();
        log.info("请求URL: {} {}", request.getMethod(), requestUri);

        // 排除操作日志相关接口，避免记录日志时触发循环
        if (requestUri.contains("operation-log")) {
            log.debug("跳过操作日志接口的日志记录: {}", requestUri);
            return joinPoint.proceed();
        }

        // 构建操作日志对象
        SysOperationLog sysOperationLog = new SysOperationLog();
        sysOperationLog.setRequestMethod(request.getMethod());
        sysOperationLog.setRequestUrl(request.getRequestURI());
        sysOperationLog.setIpAddress(getIpAddress(request));
        sysOperationLog.setUserAgent(request.getHeader("User-Agent"));

        // 从token中获取用户信息
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token)) {
            try {
                Long userId = JwtUtil.getUserId(token);
                String username = JwtUtil.getUsername(token);
                sysOperationLog.setUserId(userId);
                sysOperationLog.setUsername(username);
                log.info("从Token获取用户信息: userId={}, username={}", userId, username);
            } catch (Exception e) {
                log.warn("从Token解析用户信息失败: {}", e.getMessage());
            }
        }

        // 获取请求参数
        try {
            Map<String, String> paramMap = getRequestParams(request);
            if (!paramMap.isEmpty()) {
                sysOperationLog.setRequestParams(objectMapper.writeValueAsString(paramMap));
            }
        } catch (Exception e) {
            log.error("获取请求参数失败", e);
        }

        // 获取请求体(仅针对POST/PUT/PATCH等方法)
        if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod()) || "PATCH".equals(request.getMethod())) {
            try {
                Object[] args = joinPoint.getArgs();
                if (args != null && args.length > 0) {
                    // 只记录第一个参数作为请求体(通常是@RequestBody注解的参数)
                    for (Object arg : args) {
                        if (arg != null && !isServletType(arg)) {
                            sysOperationLog.setRequestBody(objectMapper.writeValueAsString(arg));
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                log.error("获取请求体失败", e);
            }
        }

        Object result = null;
        try {
            // 执行目标方法
            result = joinPoint.proceed();
            sysOperationLog.setStatusCode(200);

            // 记录响应体(限制大小,避免过大)
            try {
                String responseBody = objectMapper.writeValueAsString(result);
                if (responseBody.length() > 5000) {
                    responseBody = responseBody.substring(0, 5000) + "...(truncated)";
                }
                sysOperationLog.setResponseBody(responseBody);
            } catch (Exception e) {
                log.error("记录响应体失败", e);
            }

        } catch (Throwable throwable) {
            sysOperationLog.setStatusCode(500);
            sysOperationLog.setErrorMsg(throwable.getMessage());
            throw throwable;
        } finally {
            // 计算执行时间
            long endTime = System.currentTimeMillis();
            sysOperationLog.setExecutionTime(endTime - startTime);

            // 异步保存日志
            saveLogAsync(sysOperationLog);
        }

        return result;
    }

    /**
     * 异步保存日志
     */
    @Async
    public void saveLogAsync(SysOperationLog sysOperationLog) {
        try {
            log.info("开始保存操作日志: {} {}", sysOperationLog.getRequestMethod(), sysOperationLog.getRequestUrl());
            operationLogService.saveLog(sysOperationLog);
            log.info("操作日志保存成功");
        } catch (Exception e) {
            log.error("保存操作日志失败", e);
        }
    }

    /**
     * 从请求中获取Token
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        // 从Header中获取
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        // 从参数中获取
        String token = request.getParameter("token");
        if (StringUtils.hasText(token)) {
            return token;
        }

        return null;
    }

    /**
     * 获取请求参数
     */
    private Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }
        return paramMap;
    }

    /**
     * 获取真实IP地址
     * 优先从前端传递的自定义请求头获取
     */
    private String getIpAddress(HttpServletRequest request) {
        // 1. 优先从前端传递的自定义请求头获取（前端通过API或库获取的真实IP）
        String ip = request.getHeader("X-Client-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip) && !"auto-detect".equalsIgnoreCase(ip)) {
            log.debug("从X-Client-IP获取IP: {}", ip);
            return ip.trim();
        }

        // 2. 从标准代理头获取
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            log.debug("从X-Real-IP获取IP: {}", ip);
            return ip.trim();
        }

        // 3. 从X-Forwarded-For获取
        ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 对于通过多个代理的情况，第一个IP为客户端真实IP
            if (ip.contains(",")) {
                ip = ip.split(",")[0].trim();
            }
            log.debug("从X-Forwarded-For获取IP: {}", ip);
            return ip;
        }

        // 4. 从其他代理头获取
        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            log.debug("从Proxy-Client-IP获取IP: {}", ip);
            return ip.trim();
        }

        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)) {
            log.debug("从WL-Proxy-Client-IP获取IP: {}", ip);
            return ip.trim();
        }

        // 5. 最后使用远程地址
        ip = request.getRemoteAddr();
        log.debug("从RemoteAddr获取IP: {}", ip);
        return ip;
    }

    /**
     * 判断是否为Servlet相关类型
     */
    private boolean isServletType(Object arg) {
        return arg instanceof HttpServletRequest
            || arg instanceof HttpServletResponse
            || arg instanceof HttpSession;
    }
}
