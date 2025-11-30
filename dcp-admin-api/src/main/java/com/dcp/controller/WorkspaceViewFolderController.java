package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.WorkspaceViewFolderQueryDTO;
import com.dcp.entity.WorkspaceViewFolder;
import com.dcp.service.IWorkspaceViewFolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视图文件夹管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "视图文件夹管理", description = "WorkspaceViewFolder相关接口")
@RestController
@RequestMapping("/workspace/view-folder")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceViewFolderController {

    private final IWorkspaceViewFolderService workspaceViewFolderService;

    @Operation(summary = "创建视图文件夹")
    @PostMapping
    public Result<WorkspaceViewFolder> create(@RequestBody WorkspaceViewFolder entity) {
        try {
            Long userId = UserContextHolder.getUserId();
            WorkspaceViewFolder result = workspaceViewFolderService.createFolder(entity, userId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("创建文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新视图文件夹")
    @PutMapping("/{id}")
    public Result<WorkspaceViewFolder> update(@PathVariable Long id, @RequestBody WorkspaceViewFolder entity) {
        try {
            WorkspaceViewFolder result = workspaceViewFolderService.updateFolder(id, entity);
            return Result.success(result);
        } catch (Exception e) {
            log.error("更新文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除视图文件夹")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            workspaceViewFolderService.deleteFolder(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图文件夹管理")
    @GetMapping("/{id}")
    public Result<WorkspaceViewFolder> getById(@PathVariable Long id) {
        try {
            WorkspaceViewFolder entity = workspaceViewFolderService.getFolderById(id);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("查询文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图文件夹管理列表")
    @GetMapping("/list")
    public Result<List<WorkspaceViewFolder>> list() {
        try {
            log.info("[查询文件夹列表]");
            List<WorkspaceViewFolder> list = workspaceViewFolderService.list();
            return Result.success(list);
        } catch (Exception e) {
            log.error("[查询文件夹列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询视图文件夹管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceViewFolder>> page(@RequestBody WorkspaceViewFolderQueryDTO query) {
        try {
            Page<WorkspaceViewFolder> page = workspaceViewFolderService.pageFolder(query);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
