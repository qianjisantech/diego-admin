package com.qianjisan.system.request;


import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRoleQueryRequest extends PageRequest {

    /**
     * 角色编码（模糊查询）
     */
    private String roleCode;

    /**
     * 角色名称（模糊查询）
     */
    private String roleName;

    /**
     * 状态：1-启用�?-禁用
     */
    private Integer status;
}
