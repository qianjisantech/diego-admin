package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * TemplateField 返回体 (VO)
 */
@Data
public class TemplateFieldVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模板字段ID
     */
    private Long id;

    /**
     * 模板id
     */
    private Long templateId;

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
     * 字段默认值
     */
    private String fieldDefaultValue;

    /**
     * 是否可编辑
     */
    private Byte isEdit;

    /**
     * 是否必填
     */
    private Byte isRequired;

    /**
     * 提示内容
     */
    private String promptContent;

    /**
     * 字段顺序
     */
    private Integer sort;

}

