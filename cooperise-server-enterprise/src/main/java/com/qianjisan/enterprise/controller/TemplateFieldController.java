package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.BatchSaveTemplateFieldRequest;
import com.qianjisan.enterprise.request.TemplateFieldQueryRequest;

import com.qianjisan.enterprise.service.TemplateFieldService;
import com.qianjisan.enterprise.vo.TemplateFieldVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "模板字段管理", description = "TemplateField 相关接口")
@RestController
@RequestMapping("/enterprise-api/template-field")
@RequiredArgsConstructor
@Slf4j
public class TemplateFieldController {

    private final TemplateFieldService templateFieldService;

    @Operation(summary = "批量保存模板字段")
    @PostMapping("/save")
    public Result<Void> batchSave(@Valid @RequestBody BatchSaveTemplateFieldRequest request) {
        try {
            templateFieldService.batchSaveTemplateFields(request);
            return Result.success();
        } catch (Exception e) {
            log.error("批量保存模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }



    @Operation(summary = "删除模板字段")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            templateFieldService.deleteTemplateField(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询模板字段")
    @GetMapping("/{id}")
    public Result< List<TemplateFieldVo>> getTemplateFieldById(@PathVariable Long id) {
        try {
            List<TemplateFieldVo> templateFieldVos = templateFieldService.getFieldsByTemplateId(id);
            return Result.success(templateFieldVos);
        } catch (Exception e) {
            log.error("查询模板字段失败", e);
            return Result.error(e.getMessage());
        }
    }


}
