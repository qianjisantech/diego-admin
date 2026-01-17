package com.qianjisan.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解
 * 用于Controller方法上，校验用户是否有指定权�?
 *
 * 使用示例�?
 * @RequiresPermission("workspace:issue:add")
 * @RequiresPermission(value = {"workspace:issue:add", "workspace:issue:edit"}, logical = Logical.OR)
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiresPermission {

    /**
     * 需要的权限标识
     * 可以是单个权限，也可以是多个权限
     */
    String[] value();

    /**
     * 逻辑关系：AND（需要全部权限）�?OR（需要其中一个权限）
     * 默认�?AND
     */
    Logical logical() default Logical.AND;

    /**
     * 逻辑枚举
     */
    enum Logical {
        /**
         * 必须拥有所有权�?
         */
        AND,
        /**
         * 只需拥有其中一个权限即�?
         */
        OR
    }
}
