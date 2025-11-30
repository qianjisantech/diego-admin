package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.BannerNotificationQueryDTO;
import com.dcp.common.vo.BannerNotificationVO;
import com.dcp.entity.BannerNotification;

import java.util.List;

/**
 * BannerNotification服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IBannerNotificationService extends IService<BannerNotification> {

    /**
     * 获取当前激活的Banner通知列表
     *
     * @return 激活的Banner列表VO
     */
    List<BannerNotificationVO> getActiveBanners();

    /**
     * 分页查询Banner通知管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<BannerNotification> pageBannerNotification(BannerNotificationQueryDTO query);
}
