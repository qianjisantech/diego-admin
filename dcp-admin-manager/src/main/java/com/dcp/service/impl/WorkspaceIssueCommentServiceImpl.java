package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceIssueCommentQueryDTO;
import com.dcp.entity.WorkspaceIssueComment;
import com.dcp.mapper.WorkspaceIssueCommentMapper;
import com.dcp.service.IWorkspaceIssueCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueComment服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class WorkspaceIssueCommentServiceImpl extends ServiceImpl<WorkspaceIssueCommentMapper, WorkspaceIssueComment> implements IWorkspaceIssueCommentService {

    @Override
    public Page<WorkspaceIssueComment> pageIssueComment(WorkspaceIssueCommentQueryDTO query) {
        log.info("[分页查询事项评论管理] 查询参数: {}", query);
        Page<WorkspaceIssueComment> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceIssueComment> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceIssueComment::getIssueId, query.getIssueId());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(WorkspaceIssueComment::getUserId, query.getUserId());
        }
        if (query.getParentId() != null) {
            queryWrapper.eq(WorkspaceIssueComment::getParentId, query.getParentId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(WorkspaceIssueComment::getContent, query.getKeyword());
        }
        queryWrapper.orderByDesc(WorkspaceIssueComment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项评论管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
