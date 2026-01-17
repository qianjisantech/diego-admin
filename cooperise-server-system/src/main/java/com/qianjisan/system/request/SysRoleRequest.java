package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 角色请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysRoleRequest {

    /**
     * 角色ID（更新时需要）
     */
    private Long id;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 状态：1-启用�?-禁用
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sortOrder;
}
