package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.dto.SelfUserCompanyDTO;
import com.qianjisan.console.entity.UserCompany;
import com.qianjisan.console.vo.SelfCompanyVo;
import com.qianjisan.enterprise.entity.Company;

import java.util.List;

/**
 * 用户-企业关联�?服务�?
 *

 * @author Auto Generated from SQL
 * @since 2024-12-20
 */
public interface IUserCompanyService extends IService<UserCompany> {


    /**
     * 根据企业ID查询用户ID列表
     *
     * @param companyId 企业ID
     * @return 用户ID列表
     */
    List<Long> getUserIdsByCompanyId(Long companyId);

    /**
     * 查询用户的默认企业ID
     *
     * @param userId 用户ID
     * @return 默认企业ID
     */
    Long getDefaultCompanyIdByUserId(Long userId);

    /**
     * 设置用户的默认企�?
     *
     * @param userId 用户ID
     * @param companyId 企业ID
     * @return 是否设置成功
     */
    boolean setDefaultCompany(Long userId, Long companyId);

    /**
     * 查询用户的默认企业列�?
     *
     * @param userId 用户ID
     * @return 企业列表
     */
    List<SelfUserCompanyDTO> getCompaniesByUserId(Long userId);

}
