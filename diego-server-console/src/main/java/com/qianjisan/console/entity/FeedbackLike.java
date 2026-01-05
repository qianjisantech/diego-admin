package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 反馈点赞表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@TableName("feedback_like")
public class FeedbackLike  {

    /**
     * 点赞ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 反馈ID
     */
    @TableField("feedback_id")
    private Long feedbackId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;
}
