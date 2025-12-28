package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 部门请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class DepartmentRequest {

    /**
     * 部门ID（更新时需要）
     */
    private Long id;

    /**
     * 部门编码
     */
    @NotBlank(message = "部门编码不能为空")
    private String deptCode;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String deptName;

    /**
     * 父部门ID，0表示顶级部门
     */
    private Long parentId;

    /**
     * 部门描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;

    /**
     * 负责人ID
     */
    private Long leaderId;

    /**
     * 负责人姓名
     */
    private String leaderName;

    /**
     * 负责人工号
     */
    private String leaderCode;
}






