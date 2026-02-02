package com.qianjisan.console.controller;
import com.qianjisan.console.request.MyEnterpriseRequest;
import com.qianjisan.console.service.IMyEnterpriseService;
import com.qianjisan.console.vo.EnterpriseVo;

import com.qianjisan.core.Result;
import com.qianjisan.core.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "设置管理", description = "用户设置相关接口")
@RestController
@RequestMapping("/console-api/my-enterprise")
@RequiredArgsConstructor
@Slf4j
public class MyEnterpriseController {

    private  final IMyEnterpriseService myEnterpriseService;

    @Operation(summary = "获取用户拥有的企业列表")
    @GetMapping("/list")
    public Result<List<EnterpriseVo>> getEnterpriseList() {
        try {
            Long userId = UserContextHolder.getUserId();
            log.info("[获取用户拥有的企业列表] 用户ID: {}", userId);
            List<EnterpriseVo> result = myEnterpriseService.getMyEnterpriseList(userId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("[获取用户拥有的企业列表] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "用户创建企业")
    @PostMapping(value = "")
    public Result<Void> createMyEnterprise(@Valid @RequestBody MyEnterpriseRequest request) {
        try {
            myEnterpriseService.createEnterprise(request);
            return Result.success();
        } catch (Exception e) {
            log.error("用户创建公司失败", e);
            return Result.error(e.getMessage());
        }
    }
    @Operation(summary = "切换企业")
    @PutMapping(value = "/active/{enterpriseId}")
    public Result<Void> setEnterpriseActive(@Valid @PathVariable Long enterpriseId) {
        try {
            myEnterpriseService.setEnterpriseActive(enterpriseId);
            return Result.success();
        } catch (Exception e) {
            log.error("切换企业失败", e);
            return Result.error(e.getMessage());
        }
    }


}
