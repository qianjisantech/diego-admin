package com.qianjisan.enterprise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Enterprise;
import com.qianjisan.enterprise.request.EnterpriseQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseRequest;
import com.qianjisan.enterprise.vo.EnterpriseVo;

public interface IEnterpriseService extends IService<Enterprise> {

    boolean createEnterprise(EnterpriseRequest request);

    boolean updateEnterprise(Long id, EnterpriseRequest request);

    boolean deleteEnterprise(Long id);

    EnterpriseVo getEnterpriseById(Long id);

    PageVO<EnterpriseVo> getEnterprisePage(EnterpriseQueryRequest request);

    Enterprise convertToEntity(EnterpriseRequest request);

    EnterpriseVo convertToVo(Enterprise enterprise);
}