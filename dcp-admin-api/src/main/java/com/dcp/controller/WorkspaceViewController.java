package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.WorkspaceViewQueryDTO;
import com.dcp.common.vo.ViewTreeNodeVO;
import com.dcp.entity.WorkspaceView;
import com.dcp.service.IWorkspaceViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视图管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "视图管理", description = "WorkspaceView相关接口")
@RestController
@RequestMapping("/workspace/view")
@RequiredArgsConstructor
@Slf4j
public class WorkspaceViewController {

    private final IWorkspaceViewService workspaceViewService;

    @Operation(summary = "创建视图管理")
    @PostMapping
    public Result<WorkspaceView> create(@RequestBody WorkspaceView entity) {
        try {
            Long userId = UserContextHolder.getUserId();
            WorkspaceView result = workspaceViewService.createView(entity, userId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("创建视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新视图管理")
    @PutMapping("/{id}")
    public Result<WorkspaceView> update(@PathVariable Long id, @RequestBody WorkspaceView entity) {
        try {
            WorkspaceView result = workspaceViewService.updateView(id, entity);
            return Result.success(result);
        } catch (Exception e) {
            log.error("更新视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除视图管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            workspaceViewService.deleteView(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图管理")
    @GetMapping("/{id}")
    public Result<WorkspaceView> getById(@PathVariable Long id) {
        try {
            WorkspaceView entity = workspaceViewService.getViewById(id);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("查询视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图管理列表（不含文件夹结构）")
    @GetMapping("/list")
    public Result<List<WorkspaceView>> list() {
        try {
            log.info("[查询视图列表]");
            List<WorkspaceView> list = workspaceViewService.list();
            return Result.success(list);
        } catch (Exception e) {
            log.error("[查询视图列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图树形列表（包含文件夹和视图的树形结构）")
    @GetMapping("/tree-list")
    public Result<List<ViewTreeNodeVO>> getTreeList() {
        try {
            Long userId = UserContextHolder.getUserId();
            List<ViewTreeNodeVO> treeList = workspaceViewService.getViewTreeList(userId);
            return Result.success(treeList);
        } catch (Exception e) {
            log.error("查询视图树形列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询视图管理")
    @PostMapping("/page")
    public Result<Page<WorkspaceView>> page(@RequestBody WorkspaceViewQueryDTO query) {
        try {
            Page<WorkspaceView> page = workspaceViewService.pageView(query);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
