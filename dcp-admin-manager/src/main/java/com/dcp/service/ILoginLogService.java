package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.LoginLogQueryDTO;
import com.dcp.entity.SysLoginLog;

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
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SysLoginLog> pageLoginLog(LoginLogQueryDTO query);
}
