package com.qianjisan.core.handler;

import com.qianjisan.core.Result;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.core.exception.RateLimitException;
import com.qianjisan.core.exception.DataPermissionException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理�?
 * 统一处理系统中的各类异常，并返回标准化的响应格式
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理限流异常
     * 当接口访问频率超过限制时抛出
     */
    @ExceptionHandler(RateLimitException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public Result<?> handleRateLimitException(RateLimitException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String ip = getClientIp(request);

        log.warn("[限流异常] {} {} - IP: {} - {}", method, requestUri, ip, e.getMessage());

        return Result.error(e.getMessage());
    }

    /**
     * 处理数据权限异常
     * 当用户访问无权限的数据时抛出
     */
    @ExceptionHandler(DataPermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Result<?> handleDataPermissionException(DataPermissionException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();
        String ip = getClientIp(request);

        log.warn("[数据权限异常] {} {} - IP: {} - {}", method, requestUri, ip, e.getMessage());

        return Result.error(e.getMessage());
    }

    /**
     * 处理业务异常
     * 业务异常通常是主动抛出的，包含明确的错误信息
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBusinessException(BusinessException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.warn("[业务异常] {} {} - {}", method, requestUri, e.getMessage());

        return Result.error(e.getMessage());
    }

    /**
     * 处理参数校验异常 - MethodArgumentNotValidException
     * 用于 @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        // 收集所有校验失败的字段信息
        String errors = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining("; "));

        log.warn("[参数校验异常] {} {} - {}", method, requestUri, errors);

        return Result.error("参数校验失败: " + errors);
    }

    /**
     * 处理参数绑定异常 - BindException
     * 用于 @ModelAttribute 参数绑定失败
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBindException(BindException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        // 收集所有绑定失败的字段信息
        String errors = e.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining("; "));

        log.warn("[参数绑定异常] {} {} - {}", method, requestUri, errors);

        return Result.error("参数绑定失败: " + errors);
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.warn("[非法参数异常] {} {} - {}", method, requestUri, e.getMessage());

        return Result.error(e.getMessage());
    }

    /**
     * 处理空指针异�?
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleNullPointerException(NullPointerException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.error("[空指针异常] {} {} - {}", method, requestUri, e.getMessage(), e);

        return Result.error("系统内部错误，请联系管理�?);
    }

    /**
     * 处理所有未捕获的异�?
     * 这是兜底异常处理，确保所有异常都能被捕获并返回统一格式
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String method = request.getMethod();

        log.error("[系统异常] {} {} - {}", method, requestUri, e.getMessage(), e);

        return Result.error("系统异常: " + e.getMessage());
    }

    /**
     * 获取客户端真实IP
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多级反向代理的情�?
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
