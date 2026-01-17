package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.request.UserSettingsQueryRequest;
import com.qianjisan.console.service.IUserSettingsService;
import com.qianjisan.console.entity.SysUserSettings;
import com.qianjisan.console.mapper.UserSettingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * UserSettings服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class UserSettingsServiceImpl extends ServiceImpl<UserSettingsMapper, SysUserSettings> implements IUserSettingsService {

    @Override
    public Page<SysUserSettings> pageUserSettings(UserSettingsQueryRequest query) {
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
        log.info("[分页查询用户设置] 成功，共 {} �?, page.getTotal());
        return page;
    }
}
