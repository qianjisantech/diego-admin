package com.qianjisan.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.Result;
import com.qianjisan.system.request.SysBatchUpdateFieldConfigRequest;
import com.qianjisan.system.request.SysConfigQueryRequest;
import com.qianjisan.system.request.SysConfigRequest;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.vo.SysConfigVO;
import com.qianjisan.system.vo.SysFieldConfigVO;
import com.qianjisan.system.mapper.SysFieldConfigMapper;
import com.qianjisan.system.service.ISysConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 配置管理控制�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "配置管理", description = "Config相关接口")
@RestController
@RequestMapping("/rbac/config")
@RequiredArgsConstructor
@Slf4j
public class SysConfigController {

    private final ISysConfigService configService;
    private final SysFieldConfigMapper fieldConfigMapper;

    // ==================== 系统配置管理 ====================

    @Operation(summary = "创建配置")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysConfigRequest request) {
        try {
            configService.saveConfig(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新配置")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody SysConfigRequest request) {
        try {
            configService.updateConfigById(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除配置")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            configService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询配置")
    @PostMapping("/page")
    public Result<PageVO<SysConfigVO>> page(@RequestBody SysConfigQueryRequest request) {
        try {
            Page<SysConfigVO> page = configService.queryConfigPage(request);
            PageVO<SysConfigVO> pageVO = new PageVO<>();
            pageVO.setRecords(page.getRecords());
            pageVO.setTotal(page.getTotal());
            pageVO.setCurrent(page.getCurrent());
            pageVO.setSize(page.getSize());
            pageVO.setPages(page.getPages());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据配置组查询配�?)
    @GetMapping("/group/{group}")
    public Result<List<SysConfigVO>> getByGroup(@PathVariable String group) {
        try {
            List<SysConfigVO> list = configService.getConfigByGroup(group);
            return Result.success(list);
        } catch (Exception e) {
            log.error("根据配置组查询配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    // ==================== 字段配置管理 ====================

    @Operation(summary = "查询字段配置列表")
    @GetMapping("/field-config/list/{moduleCode}")
    public Result<List<SysFieldConfigVO>> getFieldConfigList(@PathVariable String moduleCode) {
        try {
            List<SysFieldConfigVO> list = configService.getFieldConfigList(moduleCode);
            return Result.success(list);
        } catch (Exception e) {
            log.error("查询字段配置列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新字段配置")
    @PutMapping("/field-config/{id}")
    public Result<Void> updateFieldConfig(@PathVariable Long id, @RequestBody SysFieldConfigVO request) {
        try {
            configService.updateFieldConfig(id, request.getIsVisible(), request.getIsRequired(), request.getIsEditable());
            return Result.success();
        } catch (Exception e) {
            log.error("更新字段配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "批量更新字段配置")
    @PutMapping("/field-config/batch")
    public Result<Void> batchUpdateFieldConfig(@Valid @RequestBody SysBatchUpdateFieldConfigRequest request) {
        try {
            configService.batchUpdateFieldConfig(request);
            return Result.success();
        } catch (Exception e) {
            log.error("批量更新字段配置失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
