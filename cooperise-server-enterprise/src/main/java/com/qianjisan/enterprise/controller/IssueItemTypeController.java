package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.IssueItemTypeQueryRequest;
import com.qianjisan.enterprise.request.IssueItemTypeRequest;
import com.qianjisan.enterprise.service.IssueItemTypeService;
import com.qianjisan.enterprise.vo.IssueItemTypeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "事项类型管理", description = "事项类型相关接口")
@RestController
@RequestMapping("/enterprise-api/issue-item-type")
@RequiredArgsConstructor
@Slf4j
public class IssueItemTypeController {

    private final IssueItemTypeService issueItemTypeService;

    @Operation(summary = "创建事项类型")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody IssueItemTypeRequest request) {
        try {
            log.info("[创建事项类型] 请求参数: {}", request);
            issueItemTypeService.createIssueItemType(request);
            log.info("[创建事项类型] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[创建事项类型] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新事项类型")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody IssueItemTypeRequest request) {
        try {
            log.info("[更新事项类型] ID: {}, 请求参数: {}", id, request);
            issueItemTypeService.updateIssueItemType(id, request);
            log.info("[更新事项类型] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新事项类型] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除事项类型")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除事项类型] ID: {}", id);
            issueItemTypeService.deleteIssueItemType(id);
            log.info("[删除事项类型] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除事项类型] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询事项类型")
    @GetMapping("/{id}")
    public Result<IssueItemTypeVo> getById(@PathVariable Long id) {
        try {
            log.info("[查询事项类型] ID: {}", id);
            IssueItemTypeVo vo = issueItemTypeService.getIssueItemTypeById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("[查询事项类型] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询事项类型")
    @PostMapping("/page")
    public Result<PageVO<IssueItemTypeVo>> page(@RequestBody IssueItemTypeQueryRequest request) {
        log.info("[分页查询事项类型] 查询参数: {}", request);
        try {
            PageVO<IssueItemTypeVo> pageVO = issueItemTypeService.getIssueItemTypePage(request);
            log.info("[分页查询事项类型] 成功，共 {} 条", pageVO.getTotal());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("[分页查询事项类型] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
