package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.entity.CompanyDepartment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司与部门关联表 Mapper
 */
@Mapper
public interface CompanyDepartmentMapper extends BaseMapper<CompanyDepartment> {
}

