package com.qianjisan.console.controller;

import com.qianjisan.console.service.IInviteService;
import com.qianjisan.console.vo.EnterpriseInviteInfoVo;
import com.qianjisan.console.vo.UserQuerySelectOptionVo;
import com.qianjisan.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "邀请管理", description = "用户邀请相关接口")
@RestController
@RequestMapping("/console-api/invite")
@RequiredArgsConstructor
@Slf4j
public class InviteController {


    private  final IInviteService inviteService;

    @Operation(summary = "用户邀请加入企业相关信息")
    @GetMapping(value = "/info/{enterpriseId}")
    public Result<EnterpriseInviteInfoVo> enterpriseInviteInfo(@Valid @PathVariable Long enterpriseId) {
        try {
            EnterpriseInviteInfoVo enterpriseInviteInfoVo =  inviteService.getEnterpriseInviteInfo(enterpriseId);
            return Result.success(enterpriseInviteInfoVo);
        } catch (Exception e) {
            log.error("用户邀请加入企业相关信息", e);
            return Result.error(e.getMessage());
        }
    }
//    @Operation(summary = "用户邀请加入企业相关信息")
//    @GetMapping(value = "/user/select/options/{enterpriseId}")
//    public Result<List<UserQuerySelectOptionVo>> userQuerySelect(@Valid @PathVariable Long enterpriseId) {
//        try {
//            List<UserQuerySelectOptionVo> userQuerySelect=  inviteService.userQuerySelect(enterpriseId);
//            return Result.success(userQuerySelect);
//        } catch (Exception e) {
//            log.error("用户邀请加入企业相关信息", e);
//            return Result.error(e.getMessage());
//        }
//    }
}
