package com.qianjisan.core.utils;

import java.security.SecureRandom;

/**
 * 用户编码生成�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class UserCodeGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成8位纯数字的用户编�?
     * 格式：当前时间戳�?�?+ 2位随机数
     * 例如�?2345678
     *
     * @return 8位数字字符串
     */
    public static String generate() {
        // 获取当前时间戳的�?�?
        long timestamp = System.currentTimeMillis();
        String timestampStr = String.valueOf(timestamp);
        String last6Digits = timestampStr.substring(timestampStr.length() - 6);

        // 生成2位随机数 (00-99)
        int randomNum = RANDOM.nextInt(100);
        String randomStr = String.format("%02d", randomNum);

        return last6Digits + randomStr;
    }

    /**
     * 生成指定长度的纯数字编码
     *
     * @param length 长度
     * @return 纯数字字符串
     */
    public static String generate(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append(RANDOM.nextInt(10));
        }
        return code.toString();
    }
}
