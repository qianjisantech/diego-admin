package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子任务表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("issue_subtask")
public class IssueSubtask extends BaseEntity {

    /**
     * 子任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属事项ID
     */
    @TableField("issue_id")
    private Long issueId;

    /**
     * 子任务标题
     */
    @TableField("title")
    private String title;

    /**
     * 是否完成：0-未完成，1-已完成
     */
    @TableField("completed")
    private Integer completed;

    /**
     * 排序顺序
     */
    @TableField("sort_order")
    private Integer sortOrder;
}
