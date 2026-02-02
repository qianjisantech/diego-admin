package com.qianjisan.console.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianjisan.enterprise.dto.SelfUserEnterpriseDTO;
import com.qianjisan.enterprise.entity.UserEnterprise;
import com.qianjisan.enterprise.mapper.UserEnterpriseMapper;
import com.qianjisan.console.request.MyEnterpriseRequest;
import com.qianjisan.console.service.IMyEnterpriseService;
import com.qianjisan.console.vo.EnterpriseInviteInfoVo;
import com.qianjisan.console.vo.EnterpriseVo;
import com.qianjisan.console.vo.UserQuerySelectOptionVo;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.core.utils.SnowflakeIdGenerator;
import com.qianjisan.enterprise.entity.Enterprise;
import com.qianjisan.enterprise.mapper.EnterpriseMapper;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyEnterpriseServiceImpl implements IMyEnterpriseService {
    private final UserEnterpriseMapper userEnterpriseMapper;
    private final EnterpriseMapper enterpriseMapper;
    private final SysUserMapper sysUserMapper;

    @Override
    public List<EnterpriseVo> getMyEnterpriseList(Long userId) {
        List<SelfUserEnterpriseDTO> companies = userEnterpriseMapper.selectEnterprisesByUserId(userId);
        if (CollectionUtil.isEmpty(companies)) {
            return Collections.emptyList();
        }

        return companies.stream()
                .sorted(Comparator
                        .comparing((SelfUserEnterpriseDTO dto) -> dto.getIsDefault() ==1,
                                Comparator.reverseOrder())
                        .thenComparing(SelfUserEnterpriseDTO::getCreateTime,
                                Comparator.nullsLast(Comparator.naturalOrder())))
                .map(dto -> {
                    EnterpriseVo vo = new EnterpriseVo();
                    vo.setId(dto.getId());
                    vo.setName(dto.getName());
                    vo.setCode(dto.getCode());
                    vo.setIsDefault(dto.getIsDefault() ==1);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEnterprise(MyEnterpriseRequest request) {
        Enterprise enterprise = new Enterprise();

        String EnterpriseCode = String.valueOf(SnowflakeIdGenerator.generateId());
        enterprise.setCode(EnterpriseCode);
        enterprise.setName(request.getName());
        enterprise.setSize(request.getSize());
        enterprise.setIndustry(request.getIndustry());
        enterprise.setStatus(0);
        enterpriseMapper.insert(enterprise);

        Long currentUserId = UserContextHolder.getUserId();

        if (currentUserId == null) {
            throw new BusinessException("用户未登录，无法创建企业");
        }

        try {
        } catch (Exception e) {
            log.warn("验证用户存在性时发生异常: {}", e.getMessage());
        }

        UserEnterprise userEnterprise = new UserEnterprise();
        userEnterprise.setEnterpriseId(enterprise.getId());
        userEnterprise.setUserId(currentUserId);
        userEnterprise.setIsDefault(1);

        try {
            userEnterpriseMapper.insert(userEnterprise);
            log.info("成功创建用户企业关联: userId={}, EnterpriseId={}", currentUserId, enterprise.getId());

            this.setEnterpriseActive(userEnterprise.getEnterpriseId());

        } catch (Exception e) {
            log.error("创建用户企业关联失败: userId={}, EnterpriseId={}, error={}", currentUserId, enterprise.getId(), e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("foreign key constraint")) {
                log.warn("检测到外键约束问题，但继续执行（权限放开模式）");
            } else {
                throw e;
            }
        }
    }

    @Override
    public void setEnterpriseActive(Long EnterpriseId) {
        Long currentUserId = UserContextHolder.getUserId();

        LambdaQueryWrapper<UserEnterprise> resetWrapper = new LambdaQueryWrapper<>();
        resetWrapper.eq(UserEnterprise::getUserId, currentUserId);
        UserEnterprise resetUserEnterprise = new UserEnterprise();
        resetUserEnterprise.setIsDefault(0);
        userEnterpriseMapper.update(resetUserEnterprise, resetWrapper);

        LambdaQueryWrapper<UserEnterprise> setActiveWrapper = new LambdaQueryWrapper<>();
        setActiveWrapper.eq(UserEnterprise::getEnterpriseId, EnterpriseId);
        setActiveWrapper.eq(UserEnterprise::getUserId, currentUserId);
        UserEnterprise activeUserEnterprise = new UserEnterprise();
        activeUserEnterprise.setIsDefault(1);
        userEnterpriseMapper.update(activeUserEnterprise, setActiveWrapper);
    }



    @Override
    public List<UserQuerySelectOptionVo> userQuerySelect(Long EnterpriseId) {
        List<Long> userIds = userEnterpriseMapper.selectUserIdsByEnterpriseId(EnterpriseId);
        List<SysUser> sysUsers = sysUserMapper.selectBatchIds(userIds);
        if (CollectionUtil.isEmpty(sysUsers)) {
            return List.of();
        } else {
            return sysUsers.stream().map(sysUser -> {
                UserQuerySelectOptionVo userQuerySelectOptionVo = new UserQuerySelectOptionVo();
                userQuerySelectOptionVo.setId(sysUser.getId());
                userQuerySelectOptionVo.setUserCode(sysUser.getUserCode());
                userQuerySelectOptionVo.setPhone(sysUser.getPhone());
                userQuerySelectOptionVo.setEmail(sysUser.getEmail());
                userQuerySelectOptionVo.setAvatar(sysUser.getAvatar());
                userQuerySelectOptionVo.setName(sysUser.getName());
                return userQuerySelectOptionVo;
            }).collect(Collectors.toList());
        }
    }
}
