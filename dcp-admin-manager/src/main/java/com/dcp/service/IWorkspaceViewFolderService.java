package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceViewFolderQueryDTO;
import com.dcp.entity.WorkspaceViewFolder;

/**
 * WorkspaceViewFolder服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceViewFolderService extends IService<WorkspaceViewFolder> {

    /**
     * 创建视图文件夹
     *
     * @param entity 文件夹实体
     * @param userId 用户ID
     * @return 创建的文件夹
     */
    WorkspaceViewFolder createFolder(WorkspaceViewFolder entity, Long userId);

    /**
     * 更新视图文件夹
     *
     * @param id     文件夹ID
     * @param entity 文件夹实体
     * @return 更新后的文件夹
     */
    WorkspaceViewFolder updateFolder(Long id, WorkspaceViewFolder entity);

    /**
     * 删除视图文件夹
     *
     * @param id 文件夹ID
     */
    void deleteFolder(Long id);

    /**
     * 根据ID查询视图文件夹
     *
     * @param id 文件夹ID
     * @return 文件夹实体
     */
    WorkspaceViewFolder getFolderById(Long id);

    /**
     * 分页查询视图文件夹
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceViewFolder> pageFolder(WorkspaceViewFolderQueryDTO query);
}
