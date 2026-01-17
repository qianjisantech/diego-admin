package com.qianjisan.system.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 操作日志查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysOperationLogRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer current = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径
     */
    private String requestUrl;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 开始时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String startTime;

    /**
     * 结束时间（格式：yyyy-MM-dd HH:mm:ss）
     */
    private String endTime;

    /**
     * 关键词搜索（用户名、请求路径、IP地址）
     */
    private String keyword;
}
