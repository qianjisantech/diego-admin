package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门表 Mapper
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {
}

