package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 系统配置请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysConfigRequest {

    /**
     * 配置ID（更新时需要）
     */
    private Long id;

    /**
     * 配置键
     */
    @NotBlank(message = "配置键不能为空")
    private String configKey;

    /**
     * 配置值
     */
    private String configValue;

    /**
     * 配置类型：string-字符串，number-数字，boolean-布尔，json-JSON
     */
    private String configType;

    /**
     * 配置名称
     */
    @NotBlank(message = "配置名称不能为空")
    private String configName;

    /**
     * 配置分组
     */
    private String configGroup;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否系统内置：1-是，0-否
     */
    private Integer isSystem;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;
}
