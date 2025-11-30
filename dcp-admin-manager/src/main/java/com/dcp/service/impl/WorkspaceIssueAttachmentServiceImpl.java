package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceIssueAttachmentQueryDTO;
import com.dcp.entity.WorkspaceIssueAttachment;
import com.dcp.mapper.WorkspaceIssueAttachmentMapper;
import com.dcp.service.IWorkspaceIssueAttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceIssueAttachment服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class WorkspaceIssueAttachmentServiceImpl extends ServiceImpl<WorkspaceIssueAttachmentMapper, WorkspaceIssueAttachment> implements IWorkspaceIssueAttachmentService {

    @Override
    public Page<WorkspaceIssueAttachment> pageIssueAttachment(WorkspaceIssueAttachmentQueryDTO query) {
        log.info("[分页查询事项附件管理] 查询参数: {}", query);
        Page<WorkspaceIssueAttachment> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceIssueAttachment> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getIssueId() != null) {
            queryWrapper.eq(WorkspaceIssueAttachment::getIssueId, query.getIssueId());
        }
        if (query.getUploaderId() != null) {
            queryWrapper.eq(WorkspaceIssueAttachment::getUploaderId, query.getUploaderId());
        }
        if (StringUtils.hasText(query.getFileType())) {
            queryWrapper.eq(WorkspaceIssueAttachment::getFileType, query.getFileType());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(WorkspaceIssueAttachment::getFileName, query.getKeyword());
        }
        queryWrapper.orderByDesc(WorkspaceIssueAttachment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项附件管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
