package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.enterprise.entity.EnterpriseDepartment;
import com.qianjisan.enterprise.mapper.EnterpriseDepartmentMapper;
import com.qianjisan.enterprise.service.IEnterpriseDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 公司与部门关联表 Service 实现
 */
@Slf4j
@Service
public class EnterpriseDepartmentServiceImpl extends ServiceImpl<EnterpriseDepartmentMapper, EnterpriseDepartment> implements IEnterpriseDepartmentService {
}

