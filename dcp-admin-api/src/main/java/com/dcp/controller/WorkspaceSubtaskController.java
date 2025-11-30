package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.dto.WorkspaceSubtaskQueryDTO;
import com.dcp.entity.WorkspaceSubtask;
import com.dcp.service.IWorkspaceSubtaskService;
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
@Tag(name = "子任务管理", description = "WorkspaceSubtask相关接口")
@RestController
@RequestMapping("/workspace/subtask")
@RequiredArgsConstructor
public class WorkspaceSubtaskController {

    private final IWorkspaceSubtaskService workspaceSubtaskService;

    @Operation(summary = "创建子任务管理")
    @PostMapping
    public Result<WorkspaceSubtask> create(@RequestBody WorkspaceSubtask entity) {
        workspaceSubtaskService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新子任务管理")
    @PutMapping("/{id}")
    public Result<WorkspaceSubtask> update(@PathVariable Long id, @RequestBody WorkspaceSubtask entity) {
        entity.setId(id);
        workspaceSubtaskService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除子任务管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        workspaceSubtaskService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询子任务管理")
    @GetMapping("/{id}")
    public Result<WorkspaceSubtask> getById(@PathVariable Long id) {
        WorkspaceSubtask entity = workspaceSubtaskService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询子任务管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceSubtask>> list() {
        List<WorkspaceSubtask> list = workspaceSubtaskService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询子任务管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceSubtask>> page(@RequestBody WorkspaceSubtaskQueryDTO query) {
        try {
            Page<WorkspaceSubtask> page = workspaceSubtaskService.pageSubtask(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
