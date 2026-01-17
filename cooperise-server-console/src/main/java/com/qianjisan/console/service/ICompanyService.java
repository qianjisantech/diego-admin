package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.request.SelfCompanyRequest;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.request.CompanyQueryRequest;
import com.qianjisan.enterprise.request.CompanyRequest;

import com.qianjisan.enterprise.vo.CompanyVo;

/**
 * 公司表 Service
 */
public interface ICompanyService extends IService<Company> {

    /**
     * 创建公司
     */
    boolean createCompany(SelfCompanyRequest request);

    /**
     * 用户创建公司
     */


    /**
     * 更新公司
     */
    boolean updateCompany(Long id, CompanyRequest request);

    /**
     * 删除公司
     */
    boolean deleteCompany(Long id);

    /**
     * 根据ID查询公司
     */
    CompanyVo getCompanyById(Long id);

    /**
     * 分页查询公司
     */
    PageVO<CompanyVo> getCompanyPage(CompanyQueryRequest request);

    /**
     * 将CompanyRequest转换为Company实体
     */
    Company convertToEntity(CompanyRequest request);

    /**
     * 将Company实体转换为CompanyVo
     */
    CompanyVo convertToVo(Company company);

}

