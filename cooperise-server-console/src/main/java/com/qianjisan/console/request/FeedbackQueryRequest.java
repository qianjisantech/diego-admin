package com.qianjisan.console.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 反馈类型�?-bug, 2-feature, 3-improvement, 4-other */
    private Integer type;

    /** 状态：1-待处�? 2-处理�? 3-已解�? 4-已关�?*/
    private Integer status;

    /** 优先级：1-�? 2-�? 3-�?*/
    private Integer priority;

    /** 提交人ID */
    private Long submitterId;

    /** 处理人ID */
    private Long assigneeId;

    /** 搜索关键词（匹配标题和内容） */
    private String keyword;
}

