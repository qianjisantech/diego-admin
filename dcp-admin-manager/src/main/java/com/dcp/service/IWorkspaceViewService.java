package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.WorkspaceViewQueryDTO;
import com.dcp.common.vo.ViewTreeNodeVO;
import com.dcp.entity.WorkspaceView;

import java.util.List;

/**
 * WorkspaceView服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IWorkspaceViewService extends IService<WorkspaceView> {

    /**
     * 创建视图
     *
     * @param entity 视图实体
     * @param userId 用户ID
     * @return 创建的视图
     */
    WorkspaceView createView(WorkspaceView entity, Long userId);

    /**
     * 更新视图
     *
     * @param id     视图ID
     * @param entity 视图实体
     * @return 更新后的视图
     */
    WorkspaceView updateView(Long id, WorkspaceView entity);

    /**
     * 删除视图
     *
     * @param id 视图ID
     */
    void deleteView(Long id);

    /**
     * 根据ID查询视图
     *
     * @param id 视图ID
     * @return 视图实体
     */
    WorkspaceView getViewById(Long id);

    /**
     * 查询视图树形列表（包含文件夹和视图的树形结构）
     *
     * @param userId 用户ID
     * @return 树形结构列表
     */
    List<ViewTreeNodeVO> getViewTreeList(Long userId);

    /**
     * 分页查询视图
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<WorkspaceView> pageView(WorkspaceViewQueryDTO query);
}
