package com.qianjisan.common.dto;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项附件查询请求参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WorkspaceIssueAttachmentQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 事项ID */
    private Long issueId;

    /** 上传人ID */
    private Long uploaderId;

    /** 文件类型 */
    private String fileType;

    /** 搜索关键词（匹配文件名） */
    private String keyword;
}

