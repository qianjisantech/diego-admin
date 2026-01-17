package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 字典数据请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysDictDataRequest {

    /**
     * 字典数据ID（更新时需要）
     */
    private Long id;

    /**
     * 字典类型ID
     */
    @NotNull(message = "字典类型ID不能为空")
    private Long dictTypeId;

    /**
     * 字典标签
     */
    @NotBlank(message = "字典标签不能为空")
    private String dictLabel;

    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String dictValue;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 是否默认：1-是，0-否
     */
    private Integer isDefault;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
