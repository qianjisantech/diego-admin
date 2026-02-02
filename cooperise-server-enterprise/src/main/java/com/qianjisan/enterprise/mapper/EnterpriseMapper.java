package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.entity.Enterprise;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公司表 Mapper
 */
@Mapper
public interface EnterpriseMapper extends BaseMapper<Enterprise> {
}

