package com.qianjisan.core.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户VO
 * 不包含敏感字段（如密码）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {

    /**
     * 用户�?
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;
}
