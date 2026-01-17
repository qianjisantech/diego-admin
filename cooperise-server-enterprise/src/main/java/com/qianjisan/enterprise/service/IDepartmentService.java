package com.qianjisan.enterprise.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Department;
import com.qianjisan.enterprise.request.DepartmentQueryRequest;
import com.qianjisan.enterprise.request.DepartmentRequest;
import com.qianjisan.enterprise.vo.DepartmentVo;

import java.util.List;

/**
 * 部门表 Service
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 创建部门
     */
    boolean createDepartment(DepartmentRequest request);

    /**
     * 更新部门
     */
    boolean updateDepartment(Long id, DepartmentRequest request);

    /**
     * 删除部门
     */
    boolean deleteDepartment(Long id);

    /**
     * 根据ID查询部门
     */
    DepartmentVo getDepartmentById(Long id);

    /**
     * 分页查询部门
     */
    PageVO<DepartmentVo> getDepartmentPage(DepartmentQueryRequest request);

    /**
     * 根据父部门ID获取子部门列表
     */
    List<DepartmentVo> getChildrenDepartments(Long parentId);

    /**
     * 将DepartmentRequest转换为Department实体
     */
    Department convertToEntity(DepartmentRequest request);

    /**
     * 将Department实体转换为DepartmentVo
     */
    DepartmentVo convertToVo(Department department);
}

