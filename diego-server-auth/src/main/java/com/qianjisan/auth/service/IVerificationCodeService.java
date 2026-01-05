package com.qianjisan.auth.service;

/**
 * 验证码服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IVerificationCodeService {

    /**
     * 生成并存储验证码
     *
     * @param email 邮箱地址
     * @return 验证码
     */
    String generateCode(String email);

    /**
     * 验证验证码是否正确
     *
     * @param email 邮箱地址
     * @param code  验证码
     * @return 是否正确
     */
    boolean verifyCode(String email, String code);

    /**
     * 删除验证码
     *
     * @param email 邮箱地址
     */
    void removeCode(String email);
}
