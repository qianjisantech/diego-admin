package com.qianjisan.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.Result;
import com.qianjisan.system.request.AssignPermissionRequest;
import com.qianjisan.system.request.SysRoleQueryRequest;
import com.qianjisan.system.request.SysRoleRequest;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.vo.SysRoleVO;
import com.qianjisan.system.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Tag(name = "角色管理", description = "Role相关接口")
@RestController
@RequestMapping("/system-api/role")
@RequiredArgsConstructor
@Slf4j
public class SysRoleController {

    private final ISysRoleService roleService;

    @Operation(summary = "创建角色")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysRoleRequest request) {
        try {
            roleService.saveRole(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建角色失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新角色")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody SysRoleRequest request) {
        try {
            roleService.updateRoleById(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新角色失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            roleService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除角色失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询角色")
    @GetMapping("/{id}")
    public Result<SysRoleVO> getById(@PathVariable Long id) {
        try {
            SysRoleVO vo = roleService.getRoleDetail(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询角色失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询角色")
    @PostMapping("/page")
    public Result<PageVO<SysRoleVO>> page(@RequestBody SysRoleQueryRequest request) {
        try {
            Page<SysRoleVO> page = roleService.queryPage(request);
            PageVO<SysRoleVO> pageVO = new PageVO<>();
            pageVO.setRecords(page.getRecords());
            pageVO.setTotal(page.getTotal());
            pageVO.setCurrent(page.getCurrent());
            pageVO.setSize(page.getSize());
            pageVO.setPages(page.getPages());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询角色失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取所有角色列表")
    @GetMapping("/list")
    public Result<List<SysRoleVO>> list() {
        try {
            List<SysRoleVO> list = roleService.getRoleList();
            return Result.success(list);
        } catch (Exception e) {
            log.error("获取角色列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分配角色权限")
    @PostMapping("/{roleId}/assign-permission")
    public Result<Void> assignPermission(@PathVariable Long roleId, @Valid @RequestBody AssignPermissionRequest request) {
        try {
            roleService.assignPermissions(roleId,request);
            return Result.success();
        } catch (Exception e) {
            log.error("分配角色权限失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取角色已分配的菜单ID列表")
    @GetMapping("/{roleId}/menu-ids")
    public Result<List<Long>> getRoleMenuIds(@PathVariable Long roleId) {
        try {
            List<Long> menuIds = roleService.getRoleMenuIds(roleId);
            return Result.success(menuIds);
        } catch (Exception e) {
            log.error("获取角色菜单权限失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
