package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.dto.UserSettingsQueryDTO;
import com.dcp.entity.SysUserSettings;
import com.dcp.service.IUserSettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户设置管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "用户设置管理", description = "用户设置相关接口")
@RestController
@RequestMapping("/user-settings")
@RequiredArgsConstructor
public class UserSettingsController {

    private final IUserSettingsService userSettingsService;

    @Operation(summary = "创建用户设置")
    @RequiresPermission("settings:edit")
    @PostMapping
    public Result<SysUserSettings> create(@RequestBody SysUserSettings entity) {
        userSettingsService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新用户设置")
    @RequiresPermission("settings:edit")
    @PutMapping("/{id}")
    public Result<SysUserSettings> update(@PathVariable Long id, @RequestBody SysUserSettings entity) {
        entity.setId(id);
        userSettingsService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除用户设置")
    @RequiresPermission("settings:edit")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userSettingsService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询用户设置")
    @RequiresPermission("settings:view")
    @GetMapping("/{id}")
    public Result<SysUserSettings> getById(@PathVariable Long id) {
        SysUserSettings entity = userSettingsService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询用户设置列表")
    @RequiresPermission("settings:view")
    @GetMapping("/list")
    public Result<List<SysUserSettings>> list() {
        List<SysUserSettings> list = userSettingsService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询用户设置")
    @RequiresPermission("settings:view")
    @PostMapping("/page")
    public Result<Page<SysUserSettings>> page(@RequestBody UserSettingsQueryDTO query) {
        try {
            Page<SysUserSettings> page = userSettingsService.pageUserSettings(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
