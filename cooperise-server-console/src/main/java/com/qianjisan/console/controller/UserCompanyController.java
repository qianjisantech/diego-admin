package com.qianjisan.console.controller;

import com.qianjisan.console.entity.UserCompany;
import com.qianjisan.console.service.IUserCompanyService;
import com.qianjisan.console.service.impl.UserCompanyServiceImpl;
import com.qianjisan.core.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户-企业关联�?测试控制�?
 * 注意：生产环境请删除此控制器
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Tag(name = "用户企业关联测试", description = "UserCompany 测试接口")
@RestController
@RequestMapping("/test/user-company")
@RequiredArgsConstructor
@Slf4j
public class UserCompanyController {

    private final IUserCompanyService userCompanyService;



    @Operation(summary = "根据企业ID查询用户列表")
    @GetMapping("/users/{companyId}")
    public Result<List<Long>> getUserIdsByCompanyId(@PathVariable Long companyId) {
        try {
            List<Long> userIds = userCompanyService.getUserIdsByCompanyId(companyId);
            return Result.success("查询成功", userIds);
        } catch (Exception e) {
            log.error("查询企业用户失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "获取用户默认企业")
    @GetMapping("/default-company/{userId}")
    public Result<Long> getDefaultCompanyIdByUserId(@PathVariable Long userId) {
        try {
            Long companyId = userCompanyService.getDefaultCompanyIdByUserId(userId);
            return Result.success("查询成功", companyId);
        } catch (Exception e) {
            log.error("查询用户默认企业失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "设置用户默认企业")
    @PostMapping("/set-default/{userId}/{companyId}")
    public Result<Boolean> setDefaultCompany(@PathVariable Long userId, @PathVariable Long companyId) {
        try {
            boolean result = userCompanyService.setDefaultCompany(userId, companyId);
            return Result.success("设置成功", result);
        } catch (Exception e) {
            log.error("设置默认企业失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "添加用户企业关联")
    @PostMapping("/add")
    public Result<Boolean> addUserCompany(@RequestBody UserCompany userCompany) {
        try {
            UserCompanyServiceImpl impl = (UserCompanyServiceImpl) userCompanyService;
            boolean result = impl.addUserCompany(userCompany.getUserId(), userCompany.getCompanyId(),
                                                userCompany.getIsDefault() == 1);
            return Result.success("添加成功", result);
        } catch (Exception e) {
            log.error("添加用户企业关联失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "移除用户企业关联")
    @DeleteMapping("/remove/{userId}/{companyId}")
    public Result<Boolean> removeUserCompany(@PathVariable Long userId, @PathVariable Long companyId) {
        try {
            UserCompanyServiceImpl impl = (UserCompanyServiceImpl) userCompanyService;
            boolean result = impl.removeUserCompany(userId, companyId);
            return Result.success("移除成功", result);
        } catch (Exception e) {
            log.error("移除用户企业关联失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "查询所有用户企业关�?)
    @GetMapping("/list")
    public Result<List<UserCompany>> listAll() {
        try {
            List<UserCompany> list = userCompanyService.list();
            return Result.success("查询成功", list);
        } catch (Exception e) {
            log.error("查询用户企业关联列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
