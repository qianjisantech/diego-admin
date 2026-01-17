package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项分页VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class IssuePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    private Long id;

    /**
     * 事项单号
     */
    private String issueNo;

    /**
     * 所属空间ID
     */
    private Long spaceId;

    /**
     * 空间名称
     */
    private String spaceName;

    /**
     * 空间关键�?
     */
    private String spaceKeyword;

    /**
     * 事项类型�?-任务�?-bug�?-需求�?-线上问题
     */
    private Integer issueType;

    /**
     * 概要
     */
    private String summary;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 状态：1-待处理�?-进行中�?-已完成�?-已关�?
     */
    private Integer status;

    /**
     * 优先级：1-高�?-中�?-�?
     */
    private Integer priority;

    /**
     * 经办人ID
     */
    private Long assigneeId;

    /**
     * 经办人工�?
     */
    private String assigneeCode;

    /**
     * 经办人姓�?
     */
    private String assigneeName;

    /**
     * 报告人ID
     */
    private Long reporterId;

    /**
     * 报告人工�?
     */
    private String reporterCode;

    /**
     * 报告人姓�?
     */
    private String reporterName;

    /**
     * 开始日�?
     */
    private LocalDate startDate;

    /**
     * 截止日期
     */
    private LocalDate dueDate;

    /**
     * 预估工时
     */
    private BigDecimal estimatedHours;

    /**
     * 实际工时
     */
    private BigDecimal actualHours;

    /**
     * 进度百分比：0-100
     */
    private Integer progress;

    /**
     * 父事项ID
     */
    private Long parentId;

    /**
     * 标签
     */
    private String tags;

    /**
     * 排期信息（JSON格式�?
     */
    private String schedule;
}

