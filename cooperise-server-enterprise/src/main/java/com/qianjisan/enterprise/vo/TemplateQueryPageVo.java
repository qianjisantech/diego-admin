package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Template 返回体 (VO)
 */
@Data
public class TemplateQueryPageVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 事项模板ID
     */
    private Long id;

    /**
     * 事项模板编码
     */
    private String code;

    /**
     * 事项模板名称
     */
    private String name;

    /**
     * 模板描述
     */
    private String description;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 企业编码
     */
    private String companyCode;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 状态：1-启用 0-禁用
     */
    private Byte status;


    /**
     * 是否默认模板   1是 0否
     */
    private Boolean isDefault;


}
