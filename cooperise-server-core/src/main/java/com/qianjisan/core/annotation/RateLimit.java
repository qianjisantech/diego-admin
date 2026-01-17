package com.qianjisan.core.annotation;



import com.qianjisan.core.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 * 基于 Redis + Lua 脚本实现分布式限�?
 *
 * 使用方式:
 * 1. 使用配置文件预设�? @RateLimit(configKey = "login")
 * 2. 自定义参�? @RateLimit(time = 60, count = 10, limitType = LimitType.IP)
 *
 * @author Diego
 * @since 2024-11-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 配置文件中的key，用于引用预设的限流配置
     * 如果设置了此值，将从配置文件 dcp.rate-limit.special.{configKey} 中读取配�?
     * 优先级高于其他参�?
     */
    String configKey() default "";

    /**
     * 限流key的前缀
     */
    String key() default "rate_limit:";

    /**
     * 限流时间窗口，单位秒
     * 默认�?-1 表示使用配置文件中的默认�?
     */
    int time() default -1;

    /**
     * 限流次数
     * 默认�?-1 表示使用配置文件中的默认�?
     */
    int count() default -1;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;

    /**
     * 提示信息
     * 空字符串表示使用配置文件中的默认�?
     */
    String message() default "";
}
