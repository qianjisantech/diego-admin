package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.entity.FeedbackLike;


/**
 * 反馈点赞服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IFeedbackLikeService extends IService<FeedbackLike> {

    /**
     * 用户是否已点赞该反馈
     *
     * @param feedbackId 反馈ID
     * @param userId 用户ID
     * @return 是否已点赞
     */
    boolean isLiked(Long feedbackId, Long userId);

    /**
     * 点赞反馈
     *
     * @param feedbackId 反馈ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean likeFeedback(Long feedbackId, Long userId);

    /**
     * 取消点赞反馈
     *
     * @param feedbackId 反馈ID
     * @param userId 用户ID
     * @return 是否成功
     */
    boolean unlikeFeedback(Long feedbackId, Long userId);
}
