package com.qianjisan.core.utils;

import org.slf4j.Logger;
import org.slf4j.MDC;

/**
 * 带traceid的日志工具类
 * 为所有日志自动添加traceid，支持链路追�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class TraceLogger {

    private static final String TRACE_ID_KEY = "traceId";

    /**
     * 获取当前traceid（带前缀�?
     */
    public static String getTraceId() {
        return MDC.get(TRACE_ID_KEY);
    }

    /**
     * 获取当前traceid（不带前缀�?
     */
    public static String getTraceIdWithoutPrefix() {
        String fullTraceId = getTraceId();
        if (fullTraceId != null && fullTraceId.startsWith("trace_id=")) {
            return fullTraceId.substring(9);
        }
        return fullTraceId;
    }

    /**
     * 设置traceid
     */
    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID_KEY, traceId);
    }

    /**
     * 清除traceid
     */
    public static void clearTraceId() {
        MDC.remove(TRACE_ID_KEY);
    }

    /**
     * 生成新的traceid
     * 使用雪花ID生成器生成分布式唯一ID
     */
    public static String generateTraceId() {
        return "trace_id=" + SnowflakeIdGenerator.generateId();
    }

    /**
     * 带traceid的日志记录器包装�?
     */
    public static class TraceLoggerWrapper {
        private final Logger logger;

        public TraceLoggerWrapper(Logger logger) {
            this.logger = logger;
        }

        // TRACE级别日志
        public void trace(String msg) {
            logger.trace("[{}] {}", getTraceId(), msg);
        }

        public void trace(String format, Object... args) {
            logger.trace("[{}] " + format, getTraceId(), args);
        }

        // DEBUG级别日志
        public void debug(String msg) {
            logger.debug("[{}] {}", getTraceId(), msg);
        }

        public void debug(String format, Object... args) {
            logger.debug("[{}] " + format, getTraceId(), args);
        }

        // INFO级别日志
        public void info(String msg) {
            logger.info("[{}] {}", getTraceId(), msg);
        }

        public void info(String format, Object... args) {
            logger.info("[{}] " + format, getTraceId(), args);
        }

        // WARN级别日志
        public void warn(String msg) {
            logger.warn("[{}] {}", getTraceId(), msg);
        }

        public void warn(String format, Object... args) {
            logger.warn("[{}] " + format, getTraceId(), args);
        }

        // ERROR级别日志
        public void error(String msg) {
            logger.error("[{}] {}", getTraceId(), msg);
        }

        public void error(String format, Object... args) {
            logger.error("[{}] " + format, getTraceId(), args);
        }

        public void error(String msg, Throwable t) {
            logger.error("[{}] {}", getTraceId(), msg, t);
        }
    }

    /**
     * 为普通logger创建带traceid的包装器
     */
    public static TraceLoggerWrapper wrap(Logger logger) {
        return new TraceLoggerWrapper(logger);
    }
}
