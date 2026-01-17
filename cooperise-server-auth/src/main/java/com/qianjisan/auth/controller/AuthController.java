package com.qianjisan.auth.controller;

import com.qianjisan.auth.request.RegisterRequest;
import com.qianjisan.auth.request.SendCodeRequest;
import com.qianjisan.auth.service.IAuthService;
import com.qianjisan.auth.vo.UserProfileVO;
import com.qianjisan.core.Result;
import com.qianjisan.core.context.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.qianjisan.auth.request.LoginRequest;
import com.qianjisan.auth.vo.LoginResponseVO;
import java.util.ArrayList;

/**
 * 认证控制�?
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
@Tag(name = "认证管理", description = "用户认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final IAuthService authService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginResponseVO> login( @RequestBody @Valid LoginRequest request) {
        try {
            String email = request.getEmail();
            Boolean remember = request.getRemember();
            String password = request.getPassword();
            log.info("[用户登录] 邮箱: {}, 记住�? {}", email,remember);
            LoginResponseVO response = authService.login(email, password);
            log.info("[用户登录] 成功");
            return Result.success("登录成功",response);
        } catch (Exception e) {
            log.error("[用户登录] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error("登录失败，失败原因为�?+e.getMessage());
        }
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        // TODO: 实现登出逻辑，清除token
        return Result.success();
    }

    @Operation(summary = "获取用户权限信息")
    @GetMapping("/profile")
    public Result<UserProfileVO> getProfile() {
        try {
            // 【权限放开模式】从 ThreadLocal 获取当前用户信息
            Long userId = UserContextHolder.getUserId();

            // 如果没有用户信息（权限放开模式），返回默认的profile信息
            if (userId == null) {
                log.info("[获取用户权限信息] 权限放开模式，返回默认用户信�?);
                UserProfileVO defaultProfile = new UserProfileVO();

                // 设置默认用户信息（访客模式）
                UserProfileVO.UserInfoVo defaultUserInfo = new UserProfileVO.UserInfoVo();
                defaultUserInfo.setName("访客用户");
                defaultUserInfo.setUserCode("guest");
                defaultUserInfo.setEmail("guest@example.com");
                defaultProfile.setUserInfo(defaultUserInfo);

                // 设置默认权限（空权限�?
                defaultProfile.setMenus(new ArrayList<>());
                defaultProfile.setMenuPermissions(new String[]{});
                defaultProfile.setRoles(new String[]{"guest"});

                return Result.success("权限放开模式，访客访�?, defaultProfile);
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

    @Operation(summary = "发送邮箱验证码")
    @PostMapping("/send-code")
    public Result<String> sendVerificationCode(@RequestBody @Valid SendCodeRequest request) {
        try {
            String email = request.getEmail();
            log.info("[发送验证码] 邮箱: {}", email);
            authService.sendVerificationCode(email);
            log.info("[发送验证码] 成功");
            return Result.success("发送验证码成功");
        } catch (Exception e) {
            log.error("[发送验证码] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<String> register(@RequestBody @Valid RegisterRequest request) {

        try {
            String email = request.getEmail();
            String code = request.getCode();
            String password = request.getPassword();
            log.info("[用户注册] 邮箱: {}", email);
            authService.register(email, code, password);
            log.info("[用户注册] 成功");
            return Result.success("注册成功");
        } catch (Exception e) {
            log.error("[用户注册] 失败，失败原因：{}", e.getMessage(), e);
            return Result.error(e.getMessage());
        }
    }
}
