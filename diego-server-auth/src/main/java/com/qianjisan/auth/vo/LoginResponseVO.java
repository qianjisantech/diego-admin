package com.qianjisan.auth.vo;


import com.qianjisan.system.vo.UserInfoVO;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class LoginResponseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * JWT Token
     */
    private String token;

    /**
     * 用户信息
     */
    private UserInfoVO userInfo;
}
