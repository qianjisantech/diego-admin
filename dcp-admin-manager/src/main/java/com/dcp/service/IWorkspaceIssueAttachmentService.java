package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceIssueAttachmentQueryDTO;
import com.dcp.entity.WorkspaceIssueAttachment;

/**
 * WorkspaceIssueAttachment服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceIssueAttachmentService extends IService<WorkspaceIssueAttachment> {

    /**
     * 分页查询事项附件管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceIssueAttachment> pageIssueAttachment(WorkspaceIssueAttachmentQueryDTO query);
}
