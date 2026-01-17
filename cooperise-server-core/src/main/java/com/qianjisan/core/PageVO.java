package com.qianjisan.core;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 分页结果VO
 * 统一前后端分页字段
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class PageVO<T> {

    /**
     * 数据列表
     */
    private List<T> records;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 每页显示条数
     */
    private Long size;

    /**
     * 当前页
     */
    private Long current;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 是否有上一页
     */
    private Boolean hasPrevious;

    /**
     * 是否有下一页
     */
    private Boolean hasNext;

    /**
     * 从 MyBatis-Plus 的 Page 对象转换
     *
     * @param page MyBatis-Plus 分页对象
     * @param <T>  数据类型
     * @return PageVO
     */
    public static <T> PageVO<T> of(Page<T> page) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords());
        pageVO.setTotal(page.getTotal());
        pageVO.setSize(page.getSize());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setPages(page.getPages());
        pageVO.setHasPrevious(page.hasPrevious());
        pageVO.setHasNext(page.hasNext());
        return pageVO;
    }
}
