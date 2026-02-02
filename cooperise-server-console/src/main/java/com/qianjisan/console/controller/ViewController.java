package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.console.request.ViewQueryRequest;
import com.qianjisan.console.request.ViewRequest;
import com.qianjisan.console.service.IViewService;
import com.qianjisan.console.vo.ViewPageVO;
import com.qianjisan.console.vo.ViewTreeNodeVO;
import com.qianjisan.console.vo.ViewVO;
import com.qianjisan.core.Result;

import com.qianjisan.core.context.UserContextHolder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "视图管理", description = "WorkspaceView相关接口")
@RestController
@RequestMapping("/console-api/view")
@RequiredArgsConstructor
@Slf4j
public class ViewController {

    private final IViewService viewService;

    @Operation(summary = "创建视图管理")
    @PostMapping
    public Result<Void> create(@RequestBody ViewRequest request) {
        try {
            Long userId = UserContextHolder.getUserId();
            viewService.createView(request, userId);
            return Result.success();
        } catch (Exception e) {
            log.error("创建视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新视图管理")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody ViewRequest request) {
        try {
            viewService.updateView(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除视图管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            viewService.deleteView(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图管理")
    @GetMapping("/{id}")
    public Result<ViewVO> getById(@PathVariable Long id) {
        try {
            ViewVO vo = viewService.getViewById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图管理列表（不含文件夹结构）")
    @GetMapping("/list")
    public Result<List<ViewVO>> list() {
        try {
            log.info("[查询视图列表]");
            List<ViewVO> list = viewService.listViews();
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
            List<ViewTreeNodeVO> treeList = viewService.getViewTreeList(userId);
            return Result.success(treeList);
        } catch (Exception e) {
            log.error("查询视图树形列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询视图管理")
    @PostMapping("/page")
    public Result<Page<ViewPageVO>> page(@RequestBody ViewQueryRequest query) {
        try {
            Page<ViewPageVO> page = viewService.pageView(query);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询视图失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
