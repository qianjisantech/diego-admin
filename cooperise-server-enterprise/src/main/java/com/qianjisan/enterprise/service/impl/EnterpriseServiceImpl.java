package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.Enterprise;
import com.qianjisan.enterprise.mapper.EnterpriseMapper;
import com.qianjisan.enterprise.request.EnterpriseQueryRequest;
import com.qianjisan.enterprise.request.EnterpriseRequest;
import com.qianjisan.enterprise.service.IEnterpriseService;
import com.qianjisan.enterprise.vo.EnterpriseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {

    @Override
    public boolean createEnterprise(EnterpriseRequest request) {
        Enterprise entity = convertToEntity(request);

        UserContext userContext = UserContextHolder.getUser();
        if (userContext != null) {
            entity.setCreateByCode(userContext.getUserCode());
            entity.setCreateByName(userContext.getUsername());
            entity.setUpdateByCode(userContext.getUserCode());
            entity.setUpdateByName(userContext.getUsername());
        }

        entity.setIsDeleted(0);
        return this.save(entity);
    }

    @Override
    public boolean updateEnterprise(Long id, EnterpriseRequest request) {
        Enterprise entity = convertToEntity(request);
        entity.setId(id);

        UserContext userContext = UserContextHolder.getUser();
        if (userContext != null) {
            entity.setUpdateByCode(userContext.getUserCode());
            entity.setUpdateByName(userContext.getUsername());
        }

        return this.updateById(entity);
    }

    @Override
    public boolean deleteEnterprise(Long id) {
        return this.removeById(id);
    }

    @Override
    public EnterpriseVo getEnterpriseById(Long id) {
        Enterprise enterprise = this.getById(id);
        return convertToVo(enterprise);
    }

    @Override
    public PageVO<EnterpriseVo> getEnterprisePage(EnterpriseQueryRequest request) {
        QueryWrapper<Enterprise> wrapper = new QueryWrapper<>();

        if (request.getEnterpriseName() != null && !request.getEnterpriseName().isBlank()) {
            wrapper.like("enterprise_name", request.getEnterpriseName());
        }

        if (request.getEnterpriseCode() != null && !request.getEnterpriseCode().isBlank()) {
            wrapper.like("enterprise_code", request.getEnterpriseCode());
        }

        wrapper.orderByDesc("create_time");

        Page<Enterprise> page = this.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);

        PageVO<EnterpriseVo> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords().stream().map(this::convertToVo).collect(Collectors.toList()));
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setPages(page.getPages());
        return pageVO;
    }

    @Override
    public Enterprise convertToEntity(EnterpriseRequest request) {
        if (request == null) return null;
        Enterprise entity = new Enterprise();
        entity.setCode(request.getEnterpriseCode());
        entity.setName(request.getEnterpriseName());
        entity.setShortName(request.getShortName());
        entity.setDescription(request.getDescription());
        entity.setContactPerson(request.getContactPerson());
        entity.setContactPhone(request.getContactPhone());
        entity.setContactEmail(request.getContactEmail());
        entity.setAddress(request.getAddress());
        entity.setStatus(request.getStatus());
        return entity;
    }

    @Override
    public EnterpriseVo convertToVo(Enterprise enterprise) {
        if (enterprise == null) return null;
        EnterpriseVo vo = new EnterpriseVo();
        vo.setId(enterprise.getId());
        vo.setCode(enterprise.getCode());
        vo.setName(enterprise.getName());
        vo.setShortName(enterprise.getShortName());
        return vo;
    }
}