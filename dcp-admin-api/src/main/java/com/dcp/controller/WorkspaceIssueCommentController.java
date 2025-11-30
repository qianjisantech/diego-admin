package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.dto.WorkspaceIssueCommentQueryDTO;
import com.dcp.entity.WorkspaceIssueComment;
import com.dcp.service.IWorkspaceIssueCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项评论管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项评论管理", description = "WorkspaceIssueComment相关接口")
@RestController
@RequestMapping("/workspace/issue-comment")
@RequiredArgsConstructor
public class WorkspaceIssueCommentController {

    private final IWorkspaceIssueCommentService workspaceIssueCommentService;

    @Operation(summary = "创建事项评论管理")
    @PostMapping
    public Result<WorkspaceIssueComment> create(@RequestBody WorkspaceIssueComment entity) {
        workspaceIssueCommentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项评论管理")
    @PutMapping("/{id}")
    public Result<WorkspaceIssueComment> update(@PathVariable Long id, @RequestBody WorkspaceIssueComment entity) {
        entity.setId(id);
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
    public Result<WorkspaceIssueComment> getById(@PathVariable Long id) {
        WorkspaceIssueComment entity = workspaceIssueCommentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项评论管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceIssueComment>> list() {
        List<WorkspaceIssueComment> list = workspaceIssueCommentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项评论管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceIssueComment>> page(@RequestBody WorkspaceIssueCommentQueryDTO query) {
        try {
            Page<WorkspaceIssueComment> page = workspaceIssueCommentService.pageIssueComment(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
