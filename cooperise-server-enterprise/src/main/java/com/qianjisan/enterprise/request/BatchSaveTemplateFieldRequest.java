package com.qianjisan.enterprise.request;

import com.qianjisan.enterprise.validation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;



/**
 * 批量保存模板字段请求
 */
@Data
public class BatchSaveTemplateFieldRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "模板ID不能为空")
    private Long templateId;

    @NotNull(message = "字段列表不能为空")
    private List<TemplateFieldRequest> fields;

    /**
     * TemplateField 请求体
     */
    @Data
    public static class TemplateFieldRequest implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private Long id;

        @NotBlank(message = "字段编码不能为空")
        private String fieldCode;

        @NotBlank(message = "字段名称不能为空")
        private String fieldName;

        @NotBlank(message = "字段类型不能为空")
        private String fieldType;

        @JsonFormat(message = "字段默认值必须是有效的 JSON 格式")
        private String fieldDefaultValue;

        /**
         * 是否可编辑 1是 0否，默认是
         */
        private Byte isEdit = 1;

        /**
         * 是否必填 1是 0否，默认否
         */
        private Byte isRequired = 0;

        private String promptContent;

        private Integer sort;

        private String position;

        /**
         * 状态：1-启用 0-禁用，默认启用
         */
        private Integer status = 1;
    }
}
