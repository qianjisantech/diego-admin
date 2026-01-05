package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.common.dto.WorkspaceIssueActivityQueryRequest;
import com.qianjisan.console.entity.IssueActivity;


/**
 * WorkspaceIssueActivity服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueActivityService extends IService<IssueActivity> {

    /**
     * 分页查询事项活动记录管理
     *
     * @param request 查询条件
     * @return 分页结果
     */
    Page<IssueActivity> pageIssueActivity(WorkspaceIssueActivityQueryRequest request);
}
