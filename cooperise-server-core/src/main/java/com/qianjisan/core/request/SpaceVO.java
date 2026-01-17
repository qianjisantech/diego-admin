package com.qianjisan.core.request;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 空间视图对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SpaceVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 空间名称
     */
    private String name;


    /**
     * 空间关键词（用于生成事项单号�?
     */
    private String keyword;

    /**
     * 空间描述
     */
    private String description;

    /**
     * 图标
     */
    private String icon;

    /**
     * 负责人ID
     */
    private Long ownerId;

    /**
     * 负责人名称（扩展字段�?
     */
    private String ownerName;

    /**
     * 状态：1-激活，0-归档
     */
    private Integer status;

    /**
     * 成员数量（扩展字段）
     */
    private Integer memberCount;

    // 不包�?isDeleted 字段
}
