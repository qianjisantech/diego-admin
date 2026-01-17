package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.utils.PageUtils;
import com.qianjisan.system.request.SysBatchUpdateFieldConfigRequest;
import com.qianjisan.system.request.SysConfigQueryRequest;
import com.qianjisan.system.request.SysConfigRequest;

import com.qianjisan.system.vo.SysConfigVO;
import com.qianjisan.system.vo.SysFieldConfigVO;
import com.qianjisan.system.entity.SysConfig;
import com.qianjisan.system.entity.SysFieldConfig;
import com.qianjisan.system.mapper.SysConfigMapper;
import com.qianjisan.system.mapper.SysFieldConfigMapper;
import com.qianjisan.system.service.ISysConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 配置服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    private final SysConfigMapper configMapper;
    private final SysFieldConfigMapper fieldConfigMapper;

    @Override
    public void saveConfig(SysConfigRequest request) {
        SysConfig config = new SysConfig();
        BeanUtils.copyProperties(request, config);
        configMapper.insert(config);
    }

    @Override
    public void updateConfigById(Long id, SysConfigRequest request) {
        SysConfig config = new SysConfig();
        config.setId(id);
        BeanUtils.copyProperties(request, config);
        configMapper.updateById(config);
    }

    @Override
    public Page<SysConfigVO> queryConfigPage(SysConfigQueryRequest request) {
        Page<SysConfig> page = new Page<>(request.getCurrent(), request.getPageSize());

        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(request.getConfigKey()), SysConfig::getConfigKey, request.getConfigKey())
                .like(StringUtils.hasText(request.getConfigName()), SysConfig::getConfigName, request.getConfigName())
                .eq(StringUtils.hasText(request.getConfigGroup()), SysConfig::getConfigGroup, request.getConfigGroup())
                .eq(request.getStatus() != null, SysConfig::getStatus, request.getStatus())
                .orderByDesc(SysConfig::getCreateTime);

        Page<SysConfig> entityPage = configMapper.selectPage(page, wrapper);

        return PageUtils.convertPage(entityPage, config -> {
            SysConfigVO vo = new SysConfigVO();
            BeanUtils.copyProperties(config, vo);
            return vo;
        });
    }

    @Override
    public List<SysConfigVO> getConfigByGroup(String group) {
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysConfig::getConfigGroup, group)
                .eq(SysConfig::getStatus, 1);

        List<SysConfig> configs = configMapper.selectList(wrapper);

        return configs.stream().map(config -> {
            SysConfigVO vo = new SysConfigVO();
            BeanUtils.copyProperties(config, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysFieldConfigVO> getFieldConfigList(String moduleCode) {
        LambdaQueryWrapper<SysFieldConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFieldConfig::getModuleCode, moduleCode)
                .orderByAsc(SysFieldConfig::getSortOrder);

        List<SysFieldConfig> configs = fieldConfigMapper.selectList(wrapper);

        return configs.stream().map(config -> {
            SysFieldConfigVO vo = new SysFieldConfigVO();
            BeanUtils.copyProperties(config, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateFieldConfig(Long id, Integer isVisible, Integer isRequired, Integer isEditable) {
        SysFieldConfig config = new SysFieldConfig();
        config.setId(id);
        config.setIsVisible(isVisible);
        config.setIsRequired(isRequired);
        config.setIsEditable(isEditable);
        fieldConfigMapper.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateFieldConfig(SysBatchUpdateFieldConfigRequest request) {
        for (SysBatchUpdateFieldConfigRequest.FieldConfigItem item : request.getConfigs()) {
            updateFieldConfig(item.getId(), item.getIsVisible(), item.getIsRequired(), item.getIsEditable());
        }
    }
}
