package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.dto.SelfUserCompanyDTO;
import com.qianjisan.console.entity.UserCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户-企业关联�?Mapper 接口
 *
 * 根据SQL表结构自动生成：
 * CREATE TABLE `user_company` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `company_id` bigint NOT NULL COMMENT '企业ID',
 *   `user_id` bigint NOT NULL COMMENT '用户ID',
 *   `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否默认部门 0为否 1为是',
 *   `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *   PRIMARY KEY (`id`) USING BTREE,
 *   UNIQUE KEY `uk_company_dept` (`company_id`,`user_id`) USING BTREE,
 *   KEY `department_id` (`user_id`) USING BTREE
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
 *
 * @author Auto Generated from SQL
 * @since 2024-12-20
 */
@Mapper
public interface UserCompanyMapper extends BaseMapper<UserCompany> {

    /**
     * 根据用户ID查询企业ID列表
     *
     * @param userId 用户ID
     * @return 企业ID列表 SelfUserCompanyDTO
     */
    @Select("SELECT c.id,c.company_name,c.company_code,uc.is_default FROM user_company as uc LEFT JOIN company as c ON uc.company_id=c.id WHERE c.is_deleted=0 and uc.user_id= #{userId}")
    List<SelfUserCompanyDTO> selectCompaniesByUserId(Long userId);

    /**
     * 根据企业ID查询用户ID列表
     *
     * @param companyId 企业ID
     * @return 用户ID列表
     */
    @Select("SELECT user_id FROM user_company WHERE company_id = #{companyId}")
    List<Long> selectUserIdsByCompanyId(Long companyId);

    /**
     * 查询用户的默认企业ID
     *
     * @param userId 用户ID
     * @return 默认企业ID
     */
    @Select("SELECT company_id FROM user_company WHERE  is_deleted=1 and user_id = #{userId} AND is_default = 1 LIMIT 1")
    Long selectDefaultCompanyIdByUserId(Long userId);
}
