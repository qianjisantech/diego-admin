package com.qianjisan.enterprise.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.TemplateField;
import com.qianjisan.enterprise.mapper.TemplateFieldMapper;
import com.qianjisan.enterprise.request.BatchSaveTemplateFieldRequest;
import com.qianjisan.enterprise.request.TemplateFieldQueryRequest;

import com.qianjisan.enterprise.service.TemplateFieldService;
import com.qianjisan.enterprise.vo.TemplateFieldVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.enterprise.vo.TemplateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 模板字段 服务实现类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Service
@RequiredArgsConstructor
public class TemplateFieldServiceImpl extends ServiceImpl<TemplateFieldMapper, TemplateField> implements TemplateFieldService {

    @Override
    public void batchSaveTemplateFields(BatchSaveTemplateFieldRequest request) {
        List<TemplateField> toSave = new ArrayList<>();
        List<TemplateField> toUpdate = new ArrayList<>();

        // 获取当前用户信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();

        for (BatchSaveTemplateFieldRequest.TemplateFieldRequest fieldRequest : request.getFields()) {
            TemplateField templateField = BeanUtil.copyProperties(fieldRequest, TemplateField.class);

            if (fieldRequest.getId() == null) {
                // 新增
                templateField.setTemplateId(request.getTemplateId());
                templateField.setCreateTime(LocalDateTime.now());
                templateField.setUpdateTime(LocalDateTime.now());

                // 设置创建人信息
                if (userId != null) {
                    templateField.setCreateById(userId);
                    templateField.setCreateByCode(username);
                    templateField.setCreateByName(nickname);
                    templateField.setUpdateById(userId);
                    templateField.setUpdateByCode(username);
                    templateField.setUpdateByName(nickname);
                }

                toSave.add(templateField);
            } else {
                // 更新
                templateField.setUpdateTime(LocalDateTime.now());

                // 设置更新人信息
                if (userId != null) {
                    templateField.setUpdateById(userId);
                    templateField.setUpdateByCode(username);
                    templateField.setUpdateByName(nickname);
                }

                toUpdate.add(templateField);
            }
        }

        // 批量保存和更新
        if (!toSave.isEmpty()) {
            this.saveBatch(toSave);
        }
        if (!toUpdate.isEmpty()) {
            this.updateBatchById(toUpdate);
        }
    }


    @Override
    public void deleteTemplateField(Long id) {
        TemplateField templateField = this.getById(id);
        if (templateField == null) {
            throw new RuntimeException("模板字段不存在");
        }

        this.removeById(id);
    }



    @Override
    public List<TemplateFieldVo> getFieldsByTemplateId(Long templateId) {
        LambdaQueryWrapper<TemplateField> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TemplateField::getTemplateId, templateId)
                .orderByAsc(TemplateField::getSort);

        List<TemplateField> templateFields = this.list(queryWrapper);
        if (CollectionUtil.isEmpty(templateFields)){
            return List.of();
        }else {
           return templateFields.stream().map(templateFieldVo -> {
                       TemplateFieldVo templateDetailVo = new TemplateFieldVo();
                        templateDetailVo.setId(templateFieldVo.getId());
                        templateDetailVo.setTemplateId(templateFieldVo.getTemplateId());
                        templateDetailVo.setFieldName(templateFieldVo.getFieldName());
                        templateDetailVo.setFieldType(templateFieldVo.getFieldType());
                        templateDetailVo.setFieldCode(templateFieldVo.getFieldCode());
                        templateDetailVo.setIsEdit(templateFieldVo.getIsEdit());
                        templateDetailVo.setIsRequired(templateFieldVo.getIsRequired());
                        templateDetailVo.setPromptContent(templateFieldVo.getPromptContent());
                        templateDetailVo.setSort(templateFieldVo.getSort());
                        return templateDetailVo;
                    }).sorted(Comparator.comparing(TemplateFieldVo::getSort, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
        }

    }
}
