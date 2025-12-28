package com.qianjisan.enterprise.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.Result;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.request.CompanyQueryRequest;
import com.qianjisan.enterprise.request.CompanyRequest;
import com.qianjisan.enterprise.service.ICompanyService;
import com.qianjisan.common.vo.CompanyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 公司管理控制器（使用 Request/Vo DTO）
 */
@Tag(name = "公司管理", description = "Company 相关接口")
@RestController
@RequestMapping("/enterprise/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final ICompanyService companyService;

    @Operation(summary = "创建公司")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody CompanyRequest request) {
        try {
            Company entity = toEntity(request);
            companyService.save(entity);
            return Result.success();
        } catch (Exception e) {
            log.error("创建公司失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "更新公司")
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        try {
            Company entity = toEntity(request);
            entity.setId(id);
            companyService.updateById(entity);
            return Result.success();
        } catch (Exception e) {
            log.error("更新公司失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除公司")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        try {
            companyService.removeById(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除公司失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "根据ID查询公司")
    @GetMapping("/{id}")
    public Result<CompanyVo> getById(@PathVariable Long id) {
        try {
            Company company = companyService.getById(id);
            CompanyVo vo = toVo(company);
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询公司失败", e);
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "分页查询公司")
    @PostMapping("/page")
    public Result<PageVO<CompanyVo>> page(@RequestBody CompanyQueryRequest request) {
        try {
            QueryWrapper<Company> wrapper = new QueryWrapper<>();
            if (request.getCompanyName() != null && !request.getCompanyName().isBlank()) {
                wrapper.like("company_name", request.getCompanyName());
            }
            if (request.getCompanyCode() != null && !request.getCompanyCode().isBlank()) {
                wrapper.eq("company_code", request.getCompanyCode());
            }
            Page<Company> page = companyService.page(new Page<>(request.getCurrent(), request.getSize()), wrapper);
            PageVO<CompanyVo> pageVO = new PageVO<>();
            pageVO.setRecords(page.getRecords().stream().map(this::toVo).collect(Collectors.toList()));
            pageVO.setTotal(page.getTotal());
            pageVO.setCurrent(page.getCurrent());
            pageVO.setSize(page.getSize());
            pageVO.setPages(page.getPages());
            return Result.success(pageVO);
        } catch (Exception e) {
            log.error("分页查询公司失败", e);
            return Result.error(e.getMessage());
        }
    }

    private Company toEntity(CompanyRequest req) {
        if (req == null) return null;
        Company c = new Company();
        c.setCompanyCode(req.getCompanyCode());
        c.setCompanyName(req.getCompanyName());
        c.setShortName(req.getShortName());
        c.setCreditCode(req.getCreditCode());
        c.setDescription(req.getDescription());
        c.setContactPerson(req.getContactPerson());
        c.setContactPhone(req.getContactPhone());
        c.setContactEmail(req.getContactEmail());
        c.setAddress(req.getAddress());
        c.setStatus(req.getStatus());
        return c;
    }

    private CompanyVo toVo(Company c) {
        if (c == null) return null;
        CompanyVo v = new CompanyVo();
        v.setId(c.getId());
        v.setCompanyCode(c.getCompanyCode());
        v.setCompanyName(c.getCompanyName());
        v.setShortName(c.getShortName());
        v.setCreditCode(c.getCreditCode());
        v.setDescription(c.getDescription());
        v.setContactPerson(c.getContactPerson());
        v.setContactPhone(c.getContactPhone());
        v.setContactEmail(c.getContactEmail());
        v.setAddress(c.getAddress());
        v.setStatus(c.getStatus());
        return v;
    }
}


