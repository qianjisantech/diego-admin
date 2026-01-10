package com.qianjisan.core.config;


import com.qianjisan.core.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC配置
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 【权限放开】暂时禁用JWT拦截器，所有接口都不需要token验证
        // 如果需要恢复JWT验证，请取消下面的注释

        /*
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/auth/login",           // 登录接口不需要验证
                        "/auth/register",        // 注册接口不需要验证
                        "/console/self/company/invite/info/**",
                        "/tracking/report",
                        "/swagger-ui/**",        // Swagger UI
                        "/swagger-resources/**", // Swagger资源
                        "/v3/api-docs/**",       // OpenAPI文档
                        "/doc.html",             // Knife4j文档
                        "/webjars/**",           // Knife4j资源
                        "/favicon.ico",          // 图标
                        "/error"                 // 错误页面
                );
        */

        // 记录拦截器已禁用（可选）
        // System.out.println("JWT拦截器已禁用，所有接口权限放开");
    }
}
