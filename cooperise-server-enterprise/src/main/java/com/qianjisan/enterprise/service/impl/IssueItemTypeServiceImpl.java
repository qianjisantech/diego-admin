package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.enterprise.entity.IssueItemType;
import com.qianjisan.enterprise.mapper.IssueItemTypeMapper;
import com.qianjisan.enterprise.request.IssueItemTypeQueryRequest;
import com.qianjisan.enterprise.request.IssueItemTypeRequest;
import com.qianjisan.enterprise.service.IssueItemTypeService;
import com.qianjisan.enterprise.vo.IssueItemTypeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IssueItemTypeServiceImpl extends ServiceImpl<IssueItemTypeMapper, IssueItemType> implements IssueItemTypeService {

    @Override
    public boolean createIssueItemType(IssueItemTypeRequest request) {
        IssueItemType entity = convertToEntity(request);

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
    public boolean updateIssueItemType(Long id, IssueItemTypeRequest request) {
        IssueItemType entity = convertToEntity(request);
        entity.setId(id);

        UserContext userContext = UserContextHolder.getUser();
        if (userContext != null) {
            entity.setUpdateByCode(userContext.getUserCode());
            entity.setUpdateByName(userContext.getUsername());
        }

        return this.updateById(entity);
    }

    @Override
    public boolean deleteIssueItemType(Long id) {
        return this.removeById(id);
    }

    @Override
    public IssueItemTypeVo getIssueItemTypeById(Long id) {
        IssueItemType entity = this.getById(id);
        return convertToVo(entity);
    }

    @Override
    public PageVO<IssueItemTypeVo> getIssueItemTypePage(IssueItemTypeQueryRequest request) {
        QueryWrapper<IssueItemType> wrapper = new QueryWrapper<>();

        if (request.getName() != null && !request.getName().isBlank()) {
            wrapper.like("name", request.getName());
        }

        if (request.getCode() != null && !request.getCode().isBlank()) {
            wrapper.like("code", request.getCode());
        }

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            wrapper.eq("status", request.getStatus());
        }

        if (request.getDepartmentId() != null) {
            wrapper.eq("department_id", request.getDepartmentId());
        }

        if (request.getCompanyId() != null) {
            wrapper.eq("company_id", request.getCompanyId());
        }

        wrapper.orderByDesc("create_time");

        Page<IssueItemType> page = this.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);

        PageVO<IssueItemTypeVo> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords().stream().map(this::convertToVo).collect(Collectors.toList()));
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setPages(page.getPages());
        return pageVO;
    }

    @Override
    public IssueItemType convertToEntity(IssueItemTypeRequest request) {
        if (request == null) return null;
        IssueItemType entity = new IssueItemType();
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setStatus(request.getStatus());
        entity.setDescription(request.getDescription());
        entity.setDepartmentId(request.getDepartmentId());
        entity.setCompanyId(request.getCompanyId());
        return entity;
    }

    @Override
    public IssueItemTypeVo convertToVo(IssueItemType entity) {
        if (entity == null) return null;
        IssueItemTypeVo vo = new IssueItemTypeVo();
        vo.setId(entity.getId());
        vo.setName(entity.getName());
        vo.setCode(entity.getCode());
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setDepartmentId(entity.getDepartmentId());
        vo.setCompanyId(entity.getCompanyId());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        vo.setCreateByCode(entity.getCreateByCode());
        vo.setUpdateByCode(entity.getUpdateByCode());
        vo.setIsDeleted(entity.getIsDeleted());
        vo.setCreateByName(entity.getCreateByName());
        vo.setUpdateByName(entity.getUpdateByName());
        return vo;
    }
}
