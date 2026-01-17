package com.qianjisan.common.dto;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项评论查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueCommentQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 事项ID */
    private Long issueId;

    /** 用户ID */
    private Long userId;

    /** 父评论ID */
    private Long parentId;

    /** 搜索关键词（匹配评论内容�?*/
    private String keyword;
}

