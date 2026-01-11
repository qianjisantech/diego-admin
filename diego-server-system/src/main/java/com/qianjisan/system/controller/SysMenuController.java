package com.qianjisan.system.controller;

import com.qianjisan.core.Result;
import com.qianjisan.system.request.SysMenuRequest;
import com.qianjisan.system.vo.SysMenuTreeVO;
import com.qianjisan.system.service.ISysMenuService;
import com.qianjisan.system.vo.SysMenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * 菜单管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "菜单管理", description = "Menu相关接口")
@RestController
@RequestMapping("/rbac/menu")
@RequiredArgsConstructor
@Slf4j
public class SysMenuController {

    private final ISysMenuService menuService;

    @Operation(summary = "创建菜单")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysMenuRequest request) {
        try {
            menuService.saveMenu(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建菜单失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新菜单")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody SysMenuRequest request) {
        try {
            menuService.updateMenuById(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新菜单失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            menuService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除菜单失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询菜单")
    @GetMapping("/{id}")
    public Result<SysMenuVO> getById(@PathVariable Long id) {
        try {
            SysMenuVO vo = menuService.getMenuDetail(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询菜单失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    public Result<List<SysMenuTreeVO>> getTree() {
        try {
            List<SysMenuTreeVO> tree = menuService.getMenuTree();
            return Result.success(tree);
        } catch (Exception e) {
            log.error("获取菜单树失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public Result<List<SysMenuVO>> list() {
        try {
            List<SysMenuVO> list = menuService.getMenuList();
            return Result.success(list);
        } catch (Exception e) {
            log.error("获取菜单列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
