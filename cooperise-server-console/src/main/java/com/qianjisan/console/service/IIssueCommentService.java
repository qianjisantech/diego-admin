package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.WorkspaceIssueCommentQueryRequest;
import com.qianjisan.console.entity.IssueComment;


/**
 * WorkspaceIssueComment服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueCommentService extends IService<IssueComment> {

    /**
     * 分页查询事项评论管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<IssueComment> pageIssueComment(WorkspaceIssueCommentQueryRequest request);
}
