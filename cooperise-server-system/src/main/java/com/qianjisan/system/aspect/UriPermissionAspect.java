package com.qianjisan.system.aspect;

import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * URI权限校验切面
 * 用于拦截Controller方法，基于请求URI进行权限校验
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Aspect
@Component
@Order(2) // 在JwtInterceptor之后执行
@RequiredArgsConstructor
public class UriPermissionAspect {

    private final ISysMenuService menuService;

    /**
     * 定义切点：拦截所有Controller方法
     * 排除认证相关接口（通过路径模式匹配�?
     */
    @Pointcut("execution(* com.qianjisan..controller..*.*(..)) && " +
              "!execution(* com.qianjisan..controller.*AuthController.*(..)) && " +
              "!execution(* com.qianjisan..controller.*TrackingLogController.*(..)) && " +
              "!execution(* com.qianjisan.console.controller.SelfController.selfCompanyInviteInfo(..))")
    public void controllerMethods() {}

    /**
     * 权限校验切面
     * 【已放开】所有接口权限全部放开，不进行URI权限校验
     */
    @Before("controllerMethods()")
    public void checkUriPermission(JoinPoint joinPoint) {
        // 【权限放开】不再进行任何权限校验，所有接口都可以访问
        // 如果需要恢复权限检查，请取消下面的注释

        /*
        // 获取当前请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("[URI权限校验] 无法获取请求上下�?);
            return;
        }

        HttpServletRequest request = requestAttributes.getRequest();
        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        // 排除一些特殊的认证接口（通过路径判断�?
        if (isExcludedUri(requestUri)) {
            log.debug("[URI权限校验] 跳过权限校验，URI: {} {}", requestMethod, requestUri);
            return;
        }

        // 获取当前用户ID
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String userCode = UserContextHolder.getUserCode();

        // 如果用户未登录，抛出异常
        if (userId == null) {
            log.warn("[URI权限校验] 用户未登录，拒绝访问URI: {} {}", requestMethod, requestUri);
            throw new BusinessException("用户未登录，请先登录");
        }

        // admin用户拥有所有权�?
        if ("admin".equalsIgnoreCase(userCode)) {
            log.debug("[URI权限校验] 用户 {} 是管理员，拥有所有权限，访问URI: {} {}", username, requestMethod, requestUri);
            return;
        }

        // 检查用户是否有该URI的访问权�?
        boolean hasPermission = menuService.checkUserUriPermission(userId, requestUri);

        if (!hasPermission) {
            log.warn("[URI权限校验] 用户 {} (ID: {}) 无权限访问URI: {} {}，拒绝访�?,
                username, userId, requestMethod, requestUri);
            throw new BusinessException("您没有权限访问此接口，如需要请联系管理�?);
        }

        log.debug("[URI权限校验] 用户 {} (ID: {}) 权限校验通过，访问URI: {} {}",
            username, userId, requestMethod, requestUri);
        */

        // 记录请求信息（可选）
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (requestAttributes != null) {
                HttpServletRequest request = requestAttributes.getRequest();
                String requestUri = request.getRequestURI();
                String requestMethod = request.getMethod();
                log.debug("[权限放开] 允许访问: {} {}", requestMethod, requestUri);
            }
        } catch (Exception e) {
            // 忽略获取请求信息时的异常
        }
    }

    /**
     * 判断是否为需要排除权限校验的URI
     *
     * @param requestUri 请求URI
     * @return 是否排除
     */
    private boolean isExcludedUri(String requestUri) {
        // 排除认证相关接口
        if (requestUri.startsWith("/auth/") ||
            requestUri.contains("/login") ||
            requestUri.contains("/logout") ||
            requestUri.contains("/register") ||
            requestUri.contains("/verify") ||
            requestUri.contains("/captcha") ||
            requestUri.contains("/refresh")) {
            return true;
        }

        // 排除健康检查接�?
        if (requestUri.contains("/health") ||
            requestUri.contains("/actuator")) {
            return true;
        }

        // 排除一些公共接�?
        if (requestUri.contains("/public") ||
            requestUri.contains("/common")) {
            return true;
        }

        // 排除企业邀请信息接口（无需登录即可查看�?
        if (requestUri.contains("/console/self/company/invite/info/")) {
            return true;
        }

        return false;
    }
}
