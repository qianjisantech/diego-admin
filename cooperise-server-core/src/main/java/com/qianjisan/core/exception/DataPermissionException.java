package com.qianjisan.core.exception;

/**
 * 数据权限异常
 * 当用户访问无权限的数据时抛出
 *
 * @author Diego
 * @since 2024-11-21
 */
public class DataPermissionException extends RuntimeException {

    public DataPermissionException(String message) {
        super(message);
    }

    public DataPermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
