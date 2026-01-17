package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 菜单请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysMenuRequest {

    /**
     * 菜单ID（更新时需要）
     */
    private Long id;

    /**
     * 父菜单ID，0表示一级菜单
     */
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单类型：1-目录，2-菜单，3-按钮
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer menuType;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否可见：1-是，0-否
     */
    private Integer visible;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 权限标识
     */
    private String permission;
}
