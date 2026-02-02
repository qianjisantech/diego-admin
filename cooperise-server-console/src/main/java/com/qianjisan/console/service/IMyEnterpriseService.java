package com.qianjisan.console.service;

import com.qianjisan.console.request.MyEnterpriseRequest;
import com.qianjisan.console.vo.EnterpriseInviteInfoVo;
import com.qianjisan.console.vo.EnterpriseVo;
import com.qianjisan.console.vo.UserQuerySelectOptionVo;

import java.util.List;

public interface IMyEnterpriseService {


    List<EnterpriseVo> getMyEnterpriseList(Long userId);


    void  createEnterprise (MyEnterpriseRequest request);

    void setEnterpriseActive(Long enterpriseId);



    List<UserQuerySelectOptionVo> userQuerySelect(Long enterpriseId);
}
