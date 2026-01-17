package com.qianjisan.console.controller;

import com.qianjisan.console.vo.IssueDetailVO;
import com.qianjisan.console.vo.IssuePageVO;
import com.qianjisan.core.Result;
import com.qianjisan.annotation.RequiresPermission;
import com.qianjisan.core.PageVO;
import com.qianjisan.console.request.WorkSpaceIssueRequest;
import com.qianjisan.console.request.IssueQueryRequest;

import com.qianjisan.console.service.IIssueService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 事项管理控制�?
 *
 * Controller层职责：
 * 1. 接收HTTP请求
 * 2. 参数验证（基础验证�?
 * 3. 调用Service层处理业务逻辑
 * 4. 返回统一的响应结�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "事项管理", description = "事项相关接口")
@RestController
@RequestMapping("/workspace/issue")
@RequiredArgsConstructor
@Slf4j
public class IssueController {

    private final IIssueService workspaceIssueService;

    /**
     * 创建事项
     */
    @Operation(summary = "创建事项")
    @RequiresPermission("workspace:issue:add")
    @PostMapping
    public Result<Void> create(@RequestBody @Valid WorkSpaceIssueRequest request) {
        try {
            log.info("[创建事项] 请求参数: {}", request);
            workspaceIssueService.createIssue(request);
            log.info("[创建事项] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[创建事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新事项
     */
    @Operation(summary = "更新事项")
    @RequiresPermission("workspace:issue:edit")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody @Valid WorkSpaceIssueRequest request) {
        try {
            log.info("[更新事项] ID: {}, 请求参数: {}", id, request);
            workspaceIssueService.updateIssue(id, request);
            log.info("[更新事项] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 删除事项
     */
    @Operation(summary = "删除事项")
    @RequiresPermission("workspace:issue:delete")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除事项] ID: {}", id);
            workspaceIssueService.deleteIssue(id);
            log.info("[删除事项] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 根据ID查询事项详情（包含扩展字段）
     */
    @Operation(summary = "根据ID查询事项")
    @RequiresPermission("workspace:issue:view")
    @GetMapping("/{id}")
    public Result<IssueDetailVO> getById(@PathVariable Long id) {
        try {
            log.info("[查询事项] ID: {}", id);
            IssueDetailVO issueDetail = workspaceIssueService.getIssueDetailById(id);
            if (issueDetail == null) {
                return Result.error("事项不存�?);
            }
            return Result.success(issueDetail);
        } catch (Exception e) {
            log.error("[查询事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 分页查询事项
     */
    @Operation(summary = "分页查询事项")
    @RequiresPermission("workspace:issue:view")
    @PostMapping("/page")
    public Result<PageVO<IssuePageVO>> page(@RequestBody IssueQueryRequest request) {
        log.info("[分页查询事项] 查询参数: {}", request);
        try {
            PageVO<IssuePageVO> page = workspaceIssueService.pageQuery(request);
            log.info("[分页查询事项] 成功，共 {} �?, page.getTotal());
            return Result.success(page);
        } catch (Exception e) {
            log.error("[分页查询事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 搜索事项（支持事项单号和标题搜索�?
     * 用于顶部搜索框的自动提示
     */
    @Operation(summary = "搜索事项")
    @RequiresPermission("workspace:issue:view")
    @GetMapping("/search")
    public Result<List<Map<String, Object>>> search(@RequestParam String keyword) {
        log.info("[搜索事项] 关键�? {}", keyword);
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return Result.success(List.of());
            }
            List<Map<String, Object>> results = workspaceIssueService.searchIssues(keyword.trim());
            log.info("[搜索事项] 成功，找�?{} 条结�?, results.size());
            return Result.success(results);
        } catch (Exception e) {
            log.error("[搜索事项] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
