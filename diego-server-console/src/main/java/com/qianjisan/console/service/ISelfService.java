package com.qianjisan.console.service;

import com.qianjisan.console.request.SelfCompanyRequest;
import com.qianjisan.console.vo.SelfCompanyInviteInfoVo;
import com.qianjisan.console.vo.SelfCompanyVo;

import java.util.List;

public interface ISelfService {


    List<SelfCompanyVo> getSelfCompanies(Long userId);


    void  selfCreateCompany (SelfCompanyRequest request);

    void setCompanyActive(Long companyId);

    SelfCompanyInviteInfoVo selfCompanyInviteInfo(Long companyId);
}
