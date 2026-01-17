package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 字典类型请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysDictTypeRequest {

    /**
     * 字典类型ID（更新时需要）
     */
    private Long id;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;
}
