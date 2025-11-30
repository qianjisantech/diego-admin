package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.BannerNotificationQueryDTO;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.BannerNotificationVO;
import com.dcp.entity.BannerNotification;
import com.dcp.mapper.BannerNotificationMapper;
import com.dcp.service.IBannerNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * BannerNotification服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class BannerNotificationServiceImpl extends ServiceImpl<BannerNotificationMapper, BannerNotification> implements IBannerNotificationService {

    @Override
    public List<BannerNotificationVO> getActiveBanners() {
        LambdaQueryWrapper<BannerNotification> queryWrapper = new LambdaQueryWrapper<>();
        LocalDateTime now = LocalDateTime.now();
        queryWrapper.eq(BannerNotification::getStatus, "active")
                .le(BannerNotification::getStartTime, now)
                .ge(BannerNotification::getEndTime, now)
                .orderByDesc(BannerNotification::getCreateTime);
        List<BannerNotification> list = list(queryWrapper);
        return BeanConverter.convertList(list, BannerNotificationVO::new);
    }

    @Override
    public Page<BannerNotification> pageBannerNotification(BannerNotificationQueryDTO query) {
        log.info("[分页查询Banner通知管理] 查询参数: {}", query);
        Page<BannerNotification> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<BannerNotification> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getStatus())) {
            queryWrapper.eq(BannerNotification::getStatus, query.getStatus());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(BannerNotification::getType, query.getType());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(BannerNotification::getTitle, query.getKeyword())
                .or()
                .like(BannerNotification::getContent, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(BannerNotification::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询Banner通知管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
