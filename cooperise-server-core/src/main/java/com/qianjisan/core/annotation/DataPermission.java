package com.qianjisan.core.annotation;



import com.qianjisan.core.enums.DataPermissionType;

import java.lang.annotation.*;

/**
 * 数据权限注解
 * 用于控制用户对数据的访问权限
 *
 * 使用示例:
 * 1. 空间数据权限: @DataPermission(type = DataPermissionType.SPACE)
 * 2. 视图数据权限: @DataPermission(type = DataPermissionType.VIEW)
 * 3. 个人数据: @DataPermission(type = DataPermissionType.OWNER)
 *
 * @author Diego
 * @since 2024-11-21
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * 数据权限类型
     */
    DataPermissionType type() default DataPermissionType.SPACE;

    /**
     * 是否启用权限校验
     * 默认启用,可通过设置为false来临时禁用某个方法的权限校验
     */
    boolean enabled() default true;

    /**
     * 提示信息
     */
    String message() default "您没有权限访问该数据";
}
