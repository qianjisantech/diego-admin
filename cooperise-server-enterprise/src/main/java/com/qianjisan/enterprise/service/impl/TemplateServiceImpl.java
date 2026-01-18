package com.qianjisan.enterprise.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.core.exception.BusinessException;
import com.qianjisan.enterprise.entity.Template;
import com.qianjisan.enterprise.mapper.TemplateMapper;
import com.qianjisan.enterprise.request.TemplateQueryRequest;
import com.qianjisan.enterprise.request.TemplateRequest;
import com.qianjisan.enterprise.service.TemplateFieldService;
import com.qianjisan.enterprise.service.TemplateService;
import com.qianjisan.enterprise.vo.TemplateOptionVo;
import com.qianjisan.enterprise.vo.TemplateQueryPageVo;
import com.qianjisan.enterprise.vo.TemplateVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 事项表 服务实现类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Service
@RequiredArgsConstructor
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {


    private  final TemplateFieldService templateFieldService;

    @Override
    public void createTemplate(TemplateRequest request) {
        Template template = new Template();
        // 使用雪花ID生成唯一编码
        template.setCode(IdUtil.getSnowflakeNextIdStr());
        template.setName(request.getName());
        template.setDescription(request.getDescription());
        template.setCompanyId(request.getCompanyId());
        template.setCompanyCode(request.getCompanyCode());
        template.setCompanyName(request.getCompanyName());
        template.setStatus(request.getStatus());
        template.setIsDefault(request.getIsDefault());

        template.setCreateTime(LocalDateTime.now());
        template.setUpdateTime(LocalDateTime.now());

        // 设置创建人信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();
        if (userId != null) {
            template.setCreateById(userId);
            template.setCreateByCode(username);
            template.setCreateByName(nickname);
            template.setUpdateById(userId);
            template.setUpdateByCode(username);
            template.setUpdateByName(nickname);
        }

        this.save(template);
    }

    @Override
    public void updateTemplate(Long id, TemplateRequest request) {
        Template template = this.getById(id);
        if (template == null) {
            throw new RuntimeException("模板不存在");
        }

        BeanUtil.copyProperties(request, template);
        template.setUpdateTime(LocalDateTime.now());

        // 设置更新人信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();
        if (userId != null) {
            template.setUpdateById(userId);
            template.setUpdateByCode(username);
            template.setUpdateByName(nickname);
        }

        this.updateById(template);
    }

    @Override
    public void deleteTemplate(Long id) {
        Template template = this.getById(id);
        if (template == null) {
            throw new RuntimeException("模板不存在");
        }

        this.removeById(id);
    }

    @Override
    public TemplateVo getTemplateDetailById(Long id) {

        TemplateVo templateVo = new TemplateVo();

        Template template = this.getById(id);
        if (template == null) {
            throw new BusinessException("模板["+id+"]不存在");
        }

        templateVo.setId(template.getId());
        templateVo.setName(template.getName());
        templateVo.setCode(template.getCode());
        templateVo.setDescription(template.getDescription());
        templateVo.setCompanyId(template.getCompanyId());
        templateVo.setCompanyCode(template.getCompanyCode());
        templateVo.setCompanyName(template.getCompanyName());
        templateVo.setStatus(template.getStatus());
        templateVo.setIsDefault(template.getIsDefault());

        List<com.qianjisan.enterprise.vo.TemplateFieldVo> templateFieldVos = templateFieldService.getFieldsByTemplateId(id);
        // 转换为内部VO类型
        List<TemplateVo.TemplateFieldVo> internalFieldVos = templateFieldVos.stream()
                .map(field -> {
                    TemplateVo.TemplateFieldVo internalField = new TemplateVo.TemplateFieldVo();
                    internalField.setId(field.getId());
                    internalField.setTemplateId(field.getTemplateId());
                    internalField.setFieldCode(field.getFieldCode());
                    internalField.setFieldName(field.getFieldName());
                    internalField.setFieldType(field.getFieldType());
                    internalField.setIsEdit(field.getIsEdit());
                    internalField.setIsRequired(field.getIsRequired());
                    internalField.setPromptContent(field.getPromptContent());
                    internalField.setSort(field.getSort());
                    return internalField;
                })
                .collect(Collectors.toList());
        templateVo.setTemplateFieldVos(internalFieldVos);

      return templateVo;
    }

    @Override
    public PageVO<TemplateQueryPageVo> getTemplatePage(TemplateQueryRequest request) {
        LambdaQueryWrapper<Template> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (StringUtils.hasText(request.getCode())) {
            queryWrapper.like(Template::getCode, request.getCode());
        }
        if (StringUtils.hasText(request.getName())) {
            queryWrapper.like(Template::getName, request.getName());
        }
        if (StringUtils.hasText(request.getDescription())) {
            queryWrapper.like(Template::getDescription, request.getDescription());
        }
        if (request.getCompanyId() != null) {
            queryWrapper.eq(Template::getCompanyId, request.getCompanyId());
        }
        if (StringUtils.hasText(request.getCompanyCode())) {
            queryWrapper.eq(Template::getCompanyCode, request.getCompanyCode());
        }
        if (StringUtils.hasText(request.getCompanyName())) {
            queryWrapper.like(Template::getCompanyName, request.getCompanyName());
        }
        if (request.getStatus() != null) {
            queryWrapper.eq(Template::getStatus, request.getStatus());
        }
        if (request.getIsDefault() != null) {
            queryWrapper.eq(Template::getIsDefault, request.getIsDefault());
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(Template::getCreateTime);

        // 分页查询
        IPage<Template> page = this.page(new Page<>(request.getCurrent(), request.getSize()), queryWrapper);

        // 转换为VO
        PageVO<TemplateQueryPageVo> pageVO = new PageVO<>();
        pageVO.setRecords(BeanUtil.copyToList(page.getRecords(), TemplateQueryPageVo.class));
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setPages(page.getPages());

        return pageVO;
    }

    @Override
    public List<TemplateOptionVo> getTemplateOptions(Long companyId) {
        LambdaQueryWrapper<Template> queryWrapper = new LambdaQueryWrapper<>();

        // 只查询启用的模板
        queryWrapper.eq(Template::getStatus, 1);

        // 根据企业ID过滤
        if (companyId != null) {
            queryWrapper.eq(Template::getCompanyId, companyId);
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(Template::getCreateTime);

        List<Template> templates = this.list(queryWrapper);

        // 转换为VO
        return templates.stream()
                .map(template -> {
                    TemplateOptionVo vo = new TemplateOptionVo();
                    vo.setId(template.getId());
                    vo.setName(template.getName());
                    vo.setCode(template.getCode());
                    return vo;
                })
                .collect(Collectors.toList());
    }
}
