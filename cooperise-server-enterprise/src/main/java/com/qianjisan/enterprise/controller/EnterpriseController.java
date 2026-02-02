package com.qianjisan.enterprise.controller;

import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.request.EnterpriseQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseRequest;
import com.qianjisan.enterprise.service.IEnterpriseService;
import com.qianjisan.enterprise.vo.EnterpriseVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "企业管理", description = "企业相关接口")
@RestController
@RequestMapping("/enterprise-api/enterprise")
@RequiredArgsConstructor
@Slf4j
public class EnterpriseController {

    private final IEnterpriseService enterpriseService;

    @Operation(summary = "创建企业")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody EnterpriseRequest request) {
        try {
            log.info("[创建企业] 请求参数: {}", request);
            enterpriseService.createEnterprise(request);
            log.info("[创建企业] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[创建企业] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新企业")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody EnterpriseRequest request) {
        try {
            log.info("[更新企业] ID: {}, 请求参数: {}", id, request);
            enterpriseService.updateEnterprise(id, request);
            log.info("[更新企业] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[更新企业] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除企业")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            log.info("[删除企业] ID: {}", id);
            enterpriseService.deleteEnterprise(id);
            log.info("[删除企业] 成功");
            return Result.success();
        } catch (Exception e) {
            log.error("[删除企业] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询企业")
    @GetMapping("/{id}")
    public Result<EnterpriseVo> getById(@PathVariable Long id) {
        try {
            log.info("[查询企业] ID: {}", id);
            EnterpriseVo vo = enterpriseService.getEnterpriseById(id);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("[查询企业] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询企业")
    @PostMapping("/page")
    public Result<PageVO<EnterpriseVo>> page(@RequestBody EnterpriseQueryRequest request) {
        log.info("[分页查询企业] 查询参数: {}", request);
        try {
            PageVO<EnterpriseVo> pageVO = enterpriseService.getEnterprisePage(request);
            log.info("[分页查询企业] 成功，共 {} 条", pageVO.getTotal());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("[分页查询企业] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}