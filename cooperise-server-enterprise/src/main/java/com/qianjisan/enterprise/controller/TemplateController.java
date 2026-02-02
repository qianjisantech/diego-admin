package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.TemplateQueryRequest;
import com.qianjisan.enterprise.request.TemplateRequest;
import com.qianjisan.enterprise.service.TemplateService;
import com.qianjisan.enterprise.vo.TemplateOptionVo;
import com.qianjisan.enterprise.vo.TemplateQueryPageVo;
import com.qianjisan.enterprise.vo.TemplateVo;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "模板管理", description = "Template 相关接口")
@RestController
@RequestMapping("/enterprise-api/template")
@RequiredArgsConstructor
@Slf4j
public class TemplateController {

    private final TemplateService templateService;

    @Operation(summary = "创建模板")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody TemplateRequest request) {
        try {
            templateService.createTemplate(request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新模板")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody TemplateRequest request) {
        try {
            templateService.updateTemplate(id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除模板")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            templateService.deleteTemplate(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询模板详情")
    @GetMapping("/{id}")
    public Result<TemplateVo> getTemplateDetailById(@PathVariable Long id) {
        try {
            TemplateVo vo = templateService.getTemplateDetailById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询模板")
    @PostMapping("/page")
    public Result<PageVO<TemplateQueryPageVo>> page(@RequestBody TemplateQueryRequest request) {
        try {
            PageVO<TemplateQueryPageVo> pageVO = templateService.getTemplatePage(request);
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询模板失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取模板选项列表（用于下拉框）")
    @GetMapping("/options")
    public Result<List<TemplateOptionVo>> getOptions(@RequestParam(required = false) Long companyId) {
        try {
            List<TemplateOptionVo> options = templateService.getTemplateOptions(companyId);
            return Result.success(options);
        } catch (Exception e) {
            log.error("获取模板选项列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
