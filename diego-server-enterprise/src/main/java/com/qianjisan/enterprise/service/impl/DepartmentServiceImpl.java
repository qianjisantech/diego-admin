package com.qianjisan.enterprise.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.enterprise.entity.Department;
import com.qianjisan.enterprise.mapper.DepartmentMapper;
import com.qianjisan.enterprise.service.IDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 部门表 Service 实现
 */
@Slf4j
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
}

