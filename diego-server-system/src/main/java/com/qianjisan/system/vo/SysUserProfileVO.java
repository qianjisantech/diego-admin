package com.qianjisan.system.vo;

import com.qianjisan.core.request.SpaceVO;
import com.qianjisan.enterprise.vo.CompanyVo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户档案VO（包含权限和空间信息）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysUserProfileVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户基本信息
     */
    private UserInfoVO userInfo;

    /**
     * 菜单权限列表（权限标识数组）
     */
    private String[] menuPermissions;

    /**
     * 菜单树结构（用于前端渲染菜单）
     */
    private List<SysMenuVO> menus;

    /**
     * 角色列表
     */
    private String[] roles;


    /**
     * 当前用户所属企业列表
     */
    private List<CompanyVo> companies;

    /**
     * 当前用户所属企业ID列表
     */
    private Long[] companyIds;

}
