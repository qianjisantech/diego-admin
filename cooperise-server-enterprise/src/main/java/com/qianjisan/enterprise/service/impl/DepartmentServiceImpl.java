package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Department;
import com.qianjisan.enterprise.mapper.DepartmentMapper;
import com.qianjisan.enterprise.request.DepartmentQueryRequest;
import com.qianjisan.enterprise.request.DepartmentRequest;
import com.qianjisan.enterprise.service.IDepartmentService;
import com.qianjisan.enterprise.vo.DepartmentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门�?Service 实现
 */
@Slf4j
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Override
    public boolean createDepartment(DepartmentRequest request) {
        Department entity = convertToEntity(request);
        return this.save(entity);
    }

    @Override
    public boolean updateDepartment(Long id, DepartmentRequest request) {
        Department entity = convertToEntity(request);
        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public boolean deleteDepartment(Long id) {
        return this.removeById(id);
    }

    @Override
    public DepartmentVo getDepartmentById(Long id) {
        Department department = this.getById(id);
        return convertToVo(department);
    }

    @Override
    public PageVO<DepartmentVo> getDepartmentPage(DepartmentQueryRequest request) {
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (request.getDeptName() != null && !request.getDeptName().isBlank()) {
            wrapper.like("dept_name", request.getDeptName());
        }
        if (request.getParentId() != null) {
            wrapper.eq("parent_id", request.getParentId());
        }
        Page<Department> page = this.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);
        PageVO<DepartmentVo> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords().stream().map(this::convertToVo).collect(Collectors.toList()));
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setPages(page.getPages());
        return pageVO;
    }

    @Override
    public List<DepartmentVo> getChildrenDepartments(Long parentId) {
        List<Department> list = this.list(new QueryWrapper<Department>().eq("parent_id", parentId));
        return list.stream().map(this::convertToVo).collect(Collectors.toList());
    }

    @Override
    public Department convertToEntity(DepartmentRequest req) {
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

    @Override
    public DepartmentVo convertToVo(Department d) {
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

