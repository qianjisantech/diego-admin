package com.dcp.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.Result;
import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.SpaceQueryDTO;
import com.dcp.common.request.SpaceRequest;
import com.dcp.common.vo.PageVO;
import com.dcp.common.vo.SpaceVO;
import com.dcp.service.ISpaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 组织管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "组织管理", description = "组织相关接口")
@RestController
@RequestMapping("/space")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {

    private final ISpaceService spaceService;

    @Operation(summary = "创建组织")
    @RequiresPermission("space:add")
    @PostMapping
    public Result<Void> create(@RequestBody SpaceRequest request) {
        log.info("[创建组织] 请求参数: {}", request);
        spaceService.createSpace(request, UserContextHolder.getUserId());
        return Result.success();
    }

    @Operation(summary = "更新组织")
    @RequiresPermission("space:edit")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody SpaceRequest request) {
        log.info("[更新组织] ID: {}, 请求参数: {}", id, request);
        spaceService.updateSpace(id, request);
        return Result.success();
    }

    @Operation(summary = "删除组织")
    @RequiresPermission("space:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("[删除组织] ID: {}", id);
        spaceService.deleteSpace(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询组织")
    @RequiresPermission("space:view")
    @GetMapping("/{id}")
    public Result<SpaceVO> getById(@PathVariable Long id) {
        log.info("[查询组织] ID: {}", id);
        return Result.success(spaceService.getSpaceById(id));
    }

    @Operation(summary = "查询组织列表")
    @RequiresPermission("space:view")
    @GetMapping("/list")
    public Result<List<SpaceVO>> list() {
        log.info("[查询组织列表]");
        return Result.success(spaceService.listAllSpaces());
    }

    @Operation(summary = "分页查询组织")
    @RequiresPermission("space:view")
    @PostMapping("/page")
    public Result<PageVO<SpaceVO>> page(@RequestBody SpaceQueryDTO query) {
        log.info("[分页查询组织] 查询参数: {}", query);
        Page<SpaceVO> page = spaceService.pageQuery(query);
        return Result.success(PageVO.of(page));
    }

    @Operation(summary = "根据用户ID查询组织")
    @RequiresPermission("space:view")
    @GetMapping("/owner/{ownerId}")
    public Result<List<SpaceVO>> listByOwner(@PathVariable Long ownerId) {
        log.info("[根据用户查询组织] 用户ID: {}", ownerId);
        return Result.success(spaceService.listByOwner(ownerId));
    }
}
