package com.qianjisan.enterprise.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户与企业关联 Mapper（user_company 表）
 */
@Mapper
public interface UserCompanyMapper {

    @Select("SELECT company_id FROM user_company WHERE user_id = #{userId} AND is_deleted = 0")
    List<Long> selectCompanyIdsByUserId(Long userId);
}


