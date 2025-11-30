package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.LoginLogQueryDTO;
import com.dcp.entity.SysLoginLog;
import com.dcp.mapper.LoginLogMapper;
import com.dcp.service.ILoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * LoginLog服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, SysLoginLog> implements ILoginLogService {

    @Override
    public Page<SysLoginLog> pageLoginLog(LoginLogQueryDTO query) {
        log.info("[分页查询登录日志管理] 查询参数: {}", query);
        Page<SysLoginLog> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<SysLoginLog> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getUserId() != null) {
            queryWrapper.eq(SysLoginLog::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getLoginIp())) {
            queryWrapper.eq(SysLoginLog::getLoginIp, query.getLoginIp());
        }
        if (StringUtils.hasText(query.getStatus())) {
            queryWrapper.eq(SysLoginLog::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysLoginLog::getLoginIp, query.getKeyword())
                .or()
                .like(SysLoginLog::getOs, query.getKeyword())
                .or()
                .like(SysLoginLog::getBrowser, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(SysLoginLog::getLoginTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询登录日志管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
