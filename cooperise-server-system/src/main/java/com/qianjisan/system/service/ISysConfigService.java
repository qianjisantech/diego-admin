package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.request.SysBatchUpdateFieldConfigRequest;
import com.qianjisan.system.request.SysConfigQueryRequest;
import com.qianjisan.system.request.SysConfigRequest;
import com.qianjisan.system.vo.SysConfigVO;
import com.qianjisan.system.vo.SysFieldConfigVO;
import com.qianjisan.system.entity.SysConfig;

import java.util.List;

/**
 * 配置服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 保存配置
     *
     * @param request 配置请求
     */
    void saveConfig(SysConfigRequest request);

    /**
     * 更新配置
     *
     * @param id 配置ID
     * @param request 配置请求
     */
    void updateConfigById(Long id, SysConfigRequest request);

    /**
     * 分页查询配置
     *
     * @param request 查询请求
     * @return 分页结果
     */
    Page<SysConfigVO> queryConfigPage(SysConfigQueryRequest request);

    /**
     * 根据配置组查询配�?
     *
     * @param group 配置�?
     * @return 配置列表
     */
    List<SysConfigVO> getConfigByGroup(String group);

    /**
     * 获取字段配置列表
     *
     * @param moduleCode 模块编码
     * @return 字段配置列表
     */
    List<SysFieldConfigVO> getFieldConfigList(String moduleCode);

    /**
     * 更新字段配置
     *
     * @param id 字段配置ID
     * @param isVisible 是否可见
     * @param isRequired 是否必填
     * @param isEditable 是否可编�?
     */
    void updateFieldConfig(Long id, Integer isVisible, Integer isRequired, Integer isEditable);

    /**
     * 批量更新字段配置
     *
     * @param request 批量更新请求
     */
    void batchUpdateFieldConfig(SysBatchUpdateFieldConfigRequest request);
}
