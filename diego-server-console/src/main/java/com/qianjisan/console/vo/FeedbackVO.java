package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 反馈视图对象(包含额外的统计信息)
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class FeedbackVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 反馈ID
     */
    private Long id;

    /**
     * 反馈标题
     */
    private String title;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 反馈类型：1-bug, 2-feature, 3-improvement, 4-other
     */
    private Integer type;

    /**
     * 状态：1-open, 2-closed
     */
    private Integer status;

    /**
     * 优先级：1-low, 2-medium, 3-high
     */
    private Integer priority;

    /**
     * 提交者ID
     */
    private Long submitterId;

    /**
     * 处理人ID
     */
    private Long assigneeId;

    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 联系方式(邮箱或电话)
     */
    private String contactInfo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 评论数量
     */
    private Integer comments;

    /**
     * 点赞数量
     */
    private Integer likes;

    /**
     * 当前用户是否已点赞
     */
    private Boolean liked;
}
