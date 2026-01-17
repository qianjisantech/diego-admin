package com.qianjisan.system.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字段配置VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysFieldConfigVO extends BaseVO {

    /**
     * 模块编码
     */
    private String moduleCode;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 是否可见�?-是，0-�?
     */
    private Integer isVisible;

    /**
     * 是否必填�?-是，0-�?
     */
    private Integer isRequired;

    /**
     * 是否可编辑：1-是，0-�?
     */
    private Integer isEditable;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 默认�?
     */
    private String defaultValue;

    /**
     * 验证规则（JSON格式�?
     */
    private String validationRule;

    /**
     * 选项配置（JSON格式�?
     */
    private String options;

    /**
     * 占位�?
     */
    private String placeholder;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1-启用�?-禁用
     */
    private Integer status;
}
