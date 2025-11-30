package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceIssueActivityQueryDTO;
import com.dcp.entity.WorkspaceIssueActivity;

/**
 * WorkspaceIssueActivity服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceIssueActivityService extends IService<WorkspaceIssueActivity> {

    /**
     * 分页查询事项活动记录管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceIssueActivity> pageIssueActivity(WorkspaceIssueActivityQueryDTO query);
}
