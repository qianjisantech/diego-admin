package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.entity.View;
import com.qianjisan.console.request.ViewQueryRequest;
import com.qianjisan.console.request.ViewRequest;
import com.qianjisan.console.vo.ViewPageVO;
import com.qianjisan.console.vo.ViewTreeNodeVO;
import com.qianjisan.console.vo.ViewVO;


import java.util.List;

/**
 * WorkspaceView服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IViewService extends IService<View> {

    /**
     * 创建视图
     *
     * @param request 视图请求
     * @param userId 用户ID
     * @return 创建的视图VO
     */
  ViewVO createView(ViewRequest request, Long userId);

    /**
     * 更新视图
     *
     * @param id     视图ID
     * @param request 视图请求
     * @return 更新后的视图VO
     */
    ViewVO updateView(Long id, ViewRequest request);

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
     * @return 视图VO
     */
    ViewVO getViewById(Long id);

    /**
     * 查询视图列表
     *
     * @return 视图VO列表
     */
    List<ViewVO> listViews();

    /**
     * 查询视图树形列表（包含文件夹和视图的树形结构�?
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
    Page<ViewPageVO> pageView(ViewQueryRequest query);
}
