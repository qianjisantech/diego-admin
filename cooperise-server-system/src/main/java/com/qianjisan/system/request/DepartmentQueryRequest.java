package com.qianjisan.system.request;


import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DepartmentQueryRequest extends PageRequest {

    /**
     * 部门编码（模糊查询）
     */
    private String deptCode;

    /**
     * 部门名称（模糊查询）
     */
    private String deptName;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;
}






