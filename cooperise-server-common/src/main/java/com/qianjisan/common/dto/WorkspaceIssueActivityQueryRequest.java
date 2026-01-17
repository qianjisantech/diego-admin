package com.qianjisan.common.dto;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项活动记录查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueActivityQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 事项ID */
    private Long issueId;

    /** 活动类型 */
    private String activityType;

    /** 用户ID */
    private Long userId;

    /** 搜索关键词（活动类型、字段、旧值、新值） */
    private String keyword;
}

