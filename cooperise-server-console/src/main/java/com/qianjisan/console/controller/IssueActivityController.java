package com.qianjisan.console.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.common.dto.WorkspaceIssueActivityQueryRequest;
import com.qianjisan.console.entity.IssueActivity;
import com.qianjisan.console.request.IssueActivityRequest;
import com.qianjisan.console.service.IIssueActivityService;
import com.qianjisan.core.Result;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 事项活动记录管理控制�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项活动记录管理", description = "WorkspaceIssueActivity相关接口")
@RestController
@RequestMapping("/workspace/issue-activity")
@RequiredArgsConstructor
public class IssueActivityController {

    private final IIssueActivityService issueActivityService;

    @Operation(summary = "创建事项活动记录管理")
    @PostMapping
    public Result<IssueActivity> create(@RequestBody IssueActivityRequest request) {
        IssueActivity entity = new IssueActivity();
        entity.setIssueId(request.getIssueId());
        entity.setUserId(request.getUserId());
        entity.setAction(request.getAction());
        entity.setField(request.getField());
        entity.setOldValue(request.getOldValue());
        entity.setNewValue(request.getNewValue());
        issueActivityService.save(entity);
        return Result.success(entity);
    }

    @Operation(summary = "更新事项活动记录管理")
    @PutMapping("/{id}")
    public Result<IssueActivity> update(@PathVariable Long id, @RequestBody IssueActivityRequest request) {
        IssueActivity entity = new IssueActivity();
        entity.setId(id);
        entity.setIssueId(request.getIssueId());
        entity.setUserId(request.getUserId());
        entity.setAction(request.getAction());
        entity.setField(request.getField());
        entity.setOldValue(request.getOldValue());
        entity.setNewValue(request.getNewValue());
        issueActivityService.updateById(entity);
        return Result.success(entity);
    }

    @Operation(summary = "删除事项活动记录管理")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        issueActivityService.removeById(id);
        return Result.success();
    }

    @Operation(summary = "根据ID查询事项活动记录管理")
    @GetMapping("/{id}")
    public Result<IssueActivity> getById(@PathVariable Long id) {
        IssueActivity entity = issueActivityService.getById(id);
        return Result.success(entity);
    }

    @Operation(summary = "查询事项活动记录管理列表")
    @GetMapping("/list")
    public Result<List<IssueActivity>> list() {
        List<IssueActivity> list = issueActivityService.list();
        return Result.success(list);
    }

    @Operation(summary = "分页查询事项活动记录管理")
    @PostMapping("/page")
    public Result<Page<IssueActivity>> page(@RequestBody WorkspaceIssueActivityQueryRequest request) {
        try {
            Page<IssueActivity> page = issueActivityService.pageIssueActivity(request);
            return Result.success(page);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
