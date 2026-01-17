package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.DepartmentQueryRequest;
import com.qianjisan.enterprise.request.DepartmentRequest;
import com.qianjisan.enterprise.service.IDepartmentService;
import com.qianjisan.enterprise.vo.DepartmentVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业部门控制器（使用 Request/Vo DTO�?
 */
@Tag(name = "企业部门", description = "Company Department 相关接口")
@RestController
@RequestMapping("/enterprise/department")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final IDepartmentService departmentService;

    @Operation(summary = "创建部门")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody DepartmentRequest request) {
        try {
            departmentService.createDepartment(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新部门")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
        try {
            departmentService.updateDepartment(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询部门")
    @GetMapping("/{id}")
    public Result<DepartmentVo> getById(@PathVariable Long id) {
        try {
            DepartmentVo vo = departmentService.getDepartmentById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询部门")
    @PostMapping("/page")
    public Result<PageVO<DepartmentVo>> page(@RequestBody DepartmentQueryRequest request) {
        try {
            PageVO<DepartmentVo> pageVO = departmentService.getDepartmentPage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据父部门ID获取子部门列�?)
    @GetMapping("/children/{parentId}")
    public Result<List<DepartmentVo>> getChildren(@PathVariable Long parentId) {
        try {
            List<DepartmentVo> vos = departmentService.getChildrenDepartments(parentId);
            return Result.success(vos);
        } catch (Exception e) {
            log.error("获取子部门失�?, e);
            return Result.error(e.getMessage());
        }
    }
}

