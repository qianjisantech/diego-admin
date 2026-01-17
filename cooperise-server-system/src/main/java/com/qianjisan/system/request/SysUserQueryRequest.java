package com.qianjisan.system.request;

import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserQueryRequest extends PageRequest {

    /**
     * 姓名（模糊查询）
     */
    private String name;


    /**
     * 状态：1-正常�?-禁用
     */
    private Integer status;
}
