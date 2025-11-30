package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceIssueActivityQueryDTO;
import com.dcp.entity.WorkspaceIssueActivity;
import com.dcp.mapper.WorkspaceIssueActivityMapper;
import com.dcp.service.IWorkspaceIssueActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueActivity服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class WorkspaceIssueActivityServiceImpl extends ServiceImpl<WorkspaceIssueActivityMapper, WorkspaceIssueActivity> implements IWorkspaceIssueActivityService {

    @Override
    public Page<WorkspaceIssueActivity> pageIssueActivity(WorkspaceIssueActivityQueryDTO query) {
        log.info("[分页查询事项活动记录管理] 查询参数: {}", query);
        Page<WorkspaceIssueActivity> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceIssueActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceIssueActivity::getIssueId, query.getIssueId());
        }
        if (StringUtils.hasText(query.getActivityType())) {
            queryWrapper.eq(WorkspaceIssueActivity::getAction, query.getActivityType());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(WorkspaceIssueActivity::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(WorkspaceIssueActivity::getAction, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getField, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getOldValue, query.getKeyword())
                .or().like(WorkspaceIssueActivity::getNewValue, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(WorkspaceIssueActivity::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项活动记录管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
