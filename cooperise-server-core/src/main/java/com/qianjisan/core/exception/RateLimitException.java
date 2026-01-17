package com.qianjisan.core.exception;

/**
 * 限流异常
 *
 * @author Diego
 * @since 2024-11-21
 */
public class RateLimitException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RateLimitException(String message) {
        super(message);
    }

    public RateLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
