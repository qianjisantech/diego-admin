package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.service.ICompanyService;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.mapper.CompanyMapper;
import com.qianjisan.enterprise.request.CompanyQueryRequest;
import com.qianjisan.enterprise.request.CompanyRequest;
import com.qianjisan.console.request.SelfCompanyRequest;
import com.qianjisan.enterprise.vo.CompanyVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 公司�?Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {



    @Override
    public boolean createCompany(SelfCompanyRequest request) {
        return false;
    }



    @Override
    public boolean updateCompany(Long id, CompanyRequest request) {
        Company entity = convertToEntity(request);
        entity.setId(id);
        return this.updateById(entity);
    }

    @Override
    public boolean deleteCompany(Long id) {
        return this.removeById(id);
    }

    @Override
    public CompanyVo getCompanyById(Long id) {
        Company company = this.getById(id);
        return convertToVo(company);
    }

    @Override
    public PageVO<CompanyVo> getCompanyPage(CompanyQueryRequest request) {
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        if (request.getCompanyName() != null && !request.getCompanyName().isBlank()) {
            wrapper.like("company_name", request.getCompanyName());
        }
        if (request.getCompanyCode() != null && !request.getCompanyCode().isBlank()) {
            wrapper.eq("company_code", request.getCompanyCode());
        }
        Page<Company> page = this.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);
        PageVO<CompanyVo> pageVO = new PageVO<>();
        pageVO.setRecords(page.getRecords().stream().map(this::convertToVo).collect(Collectors.toList()));
        pageVO.setTotal(page.getTotal());
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setPages(page.getPages());
        return pageVO;
    }

    @Override
    public Company convertToEntity(CompanyRequest req) {
        if (req == null) return null;
        Company c = new Company();
        c.setCompanyCode(req.getCompanyCode());
        c.setCompanyName(req.getCompanyName());
        c.setShortName(req.getShortName());
        c.setDescription(req.getDescription());
        c.setContactPerson(req.getContactPerson());
        c.setContactPhone(req.getContactPhone());
        c.setContactEmail(req.getContactEmail());
        c.setAddress(req.getAddress());
        c.setStatus(req.getStatus());
        return c;
    }

    @Override
    public CompanyVo convertToVo(Company c) {
        if (c == null) return null;
        CompanyVo v = new CompanyVo();
        v.setId(c.getId());
        v.setCompanyCode(c.getCompanyCode());
        v.setCompanyName(c.getCompanyName());
        v.setShortName(c.getShortName());
        v.setDescription(c.getDescription());
        v.setContactPerson(c.getContactPerson());
        v.setContactPhone(c.getContactPhone());
        v.setContactEmail(c.getContactEmail());
        v.setAddress(c.getAddress());
        return v;
    }
}

