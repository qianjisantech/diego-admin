package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.qianjisan.core.Result;
import com.qianjisan.system.entity.SysLoginLog;
import com.qianjisan.system.request.SysLoginLogQueryRequest;
import com.qianjisan.system.service.ILoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 登录日志管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "登录日志管理", description = "LoginLog相关接口")
@RestController
@RequestMapping("/login-log")
@RequiredArgsConstructor
public class SysLoginLogController {

    private final ILoginLogService loginLogService;

    @Operation(summary = "创建登录日志管理")
    @PostMapping
    public Result<SysLoginLog> create(@RequestBody SysLoginLog entity) {
        loginLogService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新登录日志管理")
    @PutMapping("/{id}")
    public Result<SysLoginLog> update(@PathVariable Long id, @RequestBody SysLoginLog entity) {
        entity.setId(id);
        loginLogService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除登录日志管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        loginLogService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询登录日志管理")
    @GetMapping("/{id}")
    public Result<SysLoginLog> getById(@PathVariable Long id) {
        SysLoginLog entity = loginLogService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询登录日志管理列表")
    @GetMapping("/list")
    public Result<List<SysLoginLog>> list() {
        List<SysLoginLog> list = loginLogService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询登录日志管理")
    @PostMapping("/page")
    public Result<Page<SysLoginLog>> page(@RequestBody SysLoginLogQueryRequest query) {
        try {
            Page<SysLoginLog> page = loginLogService.pageLoginLog(query);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
