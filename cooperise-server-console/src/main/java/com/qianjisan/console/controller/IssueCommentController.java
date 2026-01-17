package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.dto.WorkspaceIssueCommentQueryRequest;
import com.qianjisan.core.Result;
import com.qianjisan.console.entity.IssueComment;
import com.qianjisan.console.request.IssueCommentRequest;
import com.qianjisan.console.service.IIssueCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项评论管理控制�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项评论管理", description = "WorkspaceIssueComment相关接口")
@RestController
@RequestMapping("/workspace/issue-comment")
@RequiredArgsConstructor
public class IssueCommentController {

    private final IIssueCommentService workspaceIssueCommentService;

    @Operation(summary = "创建事项评论管理")
    @PostMapping
    public Result<IssueComment> create(@RequestBody IssueCommentRequest request) {
        IssueComment entity = new IssueComment();
        entity.setIssueId(request.getIssueId());
        entity.setUserId(request.getUserId());
        entity.setContent(request.getContent());
        entity.setParentId(request.getParentId());
        workspaceIssueCommentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项评论管理")
    @PutMapping("/{id}")
    public Result<IssueComment> update(@PathVariable Long id, @RequestBody IssueCommentRequest request) {
        IssueComment entity = new IssueComment();
        entity.setId(id);
        entity.setIssueId(request.getIssueId());
        entity.setUserId(request.getUserId());
        entity.setContent(request.getContent());
        entity.setParentId(request.getParentId());
        workspaceIssueCommentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项评论管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workspaceIssueCommentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项评论管理")
    @GetMapping("/{id}")
    public Result<IssueComment> getById(@PathVariable Long id) {
        IssueComment entity = workspaceIssueCommentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项评论管理列表")
    @GetMapping("/list")
    public Result<List<IssueComment>> list() {
        List<IssueComment> list = workspaceIssueCommentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项评论管理")
    @PostMapping("/page")
    public Result<Page<IssueComment>> page(@RequestBody WorkspaceIssueCommentQueryRequest request) {
        try {
            Page<IssueComment> page = workspaceIssueCommentService.pageIssueComment(request);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
