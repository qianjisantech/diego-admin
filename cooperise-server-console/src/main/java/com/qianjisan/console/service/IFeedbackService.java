package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.entity.Feedback;
import com.qianjisan.console.entity.FeedbackComment;
import com.qianjisan.console.request.FeedbackQueryRequest;
import com.qianjisan.console.vo.FeedbackPageVo;
import com.qianjisan.console.vo.FeedbackVO;

import java.util.List;

/**
 * Feedback服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IFeedbackService extends IService<Feedback> {

    /**
     * 创建反馈
     *
     * @param entity 反馈实体
     */
    void createFeedback(Feedback entity);

    /**
     * 更新反馈（包含状态变更邮件通知逻辑�?
     *
     * @param id     反馈ID
     * @param entity 反馈实体
     * @return 更新后的反馈
     */
    Feedback updateFeedback(Long id, Feedback entity);

    /**
     * 删除反馈
     *
     * @param id 反馈ID
     */
    void deleteFeedback(Long id);

    /**
     * 根据ID查询反馈
     *
     * @param id 反馈ID
     * @return 反馈实体
     */
    Feedback getFeedbackById(Long id);

    /**
     * 查询反馈列表（包含评论数、点赞数等信息）
     *
     * @param currentUserId 当前用户ID
     * @return 反馈VO列表
     */
    List<FeedbackVO> listFeedbackWithDetails(Long currentUserId);

    /**
     * 分页查询反馈（包含评论数、点赞数等信息）
     *
     * @param query         查询条件
     * @param currentUserId 当前用户ID
     * @return 分页结果
     */
    FeedbackPageVo<FeedbackVO> pageFeedbackWithDetails(FeedbackQueryRequest query, Long currentUserId);

    /**
     * 点赞反馈
     *
     * @param id     反馈ID
     * @param userId 用户ID
     */
    void likeFeedback(Long id, Long userId);

    /**
     * 取消点赞反馈
     *
     * @param id     反馈ID
     * @param userId 用户ID
     */
    void unlikeFeedback(Long id, Long userId);

    /**
     * 获取反馈的评论列�?
     *
     * @param id 反馈ID
     * @return 评论列表
     */
    List<FeedbackComment> getFeedbackComments(Long id);

    /**
     * 添加反馈评论
     *
     * @param feedbackId 反馈ID
     * @param comment    评论实体
     * @param userId     用户ID
     * @return 评论实体
     */
    FeedbackComment addFeedbackComment(Long feedbackId, FeedbackComment comment, Long userId);

    /**
     * 删除反馈评论
     *
     * @param feedbackId 反馈ID
     * @param commentId  评论ID
     */
    void deleteFeedbackComment(Long feedbackId, Long commentId);
}
