package com.qianjisan.core;

import com.qianjisan.core.utils.TraceLogger;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author DCP Team
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 时间�?
     */
    private Long timestamp;

    /**
     * 链路追踪ID
     */
    private String traceId;

    public Result() {
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceLogger.getTraceIdWithoutPrefix();
    }

    public Result(String message, T data, Boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
        this.timestamp = System.currentTimeMillis();
        this.traceId = TraceLogger.getTraceIdWithoutPrefix();
    }

    /**
     * 成功返回（无数据�?
     */
    public static <T> Result<T> success() {
        return new Result<T>("操作成功", null, true);
    }

    /**
     * 成功返回（有数据�?
     */
    public static <T> Result<T> success(T data) {
        return new Result<>("操作成功", data, true);
    }

    /**
     * 成功返回（自定义消息�?
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(message, data, true);
    }

    /**
     * 失败返回
     */
    public static <T> Result<T> error() {
        return new Result<>("操作失败", null, false);
    }

    /**
     * 失败返回（自定义消息�?
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(message, null, false);
    }

    /**
     * 自定义返�?
     */
    public static <T> Result<T> build(String message, T data, Boolean success) {
        return new Result<>(message, data, success);
    }
}
