package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.UserSettingsQueryDTO;
import com.dcp.entity.SysUserSettings;
import com.dcp.mapper.UserSettingsMapper;
import com.dcp.service.IUserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * UserSettings服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, SysUserSettings> implements IUserSettingsService {

    @Override
    public Page<SysUserSettings> pageUserSettings(UserSettingsQueryDTO query) {
        log.info("[分页查询用户设置] 查询参数: {}", query);
        Page<SysUserSettings> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<SysUserSettings> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getUserId() != null) {
            queryWrapper.eq(SysUserSettings::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getLanguage())) {
            queryWrapper.eq(SysUserSettings::getLanguage, query.getLanguage());
        }
        if (StringUtils.hasText(query.getTheme())) {
            queryWrapper.eq(SysUserSettings::getTheme, query.getTheme());
        }
        queryWrapper.orderByDesc(SysUserSettings::getUpdateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询用户设置] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
