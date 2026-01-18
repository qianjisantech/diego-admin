package com.qianjisan.enterprise.service;

import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.TemplateField;
import com.qianjisan.enterprise.request.BatchSaveTemplateFieldRequest;
import com.qianjisan.enterprise.request.TemplateFieldQueryRequest;

import com.qianjisan.enterprise.vo.TemplateFieldVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模板字段 服务类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
public interface TemplateFieldService extends IService<TemplateField> {



    /**
     * 删除模板字段
     */
    void deleteTemplateField(Long id);

    /**
     * 批量保存模板字段
     */
    void batchSaveTemplateFields(BatchSaveTemplateFieldRequest request);


    /**
     * 根据模板ID查询字段列表
     */
    List<TemplateFieldVo> getFieldsByTemplateId(Long templateId);
}
