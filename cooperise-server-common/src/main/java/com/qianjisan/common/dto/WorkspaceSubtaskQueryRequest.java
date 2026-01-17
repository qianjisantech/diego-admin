package com.qianjisan.common.dto;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子任务查询请求参�?
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceSubtaskQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 事项ID */
    private Long issueId;

    /** 是否完成�?-未完成，1-已完�?*/
    private Integer completed;

    /** 搜索关键词（匹配子任务标题） */
    private String keyword;
}

