package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.WorkspaceIssueAttachmentQueryRequest;
import com.qianjisan.console.entity.IssueAttachment;


/**
 * WorkspaceIssueAttachment服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueAttachmentService extends IService<IssueAttachment> {

    /**
     * 分页查询事项附件管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<IssueAttachment> pageIssueAttachment(WorkspaceIssueAttachmentQueryRequest request);
}
