package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryRequest;
import com.qianjisan.console.entity.IssueSubtask;
import com.qianjisan.console.mapper.IssueSubtaskMapper;
import com.qianjisan.console.service.IIssueSubtaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceSubtask服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueSubtaskServiceImpl extends ServiceImpl<IssueSubtaskMapper, IssueSubtask> implements IIssueSubtaskService {

    @Override
    public Page<IssueSubtask> pageSubtask(WorkspaceSubtaskQueryRequest request) {
        log.info("[分页查询子任务管理] 查询参数: {}", request);
        Page<IssueSubtask> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<IssueSubtask> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getIssueId() != null) {
            queryWrapper.eq(IssueSubtask::getIssueId, request.getIssueId());
        }
        if (request.getCompleted() != null) {
            queryWrapper.eq(IssueSubtask::getCompleted, request.getCompleted());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.like(IssueSubtask::getTitle, request.getKeyword());
        }
        queryWrapper.orderByDesc(IssueSubtask::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询子任务管理] 成功，共 {} �?, page.getTotal());
        return page;
    }
}
