package com.qianjisan.auth.vo;

import com.qianjisan.system.vo.SysMenuTreeVO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户档案VO（包含权限和空间信息�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UserProfileVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户基本信息
     */
    private UserInfoVo userInfo;

    /**
     * 菜单权限列表（权限标识数组）
     */
    private String[] menuPermissions;

    /**
     * 菜单树结构（用于前端渲染菜单�?
     */
    private List<UserMenuVo> menus;

    /**
     * 角色列表
     */
    private String[] roles;


    /**
     * 当前用户所属企业列�?
     */
    private List<UserCompanyVo> companies;


    @Data
    public static class UserCompanyVo implements Serializable {


        private Long id;

        private String companyName;

        private String companyCode;

        private Boolean isDefault;
    }


    @Data
    public static class UserInfoVo implements Serializable {
        /**
         * 邮箱
         */
        private String email;
        /**
         * 姓名
         */
        private String name;

        /**
         * 用户编码
         */
        private String userCode;

        /**
         * 头像URL
         */
        private String avatar;
    }

    @Data
    public static class UserMenuVo implements Serializable {


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
         * 状态：1-启用�?-禁用
         */
        private Integer status;

        /**
         * 权限标识
         */
        private String permission;

        /**
         * 子菜单列�?
         */
        private List<SysMenuTreeVO> children;
    }
}
