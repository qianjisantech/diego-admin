package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueActivityQueryRequest;
import com.qianjisan.console.entity.IssueActivity;
import com.qianjisan.console.mapper.IssueActivityMapper;
import com.qianjisan.console.service.IIssueActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueActivity服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class IssueActivityServiceImpl extends ServiceImpl<IssueActivityMapper, IssueActivity> implements IIssueActivityService {

    @Override
    public Page<IssueActivity> pageIssueActivity(WorkspaceIssueActivityQueryRequest request) {
        log.info("[分页查询事项活动记录管理] 查询参数: {}", request);
        Page<IssueActivity> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<IssueActivity> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getIssueId() != null) {
            queryWrapper.eq(IssueActivity::getIssueId, request.getIssueId());
        }
        if (StringUtils.hasText(request.getActivityType())) {
            queryWrapper.eq(IssueActivity::getAction, request.getActivityType());
        }
        if (request.getUserId() != null) {
            queryWrapper.eq(IssueActivity::getUserId, request.getUserId());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(IssueActivity::getAction, request.getKeyword())
                .or().like(IssueActivity::getField, request.getKeyword())
                .or().like(IssueActivity::getOldValue, request.getKeyword())
                .or().like(IssueActivity::getNewValue, request.getKeyword())
            );
        }
        queryWrapper.orderByDesc(IssueActivity::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项活动记录管理] 成功，共 {} �?, page.getTotal());
        return page;
    }
}
