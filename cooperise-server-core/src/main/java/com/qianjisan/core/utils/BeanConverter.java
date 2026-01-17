package com.qianjisan.core.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Bean转换工具类
 * 用于Entity和VO之间的转换
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class BeanConverter {

    /**
     * 单个对象转换
     *
     * @param source 源对象
     * @param targetSupplier 目标对象供应器
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 转换后的目标对象
     */
    public static <S, T> T convert(S source, Supplier<T> targetSupplier) {
        if (source == null) {
            return null;
        }
        T target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 列表转换
     *
     * @param sourceList 源列表
     * @param targetSupplier 目标对象供应器
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 转换后的目标列表
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Supplier<T> targetSupplier) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map(source -> convert(source, targetSupplier))
                .collect(Collectors.toList());
    }

    /**
     * 分页对象转换
     *
     * @param sourcePage 源分页对象
     * @param targetSupplier 目标对象供应器
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 转换后的目标分页对象
     */
    public static <S, T> Page<T> convertPage(Page<S> sourcePage, Supplier<T> targetSupplier) {
        if (sourcePage == null) {
            return new Page<>();
        }

        Page<T> targetPage = new Page<>(sourcePage.getCurrent(), sourcePage.getSize(), sourcePage.getTotal());
        List<T> records = convertList(sourcePage.getRecords(), targetSupplier);
        targetPage.setRecords(records);
        return targetPage;
    }

    /**
     * 带自定义转换逻辑的单个对象转换
     *
     * @param source 源对象
     * @param targetSupplier 目标对象供应器
     * @param customizer 自定义转换逻辑
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 转换后的目标对象
     */
    public static <S, T> T convertWithCustom(S source, Supplier<T> targetSupplier, CustomConverter<S, T> customizer) {
        if (source == null) {
            return null;
        }
        T target = convert(source, targetSupplier);
        if (customizer != null) {
            customizer.customize(source, target);
        }
        return target;
    }

    /**
     * 带自定义转换逻辑的列表转换
     *
     * @param sourceList 源列表
     * @param targetSupplier 目标对象供应器
     * @param customizer 自定义转换逻辑
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return 转换后的目标列表
     */
    public static <S, T> List<T> convertListWithCustom(List<S> sourceList, Supplier<T> targetSupplier, CustomConverter<S, T> customizer) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>();
        }
        return sourceList.stream()
                .map(source -> convertWithCustom(source, targetSupplier, customizer))
                .collect(Collectors.toList());
    }

    /**
     * 自定义转换接口
     */
    @FunctionalInterface
    public interface CustomConverter<S, T> {
        /**
         * 自定义转换逻辑
         *
         * @param source 源对象
         * @param target 目标对象
         */
        void customize(S source, T target);
    }
}
