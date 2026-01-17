package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.utils.PageUtils;
import com.qianjisan.system.request.SysDictTypeQueryRequest;
import com.qianjisan.system.request.SysDictDataRequest;
import com.qianjisan.system.request.SysDictTypeRequest;

import com.qianjisan.system.vo.SysDictDataVO;
import com.qianjisan.system.vo.SysDictTypeVO;
import com.qianjisan.system.entity.SysDictData;
import com.qianjisan.system.entity.SysDictType;
import com.qianjisan.system.mapper.SysDictDataMapper;
import com.qianjisan.system.mapper.SysDictTypeMapper;
import com.qianjisan.system.service.ISysDictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictService {

    private final SysDictTypeMapper dictTypeMapper;
    private final SysDictDataMapper dictDataMapper;

    @Override
    public void saveDictType(SysDictTypeRequest request) {
        SysDictType dictType = new SysDictType();
        BeanUtils.copyProperties(request, dictType);
        dictTypeMapper.insert(dictType);
    }

    @Override
    public void updateDictTypeById(Long id, SysDictTypeRequest request) {
        SysDictType dictType = new SysDictType();
        dictType.setId(id);
        BeanUtils.copyProperties(request, dictType);
        dictTypeMapper.updateById(dictType);
    }

    @Override
    public Page<SysDictTypeVO> queryDictTypePage(SysDictTypeQueryRequest request) {
        Page<SysDictType> page = new Page<>(request.getCurrent(), request.getPageSize());

        LambdaQueryWrapper<SysDictType> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(request.getDictCode()), SysDictType::getDictCode, request.getDictCode())
                .like(StringUtils.hasText(request.getDictName()), SysDictType::getDictName, request.getDictName())
                .eq(request.getStatus() != null, SysDictType::getStatus, request.getStatus())
                .orderByDesc(SysDictType::getCreateTime);

        Page<SysDictType> entityPage = dictTypeMapper.selectPage(page, wrapper);

        return PageUtils.convertPage(entityPage, dictType -> {
            SysDictTypeVO vo = new SysDictTypeVO();
            BeanUtils.copyProperties(dictType, vo);
            return vo;
        });
    }

    @Override
    public void saveDictData(SysDictDataRequest request) {
        SysDictData dictData = new SysDictData();
        BeanUtils.copyProperties(request, dictData);
        dictDataMapper.insert(dictData);
    }

    @Override
    public void updateDictDataById(Long id, SysDictDataRequest request) {
        SysDictData dictData = new SysDictData();
        dictData.setId(id);
        BeanUtils.copyProperties(request, dictData);
        dictDataMapper.updateById(dictData);
    }

    @Override
    public List<SysDictDataVO> getDictDataList(Long dictTypeId) {
        LambdaQueryWrapper<SysDictData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDictData::getDictTypeId, dictTypeId)
                .eq(SysDictData::getStatus, 1)
                .orderByAsc(SysDictData::getSortOrder);

        List<SysDictData> dataList = dictDataMapper.selectList(wrapper);

        return dataList.stream().map(data -> {
            SysDictDataVO vo = new SysDictDataVO();
            BeanUtils.copyProperties(data, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysDictDataVO> getDictDataByCode(String dictCode) {
        // 先根据编码查询字典类型
        LambdaQueryWrapper<SysDictType> typeWrapper = new LambdaQueryWrapper<>();
        typeWrapper.eq(SysDictType::getDictCode, dictCode);
        SysDictType dictType = dictTypeMapper.selectOne(typeWrapper);

        if (dictType == null) {
            return List.of();
        }

        // 再查询字典数据
        return getDictDataList(dictType.getId());
    }
}
