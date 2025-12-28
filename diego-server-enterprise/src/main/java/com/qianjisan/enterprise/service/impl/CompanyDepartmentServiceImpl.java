package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.enterprise.entity.CompanyDepartment;
import com.qianjisan.enterprise.mapper.CompanyDepartmentMapper;
import com.qianjisan.enterprise.service.ICompanyDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 公司与部门关联表 Service 实现
 */
@Slf4j
@Service
public class CompanyDepartmentServiceImpl extends ServiceImpl<CompanyDepartmentMapper, CompanyDepartment> implements ICompanyDepartmentService {
}

