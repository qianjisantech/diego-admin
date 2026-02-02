package com.qianjisan.auth.controller;
import com.qianjisan.auth.service.IAuthService;
import com.qianjisan.auth.vo.UserProfileVO;
import com.qianjisan.core.Result;
import com.qianjisan.core.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/auth-api/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

    private final IAuthService authService;


    @Operation(summary = "获取用户权限信息")
    @GetMapping("")
    public Result<UserProfileVO> getProfile() {
        try {
            Long userId = UserContextHolder.getUserId();

            if (userId == null) {
                log.info("[获取用户权限信息] 权限放开模式，返回默认用户信息");
                UserProfileVO defaultProfile = new UserProfileVO();

                UserProfileVO.UserInfoVo defaultUserInfo = new UserProfileVO.UserInfoVo();
                defaultUserInfo.setName("访客用户");
                defaultUserInfo.setUserCode("guest");
                defaultUserInfo.setEmail("guest@example.com");
                defaultProfile.setUserInfo(defaultUserInfo);

                defaultProfile.setMenus(new ArrayList<>());
                defaultProfile.setMenuPermissions(new String[]{});
                defaultProfile.setRoles(new String[]{"guest"});

                return Result.success("权限放开模式，访客访问", defaultProfile);
            }

            log.info("[获取用户权限信息] 用户ID: {}", userId);
            UserProfileVO profile = authService.getUserProfile(userId);
            log.info("[获取用户权限信息] 成功");
            return Result.success(profile);
        } catch (Exception e) {
            log.error("[获取用户权限信息] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
