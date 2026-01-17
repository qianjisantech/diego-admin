package com.qianjisan.core.interceptor;


import com.qianjisan.core.context.UserContextHolder;

import com.qianjisan.core.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT拦截�?
 * 用于解析请求头中的JWT token，并设置用户上下文信�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    /**
     * Authorization header名称
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Bearer token前缀
     */
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取token
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            // 提取token（去�?Bearer "前缀�?
            String token = authHeader.substring(BEARER_PREFIX.length());

            try {
                // 验证token是否有效
                if (JwtUtil.isTokenValid(token)) {
                    // 解析token，获取用户信�?
                    Long userId = JwtUtil.getUserId(token);
                    String username = JwtUtil.getUsername(token);
                    String nickname = JwtUtil.getNickname(token);

                    // 设置用户上下�?
                    UserContextHolder.setUser(userId, username, nickname);

                    log.debug("JWT token解析成功，用户ID: {}, 用户�? {}, 昵称: {}", userId, username, nickname);
                } else {
                    log.warn("JWT token已过期或无效");
                }
            } catch (Exception e) {
                log.error("解析JWT token失败: {}", e.getMessage());
            }
        }

        // 继续执行后续处理
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求完成后清除用户上下文，防止内存泄�?
        UserContextHolder.clear();
    }
}
