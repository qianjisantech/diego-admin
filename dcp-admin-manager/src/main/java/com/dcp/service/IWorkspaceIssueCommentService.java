package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceIssueCommentQueryDTO;
import com.dcp.entity.WorkspaceIssueComment;

/**
 * WorkspaceIssueComment服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceIssueCommentService extends IService<WorkspaceIssueComment> {

    /**
     * 分页查询事项评论管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceIssueComment> pageIssueComment(WorkspaceIssueCommentQueryDTO query);
}
