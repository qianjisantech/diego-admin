package com.qianjisan.core.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志追踪切面
 * 为所有Controller方法生成traceid，并添加到MDC中供日志使用
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Aspect
@Component
@Order(1) // 在其他切面之前执行，确保traceid在所有日志中都能获取�?
public class LogTraceAspect {

    private static final String TRACE_ID_KEY = "traceId";

    /**
     * 定义切点：拦截所有Controller方法
     */
    @Pointcut("execution(* com.qianjisan..controller..*.*(..))")
    public void controllerMethods() {}

    /**
     * 在Controller方法执行前生成traceid并放入MDC
     */
    @Before("controllerMethods()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        // 生成traceid
        String traceId = generateTraceId();

        // 放入MDC，供日志框架使用
        MDC.put(TRACE_ID_KEY, traceId);

        // 获取方法信息用于日志
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[TRACE_START] {} - {}.{}()", traceId, className, methodName);
    }

    /**
     * 在Controller方法执行后清理MDC
     */
    @After("controllerMethods()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        // 获取当前的traceid用于结束日志
        String traceId = MDC.get(TRACE_ID_KEY);

        // 获取方法信息用于日志
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[TRACE_END] {} - {}.{}()", traceId, className, methodName);

        // 清理MDC，防止内存泄�?
        MDC.remove(TRACE_ID_KEY);
    }

    /**
     * 生成traceid
     * 使用雪花ID生成器生成分布式唯一ID
     */
    private String generateTraceId() {
        return "trace_id=" + com.qianjisan.core.utils.SnowflakeIdGenerator.generateId();
    }
}
