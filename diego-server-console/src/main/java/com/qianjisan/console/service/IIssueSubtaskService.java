package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.WorkspaceSubtaskQueryRequest;
import com.qianjisan.console.entity.IssueSubtask;


/**
 * WorkspaceSubtask服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueSubtaskService extends IService<IssueSubtask> {

    /**
     * 分页查询子任务管理
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<IssueSubtask> pageSubtask(WorkspaceSubtaskQueryRequest request);
}
