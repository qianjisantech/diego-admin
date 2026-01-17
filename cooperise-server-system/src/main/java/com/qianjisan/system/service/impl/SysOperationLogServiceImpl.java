package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.utils.BeanConverter;
import com.qianjisan.system.entity.SysOperationLog;
import com.qianjisan.system.mapper.SysOperationLogMapper;
import com.qianjisan.system.request.SysOperationLogRequest;
import com.qianjisan.system.service.ISysOperationLogService;
import com.qianjisan.system.vo.SysOperationLogVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 操作日志服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
public class SysOperationLogServiceImpl extends ServiceImpl<SysOperationLogMapper, SysOperationLog> implements ISysOperationLogService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Page<SysOperationLogVO> pageQuery(SysOperationLogRequest request) {
        Page<SysOperationLog> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<SysOperationLog> queryWrapper = new LambdaQueryWrapper<>();

        // 用户ID条件
        if (request.getUserId() != null) {
            queryWrapper.eq(SysOperationLog::getUserId, request.getUserId());
        }

        // 用户名条�?
        if (StringUtils.hasText(request.getUsername())) {
            queryWrapper.like(SysOperationLog::getUsername, request.getUsername());
        }

        // 请求方法条件
        if (StringUtils.hasText(request.getRequestMethod())) {
            queryWrapper.eq(SysOperationLog::getRequestMethod, request.getRequestMethod());
        }

        // 请求路径条件(模糊查询)
        if (StringUtils.hasText(request.getRequestUrl())) {
            queryWrapper.like(SysOperationLog::getRequestUrl, request.getRequestUrl());
        }

        // IP地址条件
        if (StringUtils.hasText(request.getIpAddress())) {
            queryWrapper.eq(SysOperationLog::getIpAddress, request.getIpAddress());
        }

        // 时间范围条件
        if (StringUtils.hasText(request.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(request.getStartTime(), DATE_TIME_FORMATTER);
            queryWrapper.ge(SysOperationLog::getCreateTime, startTime);
        }
        if (StringUtils.hasText(request.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(request.getEndTime(), DATE_TIME_FORMATTER);
            queryWrapper.le(SysOperationLog::getCreateTime, endTime);
        }

        // 关键词搜�?
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysOperationLog::getUsername, request.getKeyword())
                .or()
                .like(SysOperationLog::getRequestUrl, request.getKeyword())
                .or()
                .like(SysOperationLog::getIpAddress, request.getKeyword())
            );
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(SysOperationLog::getCreateTime);

        Page<SysOperationLog> resultPage = this.page(page, queryWrapper);
        return BeanConverter.convertPage(resultPage, SysOperationLogVO::new);
    }

    @Override
    public void saveLog(SysOperationLog sysOperationLog) {
        this.save(sysOperationLog);
    }
}
