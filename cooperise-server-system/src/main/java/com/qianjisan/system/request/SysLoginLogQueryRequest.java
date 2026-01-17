package com.qianjisan.system.request;

import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录日志查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLoginLogQueryRequest extends PageRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 状态：1-成功，0-失败
     */
    private Integer status;

    /**
     * 搜索关键词（匹配登录IP、操作系统、浏览器）
     */
    private String keyword;
}
