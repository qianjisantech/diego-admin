package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceSubtaskQueryDTO;
import com.dcp.entity.WorkspaceSubtask;
import com.dcp.mapper.WorkspaceSubtaskMapper;
import com.dcp.service.IWorkspaceSubtaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceSubtask服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class WorkspaceSubtaskServiceImpl extends ServiceImpl<WorkspaceSubtaskMapper, WorkspaceSubtask> implements IWorkspaceSubtaskService {

    @Override
    public Page<WorkspaceSubtask> pageSubtask(WorkspaceSubtaskQueryDTO query) {
        log.info("[分页查询子任务管理] 查询参数: {}", query);
        Page<WorkspaceSubtask> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceSubtask> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceSubtask::getIssueId, query.getIssueId());
        }
        if (query.getCompleted() != null) {
            queryWrapper.eq(WorkspaceSubtask::getCompleted, query.getCompleted());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(WorkspaceSubtask::getTitle, query.getKeyword());
        }
        queryWrapper.orderByDesc(WorkspaceSubtask::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询子任务管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
