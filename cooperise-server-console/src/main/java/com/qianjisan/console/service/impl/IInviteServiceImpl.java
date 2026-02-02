package com.qianjisan.console.service.impl;

import com.qianjisan.console.service.IInviteService;
import com.qianjisan.console.vo.EnterpriseInviteInfoVo;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.Enterprise;
import com.qianjisan.enterprise.mapper.EnterpriseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IInviteServiceImpl  implements IInviteService {
    private  final EnterpriseMapper enterpriseMapper;

    @Override
    public EnterpriseInviteInfoVo getEnterpriseInviteInfo(Long EnterpriseId) {
        EnterpriseInviteInfoVo enterpriseInviteInfoVo = new EnterpriseInviteInfoVo();

        UserContext user = UserContextHolder.getUser();
        enterpriseInviteInfoVo.setInvitePersonId(user.getUserId());
        enterpriseInviteInfoVo.setInvitePersonName(user.getUsername());
        enterpriseInviteInfoVo.setInvitePersonCode(user.getUserCode());

        Enterprise Enterprise = enterpriseMapper.selectById(EnterpriseId);

        enterpriseInviteInfoVo.setId(Enterprise.getId());
        enterpriseInviteInfoVo.setName(Enterprise.getName());
        enterpriseInviteInfoVo.setCode(Enterprise.getCode());

        return enterpriseInviteInfoVo;
    }
}
