package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.request.SysDictTypeQueryRequest;
import com.qianjisan.system.request.SysDictDataRequest;
import com.qianjisan.system.request.SysDictTypeRequest;
import com.qianjisan.system.vo.SysDictDataVO;
import com.qianjisan.system.vo.SysDictTypeVO;
import com.qianjisan.system.entity.SysDictType;

import java.util.List;

/**
 * 字典服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysDictService extends IService<SysDictType> {

    /**
     * 保存字典类型
     *
     * @param request 字典类型请求
     */
    void saveDictType(SysDictTypeRequest request);

    /**
     * 更新字典类型
     *
     * @param id 字典类型ID
     * @param request 字典类型请求
     */
    void updateDictTypeById(Long id, SysDictTypeRequest request);

    /**
     * 分页查询字典类型
     *
     * @param request 查询请求
     * @return 分页结果
     */
    Page<SysDictTypeVO> queryDictTypePage(SysDictTypeQueryRequest request);

    /**
     * 保存字典数据
     *
     * @param request 字典数据请求
     */
    void saveDictData(SysDictDataRequest request);

    /**
     * 更新字典数据
     *
     * @param id 字典数据ID
     * @param request 字典数据请求
     */
    void updateDictDataById(Long id, SysDictDataRequest request);

    /**
     * 查询字典数据列表
     *
     * @param dictTypeId 字典类型ID
     * @return 字典数据列表
     */
    List<SysDictDataVO> getDictDataList(Long dictTypeId);

    /**
     * 根据字典编码查询字典数据
     *
     * @param dictCode 字典编码
     * @return 字典数据列表
     */
    List<SysDictDataVO> getDictDataByCode(String dictCode);
}
