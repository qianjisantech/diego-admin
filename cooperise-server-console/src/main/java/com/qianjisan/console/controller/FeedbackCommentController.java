package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.console.entity.FeedbackComment;
import com.qianjisan.console.request.FeedbackCommentQueryRequest;
import com.qianjisan.console.service.IFeedbackCommentService;
import com.qianjisan.core.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 反馈评论管理控制�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "反馈评论管理", description = "FeedbackComment相关接口")
@RestController
@RequestMapping("/feedback-comment")
@RequiredArgsConstructor
public class FeedbackCommentController {

    private final IFeedbackCommentService feedbackCommentService;

    @Operation(summary = "创建反馈评论管理")
    @PostMapping
    public Result<FeedbackComment> create(@RequestBody FeedbackComment entity) {
        feedbackCommentService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新反馈评论管理")
    @PutMapping("/{id}")
    public Result<FeedbackComment> update(@PathVariable Long id, @RequestBody FeedbackComment entity) {
        entity.setId(id);
        feedbackCommentService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除反馈评论管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        feedbackCommentService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询反馈评论管理")
    @GetMapping("/{id}")
    public Result<FeedbackComment> getById(@PathVariable Long id) {
        FeedbackComment entity = feedbackCommentService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询反馈评论管理列表")
    @GetMapping("/list")
    public Result<List<FeedbackComment>> list() {
        List<FeedbackComment> list = feedbackCommentService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询反馈评论管理")
    @PostMapping("/page")
    public Result<Page<FeedbackComment>> page(@RequestBody FeedbackCommentQueryRequest request) {
        try {
            Page<FeedbackComment> page = feedbackCommentService.pageFeedbackComment(request);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
