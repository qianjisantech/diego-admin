package com.qianjisan.core.enums;

/**
 * 数据权限类型枚举
 *
 * @author Diego
 * @since 2024-11-21
 */
public enum DataPermissionType {

    /**
     * 企业级别权限
     * 用户只能访问其所在企业的数据
     */
    SPACE,

    /**
     * 视图级别权限
     * 用户只能访问其创建或有权访问的视图
     */
    VIEW,

    /**
     * 所有者权限
     * 用户只能访问自己创建的数据
     */
    OWNER,

    /**
     * 全局权限
     * 不进行权限限制(管理员可见所有数据)
     */
    GLOBAL
}
