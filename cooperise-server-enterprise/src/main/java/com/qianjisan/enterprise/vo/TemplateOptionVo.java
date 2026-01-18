package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 模板选项枚举值VO (用于下拉框)
 */
@Data
public class TemplateOptionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板编码
     */
    private String code;
}
