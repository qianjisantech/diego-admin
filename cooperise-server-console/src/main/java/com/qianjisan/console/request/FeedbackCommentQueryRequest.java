package com.qianjisan.console.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈评论查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackCommentQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 反馈ID */
    private Long feedbackId;

    /** 用户ID */
    private Long userId;

    /** 搜索关键词（匹配评论内容�?*/
    private String keyword;
}


