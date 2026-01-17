package com.qianjisan.system.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTypeVO extends BaseVO {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态：1-启用�?-禁用
     */
    private Integer status;
}
