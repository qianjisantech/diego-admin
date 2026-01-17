package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.Result;
import com.qianjisan.common.dto.WorkspaceIssueAttachmentQueryRequest;
import com.qianjisan.console.entity.IssueAttachment;
import com.qianjisan.console.request.IssueAttachmentRequest;
import com.qianjisan.console.service.IIssueAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项附件管理控制�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项附件管理", description = "WorkspaceIssueAttachment相关接口")
@RestController
@RequestMapping("/workspace/issue-attachment")
@RequiredArgsConstructor
public class IssueAttachmentController {

    private final IIssueAttachmentService issueAttachmentService;

    @Operation(summary = "创建事项附件管理")
    @PostMapping
    public Result<IssueAttachment> create(@RequestBody IssueAttachmentRequest request) {
        IssueAttachment entity = new IssueAttachment();
        entity.setIssueId(request.getIssueId());
        entity.setFileName(request.getFileName());
        entity.setFilePath(request.getFilePath());
        entity.setFileSize(request.getFileSize());
        entity.setFileType(request.getFileType());
        entity.setUploaderId(request.getUploaderId());
        issueAttachmentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项附件管理")
    @PutMapping("/{id}")
    public Result<IssueAttachment> update(@PathVariable Long id, @RequestBody IssueAttachmentRequest request) {
        IssueAttachment entity = new IssueAttachment();
        entity.setId(id);
        entity.setIssueId(request.getIssueId());
        entity.setFileName(request.getFileName());
        entity.setFilePath(request.getFilePath());
        entity.setFileSize(request.getFileSize());
        entity.setFileType(request.getFileType());
        entity.setUploaderId(request.getUploaderId());
        issueAttachmentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项附件管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        issueAttachmentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项附件管理")
    @GetMapping("/{id}")
    public Result<IssueAttachment> getById(@PathVariable Long id) {
       IssueAttachment entity = issueAttachmentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项附件管理列表")
    @GetMapping("/list")
    public Result<List<IssueAttachment>> list() {
        List<IssueAttachment> list = issueAttachmentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项附件管理")
    @PostMapping("/page")
    public Result<Page<IssueAttachment>> page(@RequestBody WorkspaceIssueAttachmentQueryRequest request) {
        try {
            Page<IssueAttachment> page = issueAttachmentService.pageIssueAttachment(request);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
