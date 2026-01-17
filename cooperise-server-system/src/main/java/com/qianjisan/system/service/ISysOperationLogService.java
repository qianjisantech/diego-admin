package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.entity.SysOperationLog;
import com.qianjisan.system.request.SysOperationLogRequest;
import com.qianjisan.system.vo.SysOperationLogVO;

/**
 * 操作日志服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysOperationLogService extends IService<SysOperationLog> {

    /**
     * 分页查询操作日志
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<SysOperationLogVO> pageQuery(SysOperationLogRequest request);

    /**
     * 保存操作日志
     *
     * @param sysOperationLog 操作日志
     */
    void saveLog(SysOperationLog sysOperationLog);
}
