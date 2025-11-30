package com.dcp.aspect;

import com.dcp.common.annotation.RequiresPermission;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.exception.BusinessException;
import com.dcp.rbac.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 权限校验切面
 * 用于拦截带有 @RequiresPermission 注解的方法，进行权限校验
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Aspect
@Component
@Order(2) // 在JwtInterceptor之后执行
@RequiredArgsConstructor
public class PermissionAspect {

    private final ISysMenuService menuService;

    /**
     * 权限校验切点
     * 拦截所有带有 @RequiresPermission 注解的方法
     */
    @Before("@annotation(com.dcp.common.annotation.RequiresPermission)")
    public void checkPermission(JoinPoint joinPoint) {
        // 获取当前用户ID
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String userCode = UserContextHolder.getUserCode();
        // 如果用户未登录，抛出异常
        if (userId == null) {
            log.warn("[权限校验] 用户未登录，拒绝访问");
            throw new BusinessException("用户未登录，请先登录");
        }

        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 获取权限注解
        RequiresPermission requiresPermission = method.getAnnotation(RequiresPermission.class);
        if (requiresPermission == null) {
            return;
        }

        // 获取需要的权限列表
        String[] requiredPermissions = requiresPermission.value();
        RequiresPermission.Logical logical = requiresPermission.logical();

        // 如果没有指定权限，直接放行
        if (requiredPermissions == null || requiredPermissions.length == 0) {
            return;
        }

        // 获取用户拥有的权限
        List<String> userPermissions = menuService.getUserMenuPermissions(userId);

        // admin用户拥有所有权限
        if ("admin".equalsIgnoreCase(userCode) || userPermissions.contains("*:*:*")) {
            log.debug("[权限校验] 用户 {} 是管理员，拥有所有权限", username);
            return;
        }

        // 校验权限
        boolean hasPermission = checkUserPermission(userPermissions, requiredPermissions, logical);

        if (!hasPermission) {
            String requiredPermissionsStr = Arrays.toString(requiredPermissions);
            log.warn("[权限校验] 用户 {} (ID: {}) 无权限访问，需要权限: {}, 拥有权限: {}",
                username, userId, requiredPermissionsStr, userPermissions);
            throw new BusinessException("您没有权限执行此操作,如需要请联系管理员" );
        }

        log.debug("[权限校验] 用户 {} (ID: {}) 权限校验通过，需要权限: {}",
            username, userId, Arrays.toString(requiredPermissions));
    }

    /**
     * 校验用户是否拥有所需权限
     *
     * @param userPermissions 用户拥有的权限列表
     * @param requiredPermissions 需要的权限列表
     * @param logical 逻辑关系（AND 或 OR）
     * @return 是否拥有权限
     */
    private boolean checkUserPermission(List<String> userPermissions,
                                       String[] requiredPermissions,
                                       RequiresPermission.Logical logical) {
        if (userPermissions == null || userPermissions.isEmpty()) {
            return false;
        }

        if (logical == RequiresPermission.Logical.AND) {
            // AND 逻辑：需要拥有所有权限
            for (String requiredPermission : requiredPermissions) {
                if (!hasPermission(userPermissions, requiredPermission)) {
                    return false;
                }
            }
            return true;
        } else {
            // OR 逻辑：只需拥有其中一个权限
            for (String requiredPermission : requiredPermissions) {
                if (hasPermission(userPermissions, requiredPermission)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 检查用户是否拥有指定权限
     * 支持通配符匹配，例如：
     * - workspace:* 表示拥有 workspace 模块的所有权限
     * - workspace:issue:* 表示拥有 workspace:issue 的所有操作权限
     *
     * @param userPermissions 用户权限列表
     * @param requiredPermission 需要的权限
     * @return 是否拥有权限
     */
    private boolean hasPermission(List<String> userPermissions, String requiredPermission) {
        // 精确匹配
        if (userPermissions.contains(requiredPermission)) {
            return true;
        }

        // 通配符匹配
        for (String userPermission : userPermissions) {
            if (userPermission.endsWith("*")) {
                // 去掉通配符 *
                String prefix = userPermission.substring(0, userPermission.length() - 1);
                // 检查是否匹配前缀
                if (requiredPermission.startsWith(prefix)) {
                    return true;
                }
            }
        }

        return false;
    }
}
