package com.qianjisan.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "dcp.rate-limit")
public class CoreRateLimitProperties {

    /** 是否启用限流功能 */
    private Boolean enabled = true;

    /** 默认限流配置 */
    private LimitConfig defaultConfig = new LimitConfig(60, 100, "访问过于频繁，请稍后再试");

    /** IP限流配置 */
    private LimitConfig ip = new LimitConfig(60, 30, "您的访问过于频繁，请稍后再试");

    /** 用户限流配置 */
    private LimitConfig user = new LimitConfig(60, 50, "您的操作过于频繁，请稍后再试");

    /** 接口限流配置（key为接口路径或自定义标识） */
    private Map<String, LimitConfig> api = new HashMap<>();

    /**
     * 获取接口或自定义限流配置
     * 如果不存在，则返回默认配置
     */
    public LimitConfig getSpecialConfig(String key) {
        return api.getOrDefault(key, defaultConfig);
    }

    /**
     * 限流配置
     */
    @Data
    public static class LimitConfig {
        /** 时间窗口（秒） */
        private Integer time;
        /** 窗口内最大请求数 */
        private Integer count;
        /** 超限提示信息 */
        private String message;

        public LimitConfig() {
        }

        public LimitConfig(Integer time, Integer count, String message) {
            this.time = time;
            this.count = count;
            this.message = message;
        }
    }
}

