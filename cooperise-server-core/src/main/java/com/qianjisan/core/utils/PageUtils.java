package com.qianjisan.core.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具类
 * 提供分页数据转换等通用方法
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class PageUtils {

    /**
     * 将 Page<T> 转换为 Page<R>
     * 用于实体对象到 VO 对象的分页转换
     *
     * @param sourcePage 源分页对象
     * @param converter  转换函数
     * @param <T>        源类型
     * @param <R>        目标类型
     * @return 转换后的分页对象
     */
    public static <T, R> Page<R> convertPage(Page<T> sourcePage, Function<T, R> converter) {
        if (sourcePage == null) {
            return new Page<>();
        }

        // 创建目标分页对象，保留分页信息
        Page<R> targetPage = new Page<>(
            sourcePage.getCurrent(),
            sourcePage.getSize(),
            sourcePage.getTotal()
        );

        // 转换记录列表
        List<R> records = sourcePage.getRecords().stream()
                .map(converter)
                .collect(Collectors.toList());

        targetPage.setRecords(records);
        return targetPage;
    }

    /**
     * 创建空的分页对象
     *
     * @param current 当前页
     * @param size    每页大小
     * @param <T>     数据类型
     * @return 空分页对象
     */
    public static <T> Page<T> emptyPage(long current, long size) {
        return new Page<>(current, size, 0);
    }
}
