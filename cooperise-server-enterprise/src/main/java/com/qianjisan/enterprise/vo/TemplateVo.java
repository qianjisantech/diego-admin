package com.qianjisan.enterprise.vo;

import com.qianjisan.enterprise.service.TemplateFieldService;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Template 返回体 (VO)
 */
@Data
public class TemplateVo implements Serializable {

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


    private List<TemplateFieldVo> templateFieldVos;


    @Data
    public  static  class TemplateFieldVo{
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

        /**
         * 位置
         */
        private String position;

    }
}
