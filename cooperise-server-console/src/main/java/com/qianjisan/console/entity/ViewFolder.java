package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图文件夹表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("view_folder")
public class ViewFolder extends BaseEntity {

    /**
     * 文件夹ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件夹名�?
     */
    @TableField("name")
    private String name;

    /**
     * 创建者ID
     */
    @TableField("owner_id")
    private Long ownerId;

    /**
     * 父文件夹ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 排序顺序
     */
    @TableField("sort_order")
    private Integer sortOrder;
}
