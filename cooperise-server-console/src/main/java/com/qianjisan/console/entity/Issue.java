package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项实体�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("issue")
public class Issue extends BaseEntity {

    /**
     * 事项ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 事项单号
     */
    @TableField("issue_no")
    private String issueNo;

    /**
     * 所属企业ID
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 企业名称（冗余字段，便于查询�?
     */
    @TableField("company_name")
    private String companyName;

    /**
     * 企业编码（冗余字段，便于查询�?
     */
    @TableField("company_code")
    private String companyCode;

    /**
     * 事项类型�?-任务�?-bug�?-需求�?-线上问题
     */
    @TableField("issue_type")
    private Integer issueType;

    /**
     * 概要
     */
    @TableField("summary")
    private String summary;

    /**
     * 详细描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态：1-待处理�?-进行中�?-已完成�?-已关�?
     */
    @TableField("status")
    private Integer status;

    /**
     * 优先级：1-高�?-中�?-�?
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 经办人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 经办人工�?
     */
    @TableField("assignee_code")
    private String assigneeCode;

    /**
     * 经办人姓�?
     */
    @TableField("assignee_name")
    private String assigneeName;

    /**
     * 报告人ID
     */
    @TableField("reporter_id")
    private Long reporterId;

    /**
     * 报告人工�?
     */
    @TableField("reporter_code")
    private String reporterCode;

    /**
     * 报告人姓�?
     */
    @TableField("reporter_name")
    private String reporterName;

    /**
     * 开始日�?
     */
    @TableField("start_date")
    private LocalDate startDate;

    /**
     * 截止日期
     */
    @TableField("due_date")
    private LocalDate dueDate;

    /**
     * 预估工时
     */
    @TableField("estimated_hours")
    private BigDecimal estimatedHours;

    /**
     * 实际工时
     */
    @TableField("actual_hours")
    private BigDecimal actualHours;

    /**
     * 进度百分比：0-100
     */
    @TableField("progress")
    private Integer progress;

    /**
     * 父事项ID（用于子任务�?
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 标签（JSON数组�?
     */
    @TableField("tags")
    private String tags;

    /**
     * 排期信息（JSON格式�?
     * 存储格式：[{"role":"product","roleName":"产品","assigneeId":1,"estimatedHours":8,"dateRange":["2024-01-01","2024-01-05"]}]
     */
    @TableField("schedule")
    private String schedule;
}
