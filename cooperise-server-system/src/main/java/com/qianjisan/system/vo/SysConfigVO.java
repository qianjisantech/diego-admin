package com.qianjisan.system.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfigVO extends BaseVO {

    /**
     * 配置�?
     */
    private String configKey;

    /**
     * 配置�?
     */
    private String configValue;

    /**
     * 配置类型：string-字符串，number-数字，boolean-布尔，json-JSON
     */
    private String configType;

    /**
     * 配置名称
     */
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
     * 是否系统内置�?-是，0-�?
     */
    private Integer isSystem;

    /**
     * 状态：1-启用�?-禁用
     */
    private Integer status;
}
