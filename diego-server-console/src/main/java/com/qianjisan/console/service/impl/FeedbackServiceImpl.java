package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.entity.Feedback;
import com.qianjisan.console.entity.FeedbackComment;
import com.qianjisan.console.mapper.FeedbackMapper;
import com.qianjisan.console.request.FeedbackQueryRequest;
import com.qianjisan.console.service.IFeedbackCommentService;
import com.qianjisan.console.service.IFeedbackLikeService;
import com.qianjisan.console.service.IFeedbackService;
import com.qianjisan.console.vo.FeedbackPageVo;
import com.qianjisan.console.vo.FeedbackVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Feedback服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    private final IFeedbackCommentService feedbackCommentService;
    private final IFeedbackLikeService feedbackLikeService;
    // TODO: 需要实现邮件服务和用户服务
    // private final IAsyncEmailService asyncEmailService;
    // private final ISysUserService sysUserService;

    @Override
    public void createFeedback(Feedback entity) {
        log.info("[创建反馈] 请求参数: {}", entity);
        // 参数校验
        if (!StringUtils.hasText(entity.getTitle())) {
            throw new RuntimeException("反馈标题不能为空");
        }
        if (!StringUtils.hasText(entity.getContent())) {
            throw new RuntimeException("反馈内容不能为空");
        }
        this.save(entity);
        log.info("[创建反馈] 成功，反馈ID: {}", entity.getId());
    }

    @Override
    public Feedback updateFeedback(Long id, Feedback entity) {
        log.info("[更新反馈] ID: {}, 请求参数: {}", id, entity);

        // 检查反馈是否存在
        Feedback existFeedback = this.getById(id);
        if (existFeedback == null) {
            throw new RuntimeException("反馈不存在");
        }

        // 检查状态是否从1变成2
        // TODO: 需要实现邮件服务和用户服务
        // Integer oldStatus = existFeedback.getStatus();
        // Integer newStatus = entity.getStatus();
        // 只有当status从1（待处理）变成2（已关闭）时才发送邮件通知
        // boolean statusChangedFrom1To2 = (oldStatus != null && oldStatus == 1
        //         && newStatus != null && newStatus == 2);

        entity.setId(id);
        this.updateById(entity);
        log.info("[更新反馈] 成功");

        // 如果状态从1变成2，异步发送邮件通知给创建人
        // TODO: 需要实现邮件服务和用户服务
        // if (statusChangedFrom1To2 && existFeedback.getSubmitterId() != null) {
        //     try {
        //         // 获取创建人信息
        //         SysUser submitter = sysUserService.getById(existFeedback.getSubmitterId());
        //         if (submitter != null && StringUtils.hasText(submitter.getEmail())) {
        //             // 异步发送邮件通知
        //             asyncEmailService.sendFeedbackStatusChangeNotificationAsync(
        //                     submitter.getEmail(),
        //                     id,
        //                     existFeedback.getTitle() != null ? existFeedback.getTitle() : "反馈#" + id,
        //                     oldStatus,
        //                     newStatus
        //             );
        //             log.info("[更新反馈] 已发送状态变更邮件通知，收件人: {}", submitter.getEmail());
        //         } else {
        //             log.warn("[更新反馈] 创建人邮箱为空，无法发送邮件通知，创建人ID: {}", existFeedback.getSubmitterId());
        //         }
        //     } catch (Exception e) {
        //         // 邮件发送失败不影响主流程，只记录日志
        //         log.error("[更新反馈] 发送状态变更邮件通知失败，反馈ID: {}, 错误: {}", id, e.getMessage(), e);
        //     }
        // }

        return entity;
    }

    @Override
    public void deleteFeedback(Long id) {
        log.info("[删除反馈] ID: {}", id);
        // 检查反馈是否存在
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        this.removeById(id);
        log.info("[删除反馈] 成功");
    }

    @Override
    public Feedback getFeedbackById(Long id) {
        log.info("[查询反馈] ID: {}", id);
        Feedback entity = this.getById(id);
        if (entity == null) {
            throw new RuntimeException("反馈不存在");
        }
        return entity;
    }

    @Override
    public List<FeedbackVO> listFeedbackWithDetails(Long currentUserId) {
        log.info("[查询反馈列表]");
        List<Feedback> list = this.list();
        // 转换为 VO 并添加评论数、点赞数和点赞状态
        return list.stream()
                .map(feedback -> convertToVO(feedback, currentUserId))
                .collect(Collectors.toList());
    }

    @Override
    public FeedbackPageVo<FeedbackVO> pageFeedbackWithDetails(FeedbackQueryRequest query, Long currentUserId) {
        log.info("[分页查询反馈] 查询参数: {}", query);
        Page<Feedback> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<Feedback> queryWrapper = new LambdaQueryWrapper<>();

        if (query.getType() != null) {
            queryWrapper.eq(Feedback::getType, query.getType());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq(Feedback::getStatus, query.getStatus());
        }
        if (query.getPriority() != null) {
            queryWrapper.eq(Feedback::getPriority, query.getPriority());
        }
        if (query.getSubmitterId() != null) {
            queryWrapper.eq(Feedback::getSubmitterId, query.getSubmitterId());
        }
        if (query.getAssigneeId() != null) {
            queryWrapper.eq(Feedback::getAssigneeId, query.getAssigneeId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                    .like(Feedback::getTitle, query.getKeyword())
                    .or()
                    .like(Feedback::getContent, query.getKeyword())
            );
        }

        queryWrapper.orderByDesc(Feedback::getCreateTime);

        page = this.page(page, queryWrapper);

        // 转换为 VO Page 并添加评论数、点赞数和点赞状态
        FeedbackPageVo<FeedbackVO> voPage = new FeedbackPageVo<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<FeedbackVO> voList = page.getRecords().stream()
                .map(feedback -> convertToVO(feedback, currentUserId))
                .collect(Collectors.toList());
        voPage.setRecords(voList);

        // 计算统计信息：基于相同的查询条件（排除status条件）
        // 构建基础查询条件的Supplier（用于统计）
        Supplier<LambdaQueryWrapper<Feedback>> buildBaseWrapper = () -> {
            LambdaQueryWrapper<Feedback> wrapper = new LambdaQueryWrapper<>();
            if (query.getType() != null) {
                wrapper.eq(Feedback::getType, query.getType());
            }
            if (query.getPriority() != null) {
                wrapper.eq(Feedback::getPriority, query.getPriority());
            }
            if (query.getSubmitterId() != null) {
                wrapper.eq(Feedback::getSubmitterId, query.getSubmitterId());
            }
            if (query.getAssigneeId() != null) {
                wrapper.eq(Feedback::getAssigneeId, query.getAssigneeId());
            }
            if (StringUtils.hasText(query.getKeyword())) {
                wrapper.and(w -> w
                        .like(Feedback::getTitle, query.getKeyword())
                        .or()
                        .like(Feedback::getContent, query.getKeyword())
                );
            }
            return wrapper;
        };

        // 统计总数
        LambdaQueryWrapper<Feedback> allWrapper = buildBaseWrapper.get();
        long all = this.count(allWrapper);
        voPage.setAll(all);

        // 统计已关闭数量（status=2）
        LambdaQueryWrapper<Feedback> closedWrapper = buildBaseWrapper.get();
        closedWrapper.eq(Feedback::getStatus, 2);
        long closed = this.count(closedWrapper);
        voPage.setClosed(closed);

        // 统计打开数量（status=1）
        LambdaQueryWrapper<Feedback> openedWrapper = buildBaseWrapper.get();
        openedWrapper.eq(Feedback::getStatus, 1);
        long opened = this.count(openedWrapper);
        voPage.setOpened(opened);
       voPage.setPages(page.getPages());
        log.info("[分页查询反馈] 成功，共 {} 条，全部: {}, 已关闭: {}, 打开: {}", 
                voPage.getTotal(), all, closed, opened);
        return voPage;
    }

    @Override
    public void likeFeedback(Long id, Long userId) {
        log.info("[点赞反馈] 反馈ID: {}", id);
        // 检查反馈是否存在
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        boolean success = feedbackLikeService.likeFeedback(id, userId);
        if (!success) {
            throw new RuntimeException("已经点赞过了");
        }
        log.info("[点赞反馈] 成功");
    }

    @Override
    public void unlikeFeedback(Long id, Long userId) {
        log.info("[取消点赞反馈] 反馈ID: {}", id);
        // 检查反馈是否存在
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        boolean success = feedbackLikeService.unlikeFeedback(id, userId);
        if (!success) {
            throw new RuntimeException("还未点赞");
        }
        log.info("[取消点赞反馈] 成功");
    }

    @Override
    public List<FeedbackComment> getFeedbackComments(Long id) {
        log.info("[获取反馈评论列表] 反馈ID: {}", id);
        // 检查反馈是否存在
        Feedback feedback = this.getById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        List<FeedbackComment> comments = feedbackCommentService.list(
                new LambdaQueryWrapper<FeedbackComment>()
                        .eq(FeedbackComment::getFeedbackId, id)
                        .orderByDesc(FeedbackComment::getCreateTime)
        );
        log.info("[获取反馈评论列表] 成功，共 {} 条", comments.size());
        return comments;
    }

    @Override
    public FeedbackComment addFeedbackComment(Long feedbackId, FeedbackComment comment, Long userId) {
        log.info("[添加反馈评论] 反馈ID: {}, 评论内容: {}", feedbackId, comment.getContent());
        // 检查反馈是否存在
        Feedback feedback = this.getById(feedbackId);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        // 参数校验
        if (!StringUtils.hasText(comment.getContent())) {
            throw new RuntimeException("评论内容不能为空");
        }
        comment.setFeedbackId(feedbackId);
        comment.setUserId(userId);
        feedbackCommentService.save(comment);
        log.info("[添加反馈评论] 成功");
        return comment;
    }

    @Override
    public void deleteFeedbackComment(Long feedbackId, Long commentId) {
        log.info("[删除反馈评论] 反馈ID: {}, 评论ID: {}", feedbackId, commentId);
        // 检查评论是否存在
        FeedbackComment comment = feedbackCommentService.getById(commentId);
        if (comment == null) {
            throw new RuntimeException("评论不存在");
        }
        // 检查评论是否属于该反馈
        if (!comment.getFeedbackId().equals(feedbackId)) {
            throw new RuntimeException("评论不属于该反馈");
        }
        feedbackCommentService.removeById(commentId);
        log.info("[删除反馈评论] 成功");
    }

    /**
     * 转换为VO并添加评论数、点赞数和点赞状态
     */
    private FeedbackVO convertToVO(Feedback feedback, Long currentUserId) {
        FeedbackVO vo = new FeedbackVO();
        BeanUtils.copyProperties(feedback, vo);
        // 统计评论数
        Integer commentCount = Math.toIntExact(feedbackCommentService.count(
                new LambdaQueryWrapper<FeedbackComment>()
                        .eq(FeedbackComment::getFeedbackId, feedback.getId())
        ));
        vo.setComments(commentCount);
        vo.setCreatedBy(feedback.getCreateByName());
        vo.setUpdatedBy(feedback.getUpdateByName());
        // 设置点赞数
        vo.setLikes(feedback.getLikes() != null ? feedback.getLikes() : 0);
        // 设置当前用户是否已点赞
        vo.setLiked(feedbackLikeService.isLiked(feedback.getId(), currentUserId));
        return vo;
    }
}
