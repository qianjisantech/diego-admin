package com.qianjisan.core;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页查询基类
 * 提供统一的分页参�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Getter
@Setter
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码，从1开�?
     * 默认值为1
     */
    private Integer current = 1;

    /**
     * 每页显示条数
     * 默认值为10
     */
    private Integer size = 10;

    /**
     * 获取当前页码，如果为空或小于1则返回默认�?
     *
     * @return 当前页码
     */
    public Integer getCurrent() {
        return current == null || current < 1 ? 1 : current;
    }

    /**
     * 获取每页大小，如果为空或小于1则返回默认�?0
     *
     * @return 每页大小
     */
    public Integer getSize() {
        return size == null || size < 1 ? 10 : size;
    }
}

