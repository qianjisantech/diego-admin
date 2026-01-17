package com.qianjisan.system.vo;


import lombok.Data;

import java.util.List;

/**
 * 菜单VO（用于树形结构）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysMenuVO  {

    /**
     * ID
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单类型�?-目录�?-菜单�?-按钮
     */
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
     * 是否可见�?-是，0-�?
     */
    private Integer visible;



    /**
     * 权限标识
     */
    private String permission;

    /**
     * 子菜单列�?
     */
    private List<SysMenuVO> children;
}
