package com.qianjisan.core.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码加密工具�?
 * 使用 SHA-256 加盐哈希算法
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class PasswordUtil {

    private static final String ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;

    /**
     * 加密密码
     *
     * @param rawPassword 原始密码
     * @return 加密后的密码（格式：salt$hash�?
     */
    public static String encode(String rawPassword) {
        try {
            // 生成随机盐�?
            byte[] salt = generateSalt();

            // 计算哈希�?
            String hash = hash(rawPassword, salt);

            // 返回格式：salt$hash
            return Base64.getEncoder().encodeToString(salt) + "$" + hash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码
     *
     * @param rawPassword     原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        try {
            // 分离盐值和哈希�?
            String[] parts = encodedPassword.split("\\$");
            if (parts.length != 2) {
                return false;
            }

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            String expectedHash = parts[1];

            // 计算原始密码的哈希�?
            String actualHash = hash(rawPassword, salt);

            // 比较哈希�?
            return expectedHash.equals(actualHash);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 生成随机盐�?
     */
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return salt;
    }

    /**
     * 计算哈希�?
     */
    private static String hash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(ALGORITHM);
        md.update(salt);
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
