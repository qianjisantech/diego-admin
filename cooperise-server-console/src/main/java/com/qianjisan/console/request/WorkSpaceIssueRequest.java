package com.qianjisan.console.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 事项请求参数
 */
@Data
public class WorkSpaceIssueRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Valid
    private SpaceInfo space;

    private String issueNo;

    @NotNull(message = "事项类型不能为空")
    private Integer issueType;

    @NotBlank(message = "概要不能为空")
    @Size(max = 200, message = "概要长度不能超过200个字�?)
    private String summary;

    @NotBlank(message = "详细描述不能为空")
    @Size(max = 5000, message = "详细描述长度不能超过5000个字�?)
    private String description;

    @NotNull(message = "状态不能为�?)
    @Min(value = 0, message = "状态值必须在0-3之间")
    @Max(value = 3, message = "状态值必须在0-3之间")
    private Integer status;

    @NotNull(message = "优先级不能为�?)
    @Min(value = 1, message = "优先级值必须在1-3之间")
    @Max(value = 3, message = "优先级值必须在1-3之间")
    private Integer priority;

    @NotNull(message = "经办人不能为�?)
    @Valid
    private AssigneeInfo assignee;

    @Valid
    private ReporterInfo reporter;

    @NotNull(message = "开始日期不能为�?)
    private LocalDate startDate;

    @NotNull(message = "截止日期不能为空")
    private LocalDate dueDate;

    @NotNull(message = "预估工时不能为空")
    @DecimalMin(value = "0.0", message = "预估工时不能为负�?)
    private BigDecimal estimatedHours;

    @DecimalMin(value = "0.0", message = "实际工时不能为负�?)
    private BigDecimal actualHours;

    @Min(value = 0, message = "进度必须�?-100之间")
    @Max(value = 100, message = "进度必须�?-100之间")
    private Integer progress;

    private Long parentId;

    @Size(max = 500, message = "标签长度不能超过500个字�?)
    private String tags;

    private String schedule;

    @Min(value = 0, message = "附件数量不能为负�?)
    private Integer attachmentCount;

    @Min(value = 0, message = "评论数量不能为负�?)
    private Integer commentCount;

    @Data
    public static class SpaceInfo {
        @NotNull(message = "空间ID不能为空")
        private Long spaceId;

        @NotBlank(message = "空间名称不能为空")
        @Size(max = 100, message = "空间名称长度不能超过100个字�?)
        private String spaceName;

        @Size(max = 50, message = "空间关键词长度不能超�?0个字�?)
        private String spaceKeyword;
    }

    @Data
    public static class AssigneeInfo {
        @NotNull(message = "经办人ID不能为空")
        private Long assigneeId;

        @Size(max = 50, message = "经办人工号长度不能超�?0个字�?)
        private String assigneeCode;

        @NotBlank(message = "经办人姓名不能为�?)
        @Size(max = 100, message = "经办人姓名长度不能超�?00个字�?)
        private String assigneeName;
    }

    @Data
    public static class ReporterInfo {
        @NotNull(message = "报告人ID不能为空")
        private Long reporterId;

        @Size(max = 50, message = "报告人工号长度不能超�?0个字�?)
        private String reporterCode;

        @NotBlank(message = "报告人姓名不能为�?)
        @Size(max = 100, message = "报告人姓名长度不能超�?00个字�?)
        private String reporterName;
    }
}


