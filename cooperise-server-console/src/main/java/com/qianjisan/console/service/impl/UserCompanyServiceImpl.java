package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.dto.SelfUserCompanyDTO;
import com.qianjisan.console.entity.UserCompany;
import com.qianjisan.console.mapper.UserCompanyMapper;
import com.qianjisan.console.service.IUserCompanyService;
import com.qianjisan.console.vo.SelfCompanyVo;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.mapper.CompanyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户-企业关联表 服务实现类
 * @author Auto Generated from SQL
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserCompanyServiceImpl extends ServiceImpl<UserCompanyMapper, UserCompany> implements IUserCompanyService {

    private final UserCompanyMapper userCompanyMapper;

    private  final CompanyMapper companyMapper;



    @Override
    public List<Long> getUserIdsByCompanyId(Long companyId) {
        return userCompanyMapper.selectUserIdsByCompanyId(companyId);
    }

    @Override
    public Long getDefaultCompanyIdByUserId(Long userId) {
        return userCompanyMapper.selectDefaultCompanyIdByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setDefaultCompany(Long userId, Long companyId) {
        try {
            // 1. 先将该用户的所有企业关联的is_default设置为0
            LambdaUpdateWrapper<UserCompany> clearDefaultWrapper = new LambdaUpdateWrapper<>();
            clearDefaultWrapper.eq(UserCompany::getUserId, userId)
                              .set(UserCompany::getIsDefault, 0);
            this.update(clearDefaultWrapper);

            // 2. 将指定的企业设置为默认
            LambdaUpdateWrapper<UserCompany> setDefaultWrapper = new LambdaUpdateWrapper<>();
            setDefaultWrapper.eq(UserCompany::getUserId, userId)
                            .eq(UserCompany::getCompanyId, companyId)
                            .set(UserCompany::getIsDefault, 1);

            boolean result = this.update(setDefaultWrapper);
            if (result) {
                log.info("用户 {} 设置默认企业 {} 成功", userId, companyId);
            } else {
                log.warn("用户 {} 设置默认企业 {} 失败：关联关系不存在", userId, companyId);
            }
            return result;
        } catch (Exception e) {
            log.error("设置用户默认企业失败，用户ID: {}, 企业ID: {}", userId, companyId, e);
            throw e;
        }
    }

    @Override
    public List<SelfUserCompanyDTO> getCompaniesByUserId(Long userId) {

        List<SelfUserCompanyDTO> companies = userCompanyMapper.selectCompaniesByUserId(userId);
        if (companies.isEmpty()) {
            return List.of();
        }else {
            return companies;
        }

    }


    /**
     * 为用户添加企业关联
     *
     * @param userId 用户ID
     * @param companyId 企业ID
     * @param isDefault 是否设为默认企业
     * @return 是否添加成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean addUserCompany(Long userId, Long companyId, boolean isDefault) {
        try {
            // 检查是否已存在关联
            LambdaQueryWrapper<UserCompany> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserCompany::getUserId, userId).eq(UserCompany::getCompanyId, companyId);

            UserCompany existing = this.getOne(queryWrapper);
            if (existing != null) {
                log.info("用户 {} 与企业 {} 的关联已存在", userId, companyId);
                return true;
            }

            // 如果要设为默认，先清除其他默认设置
            if (isDefault) {
                LambdaUpdateWrapper<UserCompany> clearDefaultWrapper = new LambdaUpdateWrapper<>();
                clearDefaultWrapper.eq(UserCompany::getUserId, userId)
                                  .set(UserCompany::getIsDefault, 0);
                this.update(clearDefaultWrapper);
            }

            // 创建新关联
            UserCompany userCompany = new UserCompany();
            userCompany.setUserId(userId);
            userCompany.setCompanyId(companyId);
            userCompany.setIsDefault(isDefault ? 1 : 0);

            boolean result = this.save(userCompany);
            if (result) {
                log.info("为用户 {} 添加企业 {} 关联成功", userId, companyId);
            }
            return result;
        } catch (Exception e) {
            log.error("为用户添加企业关联失败，用户ID: {}, 企业ID: {}", userId, companyId, e);
            throw e;
        }
    }

    /**
     * 移除用户的企业关联
     *
     * @param userId 用户ID
     * @param companyId 企业ID
     * @return 是否移除成功
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean removeUserCompany(Long userId, Long companyId) {
        try {
            LambdaQueryWrapper<UserCompany> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserCompany::getUserId, userId).eq(UserCompany::getCompanyId, companyId);

            boolean result = this.remove(queryWrapper);
            if (result) {
                log.info("移除用户 {} 与企业 {} 的关联成功", userId, companyId);
            }
            return result;
        } catch (Exception e) {
            log.error("移除用户企业关联失败，用户ID: {}, 企业ID: {}", userId, companyId, e);
            throw e;
        }
    }
}
