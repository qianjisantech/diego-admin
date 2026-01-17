package com.qianjisan.auth.service;

import com.qianjisan.auth.vo.LoginResponseVO;
import com.qianjisan.auth.vo.UserProfileVO;


/**
 * 认证服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return 登录响应
     */
    LoginResponseVO login(String email, String password);

    /**
     * 发送邮箱验证码
     *
     * @param email 邮箱地址
     */
    void sendVerificationCode(String email);

    /**
     * 用户注册
     *
     * @param email    邮箱
     * @param code     验证�?
     * @param password 密码
     */
    void register(String email, String code, String password);

    /**
     * 获取用户权限信息
     *
     * @param userId 用户ID
     * @return 用户权限信息
     */
    UserProfileVO getUserProfile(Long userId);
}
