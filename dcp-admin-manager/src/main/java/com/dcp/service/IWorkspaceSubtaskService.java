package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceSubtaskQueryDTO;
import com.dcp.entity.WorkspaceSubtask;

/**
 * WorkspaceSubtask服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceSubtaskService extends IService<WorkspaceSubtask> {

    /**
     * 分页查询子任务管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceSubtask> pageSubtask(WorkspaceSubtaskQueryDTO query);
}
