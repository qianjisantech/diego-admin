package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianjisan.console.dto.SelfUserCompanyDTO;
import com.qianjisan.console.entity.UserCompany;
import com.qianjisan.console.mapper.UserCompanyMapper;
import com.qianjisan.console.request.SelfCompanyRequest;
import com.qianjisan.console.service.ISelfService;
import com.qianjisan.console.service.IUserCompanyService;
import com.qianjisan.console.vo.SelfCompanyInviteInfoVo;
import com.qianjisan.console.vo.SelfCompanyVo;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SelfServiceImpl implements ISelfService {
    private  final IUserCompanyService userCompanyService;
    private  final UserCompanyMapper userCompanyMapper;

    private  final CompanyMapper companyMapper;
    @Override
    public List<SelfCompanyVo> getSelfCompanies(Long userId) {

        List<SelfUserCompanyDTO> companies = userCompanyService.getCompaniesByUserId(userId);
        if (companies.isEmpty()) {
            return  List.of();
        }else {
           return companies.stream().map(company -> {
                SelfCompanyVo selfCompanyVo = new SelfCompanyVo();
                selfCompanyVo.setId(company.getId());
                selfCompanyVo.setCompanyName(company.getCompanyName());
                selfCompanyVo.setCompanyCode(company.getCompanyCode());
                selfCompanyVo.setIsDefault(company.getIsDefault() == 1);
                return selfCompanyVo;
            }).toList();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void selfCreateCompany(SelfCompanyRequest request) {
        Company company = new Company();

        // 生成10位数字企业代码，确保唯一性
        String companyCode = UUID.randomUUID().toString();
        company.setCompanyCode(companyCode);
        company.setCompanyName(request.getCompanyName());
        company.setCompanySize(request.getCompanySize());
        company.setIndustry(request.getIndustry());
        company.setStatus(0);
        companyMapper.insert(company); //新增公司

        Long currentUserId = UserContextHolder.getUserId();

        UserCompany userCompany = new UserCompany();
        userCompany.setCompanyId(company.getId());
        userCompany.setUserId(currentUserId);
        userCompanyMapper.insert(userCompany);   //新增用户和公司的关联关系


    }

    @Override
    public void setCompanyActive(Long companyId) {
        Long currentUserId = UserContextHolder.getUserId();

        // 首先将该用户所有企业的 isDefault 设为 0
        LambdaQueryWrapper<UserCompany> resetWrapper = new LambdaQueryWrapper<>();
        resetWrapper.eq(UserCompany::getUserId, currentUserId);
        UserCompany resetUserCompany = new UserCompany();
        resetUserCompany.setIsDefault(0);
        userCompanyService.update(resetUserCompany, resetWrapper);

        // 然后将指定企业的 isDefault 设为 1
        LambdaQueryWrapper<UserCompany> setActiveWrapper = new LambdaQueryWrapper<>();
        setActiveWrapper.eq(UserCompany::getCompanyId, companyId);
        setActiveWrapper.eq(UserCompany::getUserId, currentUserId);
        UserCompany activeUserCompany = new UserCompany();
        activeUserCompany.setIsDefault(1);
        userCompanyService.update(activeUserCompany, setActiveWrapper);

    }

    @Override
    public SelfCompanyInviteInfoVo selfCompanyInviteInfo(Long companyId) {

        SelfCompanyInviteInfoVo selfCompanyInviteInfoVo = new SelfCompanyInviteInfoVo();

        UserContext user = UserContextHolder.getUser();
        selfCompanyInviteInfoVo.setInvitePersonId(user.getUserId());
        selfCompanyInviteInfoVo.setInvitePersonName(user.getUsername());
        selfCompanyInviteInfoVo.setInvitePersonCode(user.getUserCode());

        Company company = companyMapper.selectById(companyId);

        selfCompanyInviteInfoVo.setCompanyId(company.getId());
        selfCompanyInviteInfoVo.setCompanyName(company.getCompanyName());
        selfCompanyInviteInfoVo.setCompanyCode(company.getCompanyCode());


        return selfCompanyInviteInfoVo;
    }


}
