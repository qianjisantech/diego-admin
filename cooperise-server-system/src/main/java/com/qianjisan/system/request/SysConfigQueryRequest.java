package com.qianjisan.system.request;

import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigQueryRequest extends PageRequest {

    /**
     * 配置键（模糊查询）
     */
    private String configKey;

    /**
     * 配置名称（模糊查询）
     */
    private String configName;

    /**
     * 配置分组
     */
    private String configGroup;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;
}
