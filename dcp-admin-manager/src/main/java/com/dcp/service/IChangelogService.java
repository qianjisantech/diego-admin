package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.ChangelogQueryDTO;
import com.dcp.entity.SysChangelog;

/**
 * Changelog服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IChangelogService extends IService<SysChangelog> {

    /**
     * 分页查询发布日志管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SysChangelog> pageChangelog(ChangelogQueryDTO query);
}
