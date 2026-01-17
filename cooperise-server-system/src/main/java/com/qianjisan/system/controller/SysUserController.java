package com.qianjisan.system.controller;

import com.qianjisan.system.request.AssignRoleRequest;
import com.qianjisan.system.request.SysUserQueryRequest;
import com.qianjisan.system.request.SysUserRequest;
import com.qianjisan.core.Result;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.vo.SysUserVO;
import com.qianjisan.system.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理Controller
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "用户管理", description = "User相关接口")
@RestController
@RequestMapping("/rbac/user")
@RequiredArgsConstructor
@Slf4j
public class SysUserController {

    private final ISysUserService userService;

    @Operation(summary = "分页查询用户")
    @PostMapping("/page")
    public Result<PageVO<SysUserVO>> page(@RequestBody SysUserQueryRequest request) {
        try {
            PageVO<SysUserVO> pageVO = userService.page(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            return Result.error("查询用户列表失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public Result<SysUserVO> detail(@PathVariable Long id) {
        try {
            SysUserVO vo = userService.getUserDetail(id);
            return Result.success(vo);
        } catch (Exception e) {
            return Result.error("获取用户详情失败: " + e.getMessage());
        }
    }

    @Operation(summary = "分配角色")
    @PostMapping("/{userId}/assign-role")
    public Result<Void> assignRole(
            @PathVariable Long userId,
            @Valid @RequestBody AssignRoleRequest request) {
        try {
            userService.assignRoles(userId, request.getRoleIds());
            return Result.success();
        } catch (Exception e) {
            return Result.error("分配角色失败: " + e.getMessage());
        }
    }

    @Operation(summary = "重置密码")
    @PostMapping("/{userId}/reset-password")
    public Result<Void> resetPassword(@PathVariable Long userId) {
        try {
            userService.resetPassword(userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("重置密码失败: " + e.getMessage());
        }
    }

    @Operation(summary = "更新用户状态")
    @PostMapping("/{userId}/status")
    public Result<Void> updateStatus(
            @PathVariable Long userId,
            @RequestParam Integer status) {
        try {
            userService.updateStatus(userId, status);
            return Result.success();
        } catch (Exception e) {
            return Result.error("更新用户状态失败: " + e.getMessage());
        }
    }

    @Operation(summary = "编辑用户")
    @PutMapping("/{userId}")
    public Result<Void> update(
            @PathVariable Long userId,
            @Valid @RequestBody SysUserRequest request) {
        try {
            log.info("[编辑用户] 用户ID: {}, 请求参数: {}", userId, request);
            userService.updateUser(userId, request);
            log.info("[编辑用户] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[编辑用户] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error("编辑用户失败: " + e.getMessage());
        }
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{userId}")
    public Result<Void> delete(@PathVariable Long userId) {
        try {
            log.info("[删除用户] 用户ID: {}", userId);
            userService.deleteUser(userId);
            log.info("[删除用户] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除用户] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error("删除用户失败: " + e.getMessage());
        }
    }
}
