package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈评论�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("feedback_comment")
public class FeedbackComment extends BaseEntity {

    /**
     * 评论ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 反馈ID
     */
    @TableField("feedback_id")
    private Long feedbackId;

    /**
     * 评论用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;
}
