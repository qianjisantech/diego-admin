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

    /** 反馈类型：1-bug, 2-feature, 3-improvement, 4-other */
    private Integer type;

    /** 状态：1-待处理, 2-处理中, 3-已解决, 4-已关闭 */
    private Integer status;

    /** 优先级：1-高, 2-中, 3-低 */
    private Integer priority;

    /** 提交人ID */
    private Long submitterId;

    /** 处理人ID */
    private Long assigneeId;

    /** 搜索关键词（匹配标题和内容） */
    private String keyword;
}

