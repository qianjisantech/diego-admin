package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 反馈表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("feedback")
public class Feedback extends BaseEntity {

    /**
     * 反馈ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 反馈标题
     */
    @TableField("title")
    private String title;

    /**
     * 反馈内容
     */
    @TableField("content")
    private String content;

    /**
     * 反馈类型：1-bug, 2-feature, 3-improvement, 4-other
     */
    @TableField("type")
    private Integer type;

    /**
     * 状态：1-open, 2-closed
     */
    @TableField("status")
    private Integer status;

    /**
     * 优先级：1-low, 2-medium, 3-high
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 提交者ID
     */
    @TableField("submitter_id")
    private Long submitterId;

    /**
     * 提交者姓名
     */
    @TableField("submitter_name")
    private String submitterName;

    /**
     * 提交者工号
     */
    @TableField("submitter_code")
    private String submitterCode;

    /**
     * 处理人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 处理人工号
     */
    @TableField("assignee_code")
    private String assigneeCode;

    /**
     * 处理人姓名
     */
    @TableField("assignee_name")
    private String assigneeName;
    /**
     * 关闭时间
     */
    @TableField("close_time")
    private LocalDateTime closeTime;

    /**
     * 联系方式(邮箱或电话)
     */
    @TableField("contact_info")
    private String contactInfo;

    /**
     * 点赞数
     */
    @TableField("likes")
    private Integer likes;

    /**
     * 评论数
     */
    @TableField("comments")
    private Integer comments;
}
