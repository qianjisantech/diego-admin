package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.Result;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryRequest;
import com.qianjisan.console.entity.IssueSubtask;
import com.qianjisan.console.request.IssueSubtaskRequest;
import com.qianjisan.console.service.IIssueSubtaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 子任务管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "子任务管�?, description = "WorkspaceSubtask相关接口")
@RestController
@RequestMapping("/workspace/subtask")
@RequiredArgsConstructor
public class IssueSubtaskController {

    private final IIssueSubtaskService iIssueSubtaskService;

    @Operation(summary = "创建子任务管�?)
    @PostMapping
    public Result<IssueSubtask> create(@RequestBody IssueSubtaskRequest request) {
        IssueSubtask entity = new IssueSubtask();
        entity.setIssueId(request.getIssueId());
        entity.setTitle(request.getTitle());
        entity.setCompleted(request.getCompleted());
        entity.setSortOrder(request.getSortOrder());
        iIssueSubtaskService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新子任务管�?)
    @PutMapping("/{id}")
    public Result<IssueSubtask> update(@PathVariable Long id, @RequestBody IssueSubtaskRequest request) {
        IssueSubtask entity = new IssueSubtask();
        entity.setId(id);
        entity.setIssueId(request.getIssueId());
        entity.setTitle(request.getTitle());
        entity.setCompleted(request.getCompleted());
        entity.setSortOrder(request.getSortOrder());
        iIssueSubtaskService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除子任务管�?)
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        iIssueSubtaskService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询子任务管�?)
    @GetMapping("/{id}")
    public Result<IssueSubtask> getById(@PathVariable Long id) {
        IssueSubtask entity = iIssueSubtaskService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询子任务管理列�?)
    @GetMapping("/list")
    public Result<List<IssueSubtask>> list() {
        List<IssueSubtask> list = iIssueSubtaskService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询子任务管�?)
    @PostMapping("/page")
    public Result<Page<IssueSubtask>> page(@RequestBody WorkspaceSubtaskQueryRequest request) {
        try {
            Page<IssueSubtask> page = iIssueSubtaskService.pageSubtask(request);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
