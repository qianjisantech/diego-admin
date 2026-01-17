package com.qianjisan.system.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserVO extends BaseVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户�?
     */
    private String username;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机�?
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 状态：1-正常�?-禁用
     */
    private Integer status;

    /**
     * 最后登录时�?
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;

    /**
     * 用户角色列表
     */
    private List<SysRoleVO> roles;
}
