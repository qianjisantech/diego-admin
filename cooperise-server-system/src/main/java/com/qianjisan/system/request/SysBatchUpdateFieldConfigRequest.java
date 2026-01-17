package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 批量更新字段配置请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysBatchUpdateFieldConfigRequest {

    /**
     * 字段配置列表
     */
    @NotEmpty(message = "字段配置列表不能为空")
    private List<FieldConfigItem> configs;

    @Data
    public static class FieldConfigItem {
        /**
         * 字段配置ID
         */
        private Long id;

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
    }
}
