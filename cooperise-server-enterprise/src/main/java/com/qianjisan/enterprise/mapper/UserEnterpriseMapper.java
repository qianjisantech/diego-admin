package com.qianjisan.enterprise.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.enterprise.dto.SelfUserEnterpriseDTO;
import com.qianjisan.enterprise.entity.UserEnterprise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserEnterpriseMapper extends BaseMapper<UserEnterprise> {

    @Select("SELECT c.id,c.name,c.code,uc.is_default,uc.created_time FROM user_enterprise as uc LEFT JOIN enterprise as c ON uc.enterprise_id=c.id WHERE c.is_deleted=0 and uc.user_id= #{userId}")
    List<SelfUserEnterpriseDTO> selectEnterprisesByUserId(Long userId);

    @Select("SELECT user_id FROM user_enterprise WHERE enterprise_id = #{enterpriseId}")
    List<Long> selectUserIdsByEnterpriseId(Long enterpriseId);

    @Select("SELECT enterprise_id FROM user_enterprise WHERE is_deleted=1 and user_id = #{userId} AND is_default = 1 LIMIT 1")
    Long selectDefaultEnterpriseIdByUserId(Long userId);
}