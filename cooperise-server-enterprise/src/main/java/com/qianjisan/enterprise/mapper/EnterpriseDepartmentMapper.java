package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.entity.EnterpriseDepartment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司与部门关联表 Mapper
 */
@Mapper
public interface EnterpriseDepartmentMapper extends BaseMapper<EnterpriseDepartment> {
}

