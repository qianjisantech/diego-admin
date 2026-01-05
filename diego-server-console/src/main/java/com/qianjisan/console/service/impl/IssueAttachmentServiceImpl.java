package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.common.dto.WorkspaceIssueAttachmentQueryRequest;
import com.qianjisan.console.entity.IssueAttachment;
import com.qianjisan.console.mapper.IssueAttachmentMapper;
import com.qianjisan.console.service.IIssueAttachmentService;
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
public class IssueAttachmentServiceImpl extends ServiceImpl<IssueAttachmentMapper, IssueAttachment> implements IIssueAttachmentService {

    @Override
    public Page<IssueAttachment> pageIssueAttachment(WorkspaceIssueAttachmentQueryRequest request) {
        log.info("[分页查询事项附件管理] 查询参数: {}", request);
        Page<IssueAttachment> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<IssueAttachment> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getIssueId() != null) {
            queryWrapper.eq(IssueAttachment::getIssueId, request.getIssueId());
        }
        if (request.getUploaderId() != null) {
            queryWrapper.eq(IssueAttachment::getUploaderId, request.getUploaderId());
        }
        if (StringUtils.hasText(request.getFileType())) {
            queryWrapper.eq(IssueAttachment::getFileType, request.getFileType());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.like(IssueAttachment::getFileName, request.getKeyword());
        }
        queryWrapper.orderByDesc(IssueAttachment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询事项附件管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
