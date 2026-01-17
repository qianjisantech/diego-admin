package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项活动记录�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("issue_activity")
public class IssueActivity extends BaseEntity {

    /**
     * 活动ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事项ID
     */
    @TableField("issue_id")
    private Long issueId;

    /**
     * 操作用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作类型：创建、修改状态、修改优先级、修改经办人、添加评论等
     */
    @TableField("action")
    private String action;

    /**
     * 修改字段
     */
    @TableField("field")
    private String field;

    /**
     * 旧�?
     */
    @TableField("old_value")
    private String oldValue;

    /**
     * 新�?
     */
    @TableField("new_value")
    private String newValue;
}
