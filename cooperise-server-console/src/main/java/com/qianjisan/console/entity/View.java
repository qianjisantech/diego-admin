package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("view")
public class View extends BaseEntity {

    /**
     * 视图ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 视图名称
     */
    @TableField("name")
    private String name;

    /**
     * 视图描述
     */
    @TableField("description")
    private String description;

    /**
     * 视图类型：gantt-甘特图、kanban-看板、table-表格、calendar-日历、resource-gantt-资源甘特�?
     */
    @TableField("type")
    private String type;

    /**
     * 所属空间ID（空间视图）
     */
    @TableField("space_id")
    private Long spaceId;

    /**
     * 创建者ID
     */
    @TableField("owner_id")
    private Long ownerId;

    /**
     * 是否公共�?-私有�?-公共
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 所属文件夹ID
     */
    @TableField("folder_id")
    private Long folderId;

    /**
     * 视图配置（JSON格式�?
     */
    @TableField("config")
    private String config;

    /**
     * 排序顺序
     */
    @TableField("sort_order")
    private Integer sortOrder;
}
