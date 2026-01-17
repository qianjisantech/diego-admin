package com.qianjisan.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段配置实体类（用于控制表单字段的显示和必填）
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_field_config")
public class SysFieldConfig extends BaseEntity {

    /**
     * 字段配置ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模块编码，如：issue-工作项，task-任务
     */
    @TableField("module_code")
    private String moduleCode;

    /**
     * 字段编码，如：summary-概要，priority-优先级
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 字段名称
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段类型：input-输入框，select-下拉框，textarea-文本域，date-日期等
     */
    @TableField("field_type")
    private String fieldType;

    /**
     * 是否可见：1-是，0-否
     */
    @TableField("is_visible")
    private Integer isVisible;

    /**
     * 是否必填：1-是，0-否
     */
    @TableField("is_required")
    private Integer isRequired;

    /**
     * 是否可编辑：1-是，0-否
     */
    @TableField("is_editable")
    private Integer isEditable;

    /**
     * 排序
     */
    @TableField("sort_order")
    private Integer sortOrder;

    /**
     * 默认值
     */
    @TableField("default_value")
    private String defaultValue;

    /**
     * 验证规则（JSON格式）
     */
    @TableField("validation_rule")
    private String validationRule;

    /**
     * 选项配置（JSON格式，用于下拉框等）
     */
    @TableField("options")
    private String options;

    /**
     * 占位符
     */
    @TableField("placeholder")
    private String placeholder;

    /**
     * 描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态：1-启用，0-禁用
     */
    @TableField("status")
    private Integer status;
}
