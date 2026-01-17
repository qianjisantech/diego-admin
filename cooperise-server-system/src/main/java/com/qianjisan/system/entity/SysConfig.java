package com.qianjisan.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置实体�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_config")
public class SysConfig extends BaseEntity {

    /**
     * 配置ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 配置�?
     */
    @TableField("config_key")
    private String configKey;

    /**
     * 配置�?
     */
    @TableField("config_value")
    private String configValue;

    /**
     * 配置类型：string-字符串，number-数字，boolean-布尔，json-JSON
     */
    @TableField("config_type")
    private String configType;

    /**
     * 配置名称
     */
    @TableField("config_name")
    private String configName;

    /**
     * 配置分组
     */
    @TableField("config_group")
    private String configGroup;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 是否系统内置�?-是，0-�?
     */
    @TableField("is_system")
    private Integer isSystem;

    /**
     * 状态：1-启用�?-禁用
     */
    @TableField("status")
    private Integer status;
}
