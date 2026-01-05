package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项实体类
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
     * 所属空间ID
     */
    @TableField("space_id")
    private Long spaceId;

    /**
     * 空间名称（冗余字段，便于查询）
     */
    @TableField("space_name")
    private String spaceName;

    /**
     * 空间关键词（冗余字段，便于查询）
     */
    @TableField("space_keyword")
    private String spaceKeyword;

    /**
     * 事项类型：1-任务、2-bug、3-需求、4-线上问题
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
     * 状态：1-待处理、2-进行中、3-已完成、0-已关闭
     */
    @TableField("status")
    private Integer status;

    /**
     * 优先级：1-高、2-中、3-低
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 经办人ID
     */
    @TableField("assignee_id")
    private Long assigneeId;

    /**
     * 经办人工号
     */
    @TableField("assignee_code")
    private String assigneeCode;

    /**
     * 经办人姓名
     */
    @TableField("assignee_name")
    private String assigneeName;

    /**
     * 报告人ID
     */
    @TableField("reporter_id")
    private Long reporterId;

    /**
     * 报告人工号
     */
    @TableField("reporter_code")
    private String reporterCode;

    /**
     * 报告人姓名
     */
    @TableField("reporter_name")
    private String reporterName;

    /**
     * 开始日期
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
     * 父事项ID（用于子任务）
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 标签（JSON数组）
     */
    @TableField("tags")
    private String tags;

    /**
     * 排期信息（JSON格式）
     * 存储格式：[{"role":"product","roleName":"产品","assigneeId":1,"estimatedHours":8,"dateRange":["2024-01-01","2024-01-05"]}]
     */
    @TableField("schedule")
    private String schedule;
}
