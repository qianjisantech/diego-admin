package com.qianjisan.console.controller;

import com.qianjisan.console.entity.Feedback;
import com.qianjisan.console.entity.FeedbackComment;
import com.qianjisan.console.request.FeedbackQueryRequest;
import com.qianjisan.console.service.IFeedbackService;
import com.qianjisan.console.vo.FeedbackPageVo;
import com.qianjisan.console.vo.FeedbackVO;
import com.qianjisan.core.Result;
import com.qianjisan.annotation.RequiresPermission;

import com.qianjisan.core.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反馈管理控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "反馈管理", description = "Feedback相关接口")
@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
@Slf4j
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @Operation(summary = "创建反馈管理")
    @RequiresPermission("feedback:add")
    @PostMapping
    public Result<Feedback> create(@RequestBody Feedback entity) {
        try {
            feedbackService.createFeedback(entity);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("创建反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新反馈管理")
    @RequiresPermission("feedback:edit")
    @PutMapping("/{id}")
    public Result<Feedback> update(@PathVariable Long id, @RequestBody Feedback entity) {
        try {
            Feedback result = feedbackService.updateFeedback(id, entity);
            return Result.success(result);
        } catch (Exception e) {
            log.error("更新反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除反馈管理")
    @RequiresPermission("feedback:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询反馈管理")
    @RequiresPermission("feedback:view")
    @GetMapping("/{id}")
    public Result<Feedback> getById(@PathVariable Long id) {
        try {
            Feedback entity = feedbackService.getFeedbackById(id);
            return Result.success(entity);
        } catch (Exception e) {
            log.error("查询反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询反馈管理列表")
    @RequiresPermission("feedback:view")
    @GetMapping("/list")
    public Result<List<FeedbackVO>> list() {
        try {
            Long currentUserId = UserContextHolder.getUserId();
            List<FeedbackVO> voList = feedbackService.listFeedbackWithDetails(currentUserId);
            return Result.success(voList);
        } catch (Exception e) {
            log.error("查询反馈列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询反馈管理")
    @RequiresPermission("feedback:view")
    @PostMapping("/page")
    public Result<FeedbackPageVo<FeedbackVO>> page(@RequestBody FeedbackQueryRequest query) {
        try {
            Long currentUserId = UserContextHolder.getUserId();
            FeedbackPageVo<FeedbackVO> page = feedbackService.pageFeedbackWithDetails(query, currentUserId);
            return Result.success(page);
        } catch (Exception e) {
            log.error("分页查询反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "点赞反馈")
    @PostMapping("/{id}/like")
    public Result<Void> likeFeedback(@PathVariable Long id) {
        try {
            Long userId = UserContextHolder.getUserId();
            feedbackService.likeFeedback(id, userId);
            return Result.success();
        } catch (Exception e) {
            log.error("点赞反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "取消点赞反馈")
    @PostMapping("/{id}/unlike")
    public Result<Void> unlikeFeedback(@PathVariable Long id) {
        try {
            Long userId = UserContextHolder.getUserId();
            feedbackService.unlikeFeedback(id, userId);
            return Result.success();
        } catch (Exception e) {
            log.error("取消点赞反馈失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取反馈的评论列表")
    @GetMapping("/{id}/comments")
    public Result<List<FeedbackComment>> getComments(@PathVariable Long id) {
        try {
            List<FeedbackComment> comments = feedbackService.getFeedbackComments(id);
            return Result.success(comments);
        } catch (Exception e) {
            log.error("获取反馈评论列表失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "添加反馈评论")
    @PostMapping("/{id}/comments")
    public Result<FeedbackComment> addComment(@PathVariable Long id, @RequestBody FeedbackComment comment) {
        try {
            Long userId = UserContextHolder.getUserId();
            FeedbackComment result = feedbackService.addFeedbackComment(id, comment, userId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("添加反馈评论失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除反馈评论")
    @RequiresPermission("feedback:comment:delete")
    @DeleteMapping("/{feedbackId}/comments/{commentId}")
    public Result<Void> deleteComment(@PathVariable Long feedbackId, @PathVariable Long commentId) {
        try {
            feedbackService.deleteFeedbackComment(feedbackId, commentId);
            return Result.success();
        } catch (Exception e) {
            log.error("删除反馈评论失败，失败原因：{}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}
