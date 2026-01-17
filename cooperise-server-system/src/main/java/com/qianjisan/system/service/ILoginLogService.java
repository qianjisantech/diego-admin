package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.entity.SysLoginLog;
import com.qianjisan.system.request.SysLoginLogQueryRequest;


/**
 * LoginLog服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ILoginLogService extends IService<SysLoginLog> {

    /**
     * 分页查询登录日志管理
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<SysLoginLog> pageLoginLog(SysLoginLogQueryRequest request);
}
