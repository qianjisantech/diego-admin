package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.enterprise.entity.Company;
import com.qianjisan.enterprise.mapper.CompanyMapper;
import com.qianjisan.enterprise.service.ICompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 公司表 Service 实现
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements ICompanyService {
}

