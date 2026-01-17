package com.qianjisan.request;

import lombok.Data;

/**
 * 空间请求对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SpaceRequest {

    /**
     * 空间名称
     */
    private String name;

    /**
     * 空间关键词（用于生成事项单号）
     */
    private String keyword;

    /**
     * 空间描述
     */
    private String description;

    /**
     * 空间图标
     */
    private String icon;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 所有者工号
     */
    private String ownerCode;

    /**
     * 所有者姓名
     */
    private String ownerName;
}
