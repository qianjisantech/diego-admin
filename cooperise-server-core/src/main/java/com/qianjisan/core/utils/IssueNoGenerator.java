package com.qianjisan.core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 事项单号生成器
 * 格式: 关键词-年月日时分秒
 * 例如: PRJ-20241108143025
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class IssueNoGenerator {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成事项单号（使用默认关键词）
     *
     * @return 事项单号
     */
    public static String generate() {
        return generateWithKeyword("ISSUE");
    }

    /**
     * 根据企业关键词生成事项单号
     * 格式: 关键词-年月日时分秒
     * 例如: PRJ-20241108143025
     *
     * @param keyword 企业关键词
     * @return 事项单号
     */
    public static String generateWithKeyword(String keyword) {
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(TIMESTAMP_FORMATTER);

        // 如果没有关键词，使用默认的 ISSUE
        String prefix = (keyword != null && !keyword.isEmpty()) ? keyword : "ISSUE";

        return prefix + "-" + timestamp;
    }

    /**
     * 根据企业代码生成事项单号（兼容旧方法）
     * 格式: 企业代码-年月日时分秒
     * 例如: PRJ-20241108143025
     *
     * @param spaceCode 企业代码
     * @return 事项单号
     * @deprecated 请使用 generateWithKeyword
     */
    @Deprecated
    public static String generateWithSpace(String spaceCode) {
        return generateWithKeyword(spaceCode);
    }
}
