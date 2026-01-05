package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qianjisan.console.entity.Feedback;
import com.qianjisan.console.entity.FeedbackLike;
import com.qianjisan.console.mapper.FeedbackLikeMapper;
import com.qianjisan.console.mapper.FeedbackMapper;
import com.qianjisan.console.service.IFeedbackLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 反馈点赞服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FeedbackLikeServiceImpl extends ServiceImpl<FeedbackLikeMapper, FeedbackLike> implements IFeedbackLikeService {

    private final FeedbackMapper feedbackMapper;

    @Override
    public boolean isLiked(Long feedbackId, Long userId) {
        if (feedbackId == null || userId == null) {
            return false;
        }
        Long count = this.count(new LambdaQueryWrapper<FeedbackLike>()
            .eq(FeedbackLike::getFeedbackId, feedbackId)
            .eq(FeedbackLike::getUserId, userId));
        return count > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean likeFeedback(Long feedbackId, Long userId) {
        if (feedbackId == null || userId == null) {
            return false;
        }

        // 检查是否已点赞
        if (isLiked(feedbackId, userId)) {
            return false;
        }

        // 创建点赞记录
        FeedbackLike like = new FeedbackLike();
        like.setFeedbackId(feedbackId);
        like.setUserId(userId);
        boolean saved = this.save(like);

        if (saved) {
            // 更新反馈的点赞数
            Feedback feedback = feedbackMapper.selectById(feedbackId);
            if (feedback != null) {
                Integer likes = feedback.getLikes() == null ? 0 : feedback.getLikes();
                feedback.setLikes(likes + 1);
                feedbackMapper.updateById(feedback);
            }
        }

        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlikeFeedback(Long feedbackId, Long userId) {
        if (feedbackId == null || userId == null) {
            return false;
        }

        // 检查是否已点赞
        if (!isLiked(feedbackId, userId)) {
            return false;
        }

        // 删除点赞记录
        boolean removed = this.remove(new LambdaQueryWrapper<FeedbackLike>()
            .eq(FeedbackLike::getFeedbackId, feedbackId)
            .eq(FeedbackLike::getUserId, userId));

        if (removed) {
            // 更新反馈的点赞数
            Feedback feedback = feedbackMapper.selectById(feedbackId);
            if (feedback != null) {
                Integer likes = feedback.getLikes() == null ? 0 : feedback.getLikes();
                feedback.setLikes(Math.max(0, likes - 1)); // 确保不会是负数
                feedbackMapper.updateById(feedback);
            }
        }

        return removed;
    }
}
