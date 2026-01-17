package com.qianjisan.console.controller;
import com.qianjisan.console.request.SelfCompanyRequest;
import com.qianjisan.console.service.ICompanyService;
import com.qianjisan.console.service.ISelfService;
import com.qianjisan.console.vo.SelfCompanyInviteInfoVo;
import com.qianjisan.console.vo.SelfCompanyVo;

import com.qianjisan.console.vo.UserQuerySelectOptionVo;
import com.qianjisan.core.Result;
import com.qianjisan.core.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设置控制器
 * 处理用户设置相关的HTTP请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "设置管理", description = "用户设置相关接口")
@RestController
@RequestMapping("/console/self")
@RequiredArgsConstructor
@Slf4j
public class SelfController {

    private  final ISelfService selfService;

    private final ICompanyService companyService;
    /**
     * 获取当前用户的完整设置信息（分组格式）
     */
    @Operation(summary = "获取用户拥有的企业列表")
    @GetMapping("/companies")
    public Result<List<SelfCompanyVo>> getSelfCompanies() {
        try {
            Long userId = UserContextHolder.getUserId();
            log.info("[获取用户拥有的企业列表] 用户ID: {}", userId);
            List<SelfCompanyVo> result = selfService.getSelfCompanies(userId);
            return Result.success(result);
        } catch (Exception e) {
            log.error("[获取用户拥有的企业列表] 失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "用户创建企业")
    @PostMapping(value = "/company")
    public Result<Void> selfCreateCompany(@Valid @RequestBody SelfCompanyRequest request) {
        try {
            selfService.selfCreateCompany(request);
            return Result.success();
        } catch (Exception e) {
            log.error("用户创建公司失败", e);
            return Result.error(e.getMessage());
        }
    }
    @Operation(summary = "切换企业")
    @PutMapping(value = "/company/active/{companyId}")
    public Result<Void> setCompanyActive(@Valid @PathVariable Long companyId) {
        try {
            selfService.setCompanyActive(companyId);
            return Result.success();
        } catch (Exception e) {
            log.error("切换企业失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "用户邀请加入企业相关信息")
    @GetMapping(value = "/company/invite/info/{companyId}")
    public Result<SelfCompanyInviteInfoVo> selfCompanyInviteInfo(@Valid @PathVariable Long companyId) {
        try {
            SelfCompanyInviteInfoVo selfCompanyInviteInfoVo=  selfService.selfCompanyInviteInfo(companyId);
            return Result.success(selfCompanyInviteInfoVo);
        } catch (Exception e) {
            log.error("用户邀请加入企业相关信息", e);
            return Result.error(e.getMessage());
        }
    }
    @Operation(summary = "用户邀请加入企业相关信息")
    @GetMapping(value = "/user/select/options/{companyId}")
    public Result<List<UserQuerySelectOptionVo>> userQuerySelect(@Valid @PathVariable Long companyId) {
        try {
            List<UserQuerySelectOptionVo> userQuerySelect=  selfService.userQuerySelect(companyId);
            return Result.success(userQuerySelect);
        } catch (Exception e) {
            log.error("用户邀请加入企业相关信息", e);
            return Result.error(e.getMessage());
        }
    }
}