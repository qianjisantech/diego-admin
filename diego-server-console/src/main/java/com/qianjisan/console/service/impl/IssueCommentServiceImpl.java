package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueCommentQueryRequest;
import com.qianjisan.console.entity.IssueComment;
import com.qianjisan.console.mapper.IssueCommentMapper;
import com.qianjisan.console.service.IIssueCommentService;
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
public class IssueCommentServiceImpl extends ServiceImpl<IssueCommentMapper, IssueComment> implements IIssueCommentService {

    @Override
    public Page<IssueComment> pageIssueComment(WorkspaceIssueCommentQueryRequest request) {
        log.info("[分页查询事项评论管理] 查询参数: {}", request);
        Page<IssueComment> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<IssueComment> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getIssueId() != null) {
            queryWrapper.eq(IssueComment::getIssueId, request.getIssueId());
        }
        if (request.getUserId() != null) {
            queryWrapper.eq(IssueComment::getUserId, request.getUserId());
        }
        if (request.getParentId() != null) {
            queryWrapper.eq(IssueComment::getParentId, request.getParentId());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.like(IssueComment::getContent, request.getKeyword());
        }
        queryWrapper.orderByDesc(IssueComment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项评论管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
