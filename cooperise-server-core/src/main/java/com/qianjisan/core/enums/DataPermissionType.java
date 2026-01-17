package com.qianjisan.core.enums;

/**
 * 数据权限类型枚举
 *
 * @author cooperise
 * @since 2024-11-21
 */
public enum DataPermissionType {

    /**
     * 空间级别权限
     * 用户只能访问其所在空间的数据
     */
    SPACE,

    /**
     * 视图级别权限
     * 用户只能访问其创建或有权访问的视�?
     */
    VIEW,

    /**
     * 所有者权�?
     * 用户只能访问自己创建的数�?
     */
    OWNER,

    /**
     * 全局权限
     * 不进行权限限�?管理员可见所有数�?
     */
    GLOBAL
}
