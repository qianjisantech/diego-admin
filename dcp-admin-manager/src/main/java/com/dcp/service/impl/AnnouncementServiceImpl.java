package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.request.AnnouncementQueryRequest;
import com.dcp.common.request.AnnouncementRequest;
import com.dcp.common.util.PageUtils;
import com.dcp.common.vo.AnnouncementVO;
import com.dcp.entity.Announcement;
import com.dcp.entity.AnnouncementUser;
import com.dcp.mapper.AnnouncementMapper;
import com.dcp.mapper.AnnouncementUserRelationMapper;
import com.dcp.service.IAnnouncementService;
import com.dcp.service.IAnnouncementUserRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Announcement服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements IAnnouncementService {


    private final AnnouncementMapper announcementMapper;
    private final AnnouncementUserRelationMapper announcementUserRelationMapper;
    private final IAnnouncementUserRelationService relationService;

    /**
     * 保存公告
     * @param request
     * @return
     */
    @Override
    public void saveAnnouncement(AnnouncementRequest request) {
        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setType(request.getType());
        announcement.setContent(request.getContent());
        announcement.setType(request.getType());
        announcement.setStatus(request.getStatus());
        announcement.setIsTop(request.getIsTop()==true?1:0);
        announcementMapper.insert(announcement);
    }

    /**
     * 更新公告
     * @param id
     * @param request
     */
    @Override
    public void updateAnnouncementById(Long id, AnnouncementRequest request) {
        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setTitle(request.getTitle());
        announcement.setType(request.getType());
        announcement.setContent(request.getContent());
        announcement.setType(request.getType());
        announcement.setStatus(request.getStatus());
        announcement.setIsTop(request.getIsTop()==true?1:0);
        announcementMapper.updateById(announcement);
    }

    /**
     * 查询最新的未读功能更新公告
     * 查询条件：
     * 1. 类型为系统更新（type=1）
     * 2. 状态为已发布（status=1）
     * 3. 用户未查看（在announcement_user表中没有记录或has_update=0）
     * 4. 按发布时间降序，取最新的一条
     *
     * @param userId 用户ID
     * @return 最新的功能更新公告，如果没有则返回null
     */
    @Override
    public Announcement getLatestUnreadUpdateAnnouncement(Long userId) {
        // 1. 查询所有"系统更新"类型且已发布的公告
        LambdaQueryWrapper<Announcement> announcementWrapper = new LambdaQueryWrapper<>();
        announcementWrapper.eq(Announcement::getType, 0)   // 1 为系统更新
                .eq(Announcement::getStatus, 1) // 状态为已发布
                .orderByDesc(Announcement::getPublishTime); // 按发布时间降序

        List<Announcement> announcements = announcementMapper.selectList(announcementWrapper);

        if (announcements.isEmpty()) {
            log.debug("没有找到系统更新类型的公告");
            return null;
        }

        // 2. 查询用户已查看的公告ID列表（has_update=1）
        LambdaQueryWrapper<AnnouncementUser> relationWrapper = new LambdaQueryWrapper<>();
        relationWrapper.eq(AnnouncementUser::getUserId, userId)
                .eq(AnnouncementUser::getHasUpdate, 1)
                .select(AnnouncementUser::getAnnouncementId);

        List<Long> viewedAnnouncementIds = announcementUserRelationMapper.selectList(relationWrapper)
                .stream()
                .map(AnnouncementUser::getAnnouncementId)
                .collect(Collectors.toList());

        // 3. 过滤出未查看的公告，取第一条（最新的）
        Announcement latestUnread = announcements.stream()
                .filter(announcement -> !viewedAnnouncementIds.contains(announcement.getId()))
                .findFirst()
                .orElse(null);

        if (latestUnread != null) {
            log.info("找到最新未读系统更新公告: id={}, title={}", latestUnread.getId(), latestUnread.getTitle());
        } else {
            log.debug("用户已查看所有系统更新公告: userId={}", userId);
        }

        return latestUnread;
    }

    /**
     * 分页查询公告列表
     *
     * @param request 查询请求参数
     * @return 分页结果
     */
    @Override
    public Page<AnnouncementVO> queryPage(AnnouncementQueryRequest request) {
        // 1. 构建查询条件
        LambdaQueryWrapper<Announcement> queryWrapper = buildQueryWrapper(request);

        // 2. 执行分页查询
        Page<Announcement> page = announcementMapper.selectPage(
            new Page<>(request.getCurrent(), request.getSize()),
            queryWrapper
        );

        // 3. 使用工具类转换为 VO 并返回
        return PageUtils.convertPage(page, this::convertToVO);
    }

    /**
     * 构建查询条件
     *
     * @param request 查询请求
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<Announcement> buildQueryWrapper(AnnouncementQueryRequest request) {
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();

        // 标题模糊查询
        if (StringUtils.hasText(request.getTitle())) {
            queryWrapper.like(Announcement::getTitle, request.getTitle());
        }

        // 内容模糊查询
        if (StringUtils.hasText(request.getContent())) {
            queryWrapper.like(Announcement::getContent, request.getContent());
        }

        // 状态精确查询
        if (request.getStatus() != null) {
            queryWrapper.eq(Announcement::getStatus, request.getStatus());
        }

        // 类型精确查询
        if (request.getType() != null) {
            queryWrapper.eq(Announcement::getType, request.getType());
        }

        // 是否置顶
        if (request.getIsTop() != null) {
            queryWrapper.eq(Announcement::getIsTop, request.getIsTop());
        }

        // 发布者ID查询
        if (request.getPublisherId() != null) {
            queryWrapper.eq(Announcement::getPublisherId, request.getPublisherId());
        }

        // 发布时间范围查询
        if (request.getPublishTimeStart() != null) {
            queryWrapper.ge(Announcement::getPublishTime, request.getPublishTimeStart());
        }
        if (request.getPublishTimeEnd() != null) {
            queryWrapper.le(Announcement::getPublishTime, request.getPublishTimeEnd());
        }

        // 创建时间范围查询
        if (request.getCreateTimeStart() != null) {
            queryWrapper.ge(Announcement::getCreateTime, request.getCreateTimeStart());
        }
        if (request.getCreateTimeEnd() != null) {
            queryWrapper.le(Announcement::getCreateTime, request.getCreateTimeEnd());
        }

        // 排序：置顶优先，然后按创建时间降序
        queryWrapper.orderByDesc(Announcement::getIsTop)
                    .orderByDesc(Announcement::getCreateTime);

        return queryWrapper;
    }

    /**
     * 实体转换为 VO
     *
     * @param announcement 公告实体
     * @return 公告 VO
     */
    private AnnouncementVO convertToVO(Announcement announcement) {
        if (announcement == null) {
            return null;
        }

        AnnouncementVO vo = new AnnouncementVO();
        vo.setId(announcement.getId());
        vo.setTitle(announcement.getTitle());
        vo.setContent(announcement.getContent());
        vo.setType(announcement.getType());
        vo.setStatus(announcement.getStatus());
        vo.setIsTop(announcement.getIsTop());
        vo.setPublisherId(announcement.getPublisherId());
        vo.setPublishTime(announcement.getPublishTime());

        // 设置发布者信息
        vo.setPublisherUsername(announcement.getPublisher_code());
        vo.setPublisherNickname(announcement.getPublisher_code());

        // 设置基础字段
        vo.setCreateTime(announcement.getCreateTime());
        vo.setUpdateTime(announcement.getUpdateTime());
        return vo;
    }

    /**
     * 标记公告为已读
     *
     * @param userId         用户ID
     * @param announcementId 公告ID
     */
    @Override
    public void markAnnouncementAsRead(Long userId, Long announcementId) {
        log.info("标记公告为已读: userId={}, announcementId={}", userId, announcementId);

        if (userId == null) {
            throw new RuntimeException("用户未登录");
        }

        // 1. 获取公告详情
        Announcement announcement = this.getById(announcementId);
        if (announcement == null) {
            throw new RuntimeException("公告不存在");
        }

        // 2. 提取公告的版本号
        String latestVersion = extractVersionFromTitle(announcement.getTitle());

        // 3. 获取用户当前版本
        String currentVersion = "1.0.0"; // 默认版本
        AnnouncementUser latestRelation = relationService.getLatestByUserId(userId);
        if (latestRelation != null && latestRelation.getLatestVersion() != null) {
            currentVersion = latestRelation.getLatestVersion();
        }

        // 4. 更新或创建关联记录
        boolean success = relationService.updateOrCreateRelation(userId, announcementId, currentVersion, latestVersion);

        if (!success) {
            throw new RuntimeException("标记已读失败");
        }

        log.info("标记公告为已读成功: userId={}, announcementId={}, version: {} -> {}",
                userId, announcementId, currentVersion, latestVersion);
    }

    /**
     * 从公告标题中提取版本号
     * 支持格式: "v1.1.0 系统更新"、"系统更新 v1.1.0"、"1.1.0版本更新"等
     *
     * @param title 公告标题
     * @return 版本号，如果无法提取则返回默认版本
     */
    private String extractVersionFromTitle(String title) {
        if (title == null || title.isEmpty()) {
            return "1.0.1";
        }

        // 使用正则表达式提取版本号: v?数字.数字.数字
        Pattern pattern = Pattern.compile("v?(\\d+\\.\\d+\\.\\d+)");
        Matcher matcher = pattern.matcher(title);

        if (matcher.find()) {
            return matcher.group(1);
        }

        // 如果无法提取版本号，返回默认值
        return "1.0.1";
    }
}
