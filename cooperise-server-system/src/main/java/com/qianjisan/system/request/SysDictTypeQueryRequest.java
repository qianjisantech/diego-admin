package com.qianjisan.system.request;


import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeQueryRequest extends PageRequest {

    /**
     * 字典编码（模糊查询）
     */
    private String dictCode;

    /**
     * 字典名称（模糊查询）
     */
    private String dictName;

    /**
     * 状态：1-启用，0-禁用
     */
    private Integer status;
}
