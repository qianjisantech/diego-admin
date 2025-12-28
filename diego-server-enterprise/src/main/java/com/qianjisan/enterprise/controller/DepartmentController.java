package com.qianjisan.enterprise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.entity.Department;
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
import java.util.stream.Collectors;

/**
 * 企业部门控制器（使用 Request/Vo DTO）
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
            Department entity = toEntity(request);
            departmentService.save(entity);
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
            Department entity = toEntity(request);
            entity.setId(id);
            departmentService.updateById(entity);
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
            departmentService.removeById(id);
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
            Department dept = departmentService.getById(id);
            DepartmentVo vo = toVo(dept);
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
            QueryWrapper<Department> wrapper = new QueryWrapper<>();
            if (request.getDeptName() != null && !request.getDeptName().isBlank()) {
                wrapper.like("dept_name", request.getDeptName());
            }
            if (request.getParentId() != null) {
                wrapper.eq("parent_id", request.getParentId());
            }
            Page<Department> page = departmentService.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);
            PageVO<DepartmentVo> pageVO = new PageVO<>();
            pageVO.setRecords(page.getRecords().stream().map(this::toVo).collect(Collectors.toList()));
            pageVO.setTotal(page.getTotal());
            pageVO.setCurrent(page.getCurrent());
            pageVO.setSize(page.getSize());
            pageVO.setPages(page.getPages());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据父部门ID获取子部门列表")
    @GetMapping("/children/{parentId}")
    public Result<List<DepartmentVo>> getChildren(@PathVariable Long parentId) {
        try {
            List<Department> list = departmentService.list(new QueryWrapper<Department>().eq("parent_id", parentId));
            List<DepartmentVo> vos = list.stream().map(this::toVo).collect(Collectors.toList());
            return Result.success(vos);
        } catch (Exception e) {
            log.error("获取子部门失败", e);
            return Result.error(e.getMessage());
        }
    }

    private Department toEntity(DepartmentRequest req) {
        if (req == null) return null;
        Department d = new Department();
        d.setDeptCode(req.getDeptCode());
        d.setDeptName(req.getDeptName());
        d.setParentId(req.getParentId());
        d.setDescription(req.getDescription());
        d.setSortOrder(req.getSortOrder());
        d.setStatus(req.getStatus());
        d.setLeaderId(req.getLeaderId());
        d.setLeaderName(req.getLeaderName());
        d.setLeaderCode(req.getLeaderCode());
        return d;
    }

    private DepartmentVo toVo(Department d) {
        if (d == null) return null;
        DepartmentVo v = new DepartmentVo();
        v.setId(d.getId());
        v.setDeptCode(d.getDeptCode());
        v.setDeptName(d.getDeptName());
        v.setParentId(d.getParentId());
        v.setDescription(d.getDescription());
        v.setSortOrder(d.getSortOrder());
        v.setStatus(d.getStatus());
        v.setLeaderId(d.getLeaderId());
        v.setLeaderName(d.getLeaderName());
        v.setLeaderCode(d.getLeaderCode());
        return v;
    }
}


