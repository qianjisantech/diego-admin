package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.console.request.ViewFolderQueryRequest;
import com.qianjisan.console.request.ViewFolderRequest;
import com.qianjisan.console.service.IViewFolderService;
import com.qianjisan.console.vo.ViewFolderPageVO;
import com.qianjisan.console.vo.ViewFolderVO;
import com.qianjisan.core.Result;

import com.qianjisan.core.context.UserContextHolder;
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
@Tag(name = "视图文件夹管�?, description = "WorkspaceViewFolder相关接口")
@RestController
@RequestMapping("/workspace/view-folder")
@RequiredArgsConstructor
@Slf4j
public class ViewFolderController {

    private final IViewFolderService viewFolderService;

    @Operation(summary = "创建视图文件�?)
    @PostMapping
    public Result<Void> create(@RequestBody ViewFolderRequest request) {
        try {
            Long userId = UserContextHolder.getUserId();
            viewFolderService.createFolder(request, userId);
            return Result.success();
        } catch (Exception e) {
            log.error("创建文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新视图文件�?)
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody ViewFolderRequest request) {
        try {
            viewFolderService.updateFolder(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除视图文件�?)
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            viewFolderService.deleteFolder(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询视图文件夹管�?)
    @GetMapping("/{id}")
    public Result<ViewFolderVO> getById(@PathVariable Long id) {
        try {
            ViewFolderVO vo = viewFolderService.getFolderById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询视图文件夹管理列�?)
    @GetMapping("/list")
    public Result<List<ViewFolderVO>> list() {
        try {
            log.info("[查询文件夹列表]");
            List<ViewFolderVO> list = viewFolderService.listFolders();
            return Result.success(list);
        } catch (Exception e) {
            log.error("[查询文件夹列表] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询视图文件夹管�?)
    @PostMapping("/page")
    public Result<Page<ViewFolderPageVO>> page(@RequestBody ViewFolderQueryRequest request) {
        try {
            Page<ViewFolderPageVO> page = viewFolderService.pageFolder(request);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询文件夹失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
