package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.entity.FeedbackComment;
import com.qianjisan.console.mapper.FeedbackCommentMapper;
import com.qianjisan.console.request.FeedbackCommentQueryRequest;
import com.qianjisan.console.service.IFeedbackCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * FeedbackComment服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class FeedbackCommentServiceImpl extends ServiceImpl<FeedbackCommentMapper, FeedbackComment> implements IFeedbackCommentService {

    @Override
    public Page<FeedbackComment> pageFeedbackComment(FeedbackCommentQueryRequest query) {
        log.info("[分页查询反馈评论管理] 查询参数: {}", query);
        Page<FeedbackComment> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<FeedbackComment> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getFeedbackId() != null) {
            queryWrapper.eq(FeedbackComment::getFeedbackId, query.getFeedbackId());
        }
        if (query.getUserId() != null) {
            queryWrapper.eq(FeedbackComment::getUserId, query.getUserId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(FeedbackComment::getContent, query.getKeyword());
        }
        queryWrapper.orderByDesc(FeedbackComment::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询反馈评论管理] 成功，共 {} �?, page.getTotal());
        return page;
    }
}
