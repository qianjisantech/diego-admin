package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;

import com.qianjisan.core.utils.BeanConverter;
import com.qianjisan.system.entity.SysTrackingLog;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.mapper.SysTrackingLogMapper;
import com.qianjisan.system.request.SysTrackingLogQueryRequest;
import com.qianjisan.system.request.SysTrackingLogRequest;
import com.qianjisan.system.request.TrackingReportQueryRequest;
import com.qianjisan.system.service.ISysTrackingLogService;
import com.qianjisan.system.service.ISysUserService;
import com.qianjisan.system.vo.EventTypeStatisticsVO;
import com.qianjisan.system.vo.SysTrackingLogVO;
import com.qianjisan.system.vo.UserActivityVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 埋点日志服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysTrackingLogServiceImpl extends ServiceImpl<SysTrackingLogMapper, SysTrackingLog> implements ISysTrackingLogService {

    private final ISysUserService userService;

    @Override
    public Page<SysTrackingLogVO> pageQuery(SysTrackingLogQueryRequest request) {
        Page<SysTrackingLog> page = new Page<>(request.getCurrent(), request.getSize());

        LambdaQueryWrapper<SysTrackingLog> wrapper = new LambdaQueryWrapper<>();

        // 用户ID
        if (request.getUserId() != null) {
            wrapper.eq(SysTrackingLog::getUserId, request.getUserId());
        }

        // 用户名
        if (StringUtils.hasText(request.getUsername())) {
            wrapper.like(SysTrackingLog::getUsername, request.getUsername());
        }

        // 事件类型
        if (StringUtils.hasText(request.getEventType())) {
            wrapper.eq(SysTrackingLog::getEventType, request.getEventType());
        }

        // 事件名称
        if (StringUtils.hasText(request.getEventName())) {
            wrapper.like(SysTrackingLog::getEventName, request.getEventName());
        }

        // 页面URL
        if (StringUtils.hasText(request.getPageUrl())) {
            wrapper.like(SysTrackingLog::getPageUrl, request.getPageUrl());
        }

        // 时间范围
        if (StringUtils.hasText(request.getStartTime())) {
            LocalDateTime startTime = LocalDateTime.parse(request.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.ge(SysTrackingLog::getCreateTime, startTime);
        }
        if (StringUtils.hasText(request.getEndTime())) {
            LocalDateTime endTime = LocalDateTime.parse(request.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            wrapper.le(SysTrackingLog::getCreateTime, endTime);
        }

        // 关键词搜索
        if (StringUtils.hasText(request.getKeyword())) {
            wrapper.and(w -> w
                .like(SysTrackingLog::getEventName, request.getKeyword())
                .or()
                .like(SysTrackingLog::getPageUrl, request.getKeyword())
                .or()
                .like(SysTrackingLog::getPageTitle, request.getKeyword())
                .or()
                .like(SysTrackingLog::getElementText, request.getKeyword())
            );
        }

        // 按创建时间倒序
        wrapper.orderByDesc(SysTrackingLog::getCreateTime);

        Page<SysTrackingLog> resultPage = page(page, wrapper);
        return BeanConverter.convertPage(resultPage, SysTrackingLogVO::new);
    }

    @Override
    public void saveTrackingLog(SysTrackingLogRequest request) {
        SysTrackingLog trackingLog = new SysTrackingLog();
        BeanUtils.copyProperties(request, trackingLog);

        // 获取当前用户信息
        Long userId = UserContextHolder.getUserId();
        if (userId != null) {
            trackingLog.setUserId(userId);
            SysUser user = userService.getById(userId);
            if (user != null) {
                trackingLog.setUsername(user.getName());
                trackingLog.setUserCode(user.getUserCode());
            }
        }

        // 获取IP地址和User-Agent
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest httpRequest = attributes.getRequest();
            trackingLog.setIpAddress(getIpAddress(httpRequest));
            trackingLog.setUserAgent(httpRequest.getHeader("User-Agent"));
        }

        boolean success = save(trackingLog);
        if (!success) {
            throw new BusinessException("保存埋点日志失败");
        }
    }

    @Override
    public SysTrackingLogVO getTrackingLogById(Long id) {
        SysTrackingLog trackingLog = getById(id);
        if (trackingLog == null) {
            throw new BusinessException("埋点日志不存在");
        }
        return BeanConverter.convert(trackingLog, SysTrackingLogVO::new);
    }

    @Override
    public List<EventTypeStatisticsVO> statisticsByEventType(TrackingReportQueryRequest request) {
        log.info("[统计埋点类型数量] 查询参数: {}", request);
        
        // 设置默认时间类型
        String timeType = StringUtils.hasText(request.getTimeType()) ? request.getTimeType() : "day";
        
        // 处理时间格式，如果没有指定时间，默认查询最近30天
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        
        if (!StringUtils.hasText(startTime) || !StringUtils.hasText(endTime)) {
            LocalDateTime now = LocalDateTime.now();
            if (!StringUtils.hasText(endTime)) {
                endTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.hasText(startTime)) {
                startTime = now.minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        } else {
            // 如果只提供了日期，补充时间部分
            if (startTime.length() == 10) {
                startTime = startTime + " 00:00:00";
            }
            if (endTime.length() == 10) {
                endTime = endTime + " 23:59:59";
            }
        }
        
        List<EventTypeStatisticsVO> result = baseMapper.statisticsByEventType(timeType, startTime, endTime);
        // 将事件类型转换为中文
        result.forEach(item -> {
            item.setEventType(convertEventTypeToChinese(item.getEventType()));
        });
        log.info("[统计埋点类型数量] 成功，共 {} 条记录", result.size());
        return result;
    }

    @Override
    public List<UserActivityVO> statisticsUserActivity(SysTrackingLogQueryRequest request) {
        log.info("[统计用户活跃量] 查询参数: {}", request);
        
        // 设置默认时间类型
        String timeType = StringUtils.hasText(request.getTimeType()) ? request.getTimeType() : "day";
        
        // 处理时间格式，如果没有指定时间，默认查询最近30天
        String startTime = request.getStartTime();
        String endTime = request.getEndTime();
        
        if (!StringUtils.hasText(startTime) || !StringUtils.hasText(endTime)) {
            LocalDateTime now = LocalDateTime.now();
            if (!StringUtils.hasText(endTime)) {
                endTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            if (!StringUtils.hasText(startTime)) {
                startTime = now.minusDays(30).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        } else {
            // 如果只提供了日期，补充时间部分
            if (startTime.length() == 10) {
                startTime = startTime + " 00:00:00";
            }
            if (endTime.length() == 10) {
                endTime = endTime + " 23:59:59";
            }
        }
        
        List<UserActivityVO> result = baseMapper.statisticsUserActivity(timeType, startTime, endTime);
        log.info("[统计用户活跃量] 成功，共 {} 条记录", result.size());
        return result;
    }

    /**
     * 将事件类型转换为中文
     *
     * @param eventType 事件类型（英文）
     * @return 中文名称
     */
    private String convertEventTypeToChinese(String eventType) {
        if (eventType == null) {
            return "未知";
        }
        switch (eventType) {
            case "page_view":
                return "页面访问";
            case "login":
                return "登录";
            case "logout":
                return "登出";
            case "button_click":
                return "按钮点击";
            case "form_submit":
                return "表单提交";
            case "file_upload":
                return "文件上传";
            case "custom":
                return "自定义";
            default:
                return eventType;
        }
    }

    /**
     * 获取IP地址
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于多级代理，取第一个IP
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
