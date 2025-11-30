package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.request.AnnouncementQueryRequest;
import com.dcp.common.request.AnnouncementRequest;
import com.dcp.common.vo.AnnouncementVO;
import com.dcp.entity.Announcement;

/**
 * Announcement服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IAnnouncementService extends IService<Announcement> {

    void saveAnnouncement(AnnouncementRequest request);

    void updateAnnouncementById(Long id, AnnouncementRequest request);

    /**
     * 查询最新的未读功能更新公告
     *
     * @param userId 用户ID
     * @return 最新的功能更新公告，如果没有则返回null
     */
    Announcement getLatestUnreadUpdateAnnouncement(Long userId);

    Page<AnnouncementVO> queryPage(AnnouncementQueryRequest request);

    /**
     * 标记公告为已读
     *
     * @param userId         用户ID
     * @param announcementId 公告ID
     */
    void markAnnouncementAsRead(Long userId, Long announcementId);
}
