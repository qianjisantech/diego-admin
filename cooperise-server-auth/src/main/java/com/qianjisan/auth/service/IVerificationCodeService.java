package com.qianjisan.auth.service;

/**
 * 验证码服务接�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IVerificationCodeService {

    /**
     * 生成并存储验证码
     *
     * @param email 邮箱地址
     * @return 验证�?
     */
    String generateCode(String email);

    /**
     * 验证验证码是否正�?
     *
     * @param email 邮箱地址
     * @param code  验证�?
     * @return 是否正确
     */
    boolean verifyCode(String email, String code);

    /**
     * 删除验证�?
     *
     * @param email 邮箱地址
     */
    void removeCode(String email);
}
