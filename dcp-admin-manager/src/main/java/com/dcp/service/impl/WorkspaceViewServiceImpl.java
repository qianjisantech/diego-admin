package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceViewQueryDTO;
import com.dcp.common.vo.ViewTreeNodeVO;
import com.dcp.entity.WorkspaceView;
import com.dcp.entity.WorkspaceViewFolder;
import com.dcp.mapper.WorkspaceViewMapper;
import com.dcp.service.IWorkspaceViewFolderService;
import com.dcp.service.IWorkspaceViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * WorkspaceView服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceViewServiceImpl extends ServiceImpl<WorkspaceViewMapper, WorkspaceView> implements IWorkspaceViewService {

    private final IWorkspaceViewFolderService workspaceViewFolderService;

    @Override
    public WorkspaceView createView(WorkspaceView entity, Long userId) {
        log.info("[创建视图] 接收到请求: {}", entity);
        // 参数校验
        if (!StringUtils.hasText(entity.getName())) {
            throw new RuntimeException("视图名称不能为空");
        }
        if (!StringUtils.hasText(entity.getType())) {
            throw new RuntimeException("视图类型不能为空");
        }
        // 设置默认值
        if (entity.getOwnerId() == null) {
            if (userId != null) {
                entity.setOwnerId(userId);
                log.info("[创建视图] 设置创建者ID: {}", userId);
            }
        }
        if (entity.getIsPublic() == null) {
            entity.setIsPublic(0); // 默认私有
        }
        if (entity.getSortOrder() == null) {
            entity.setSortOrder(0);
        }
        // config 字段如果为 null，设置为空 JSON 字符串
        if (entity.getConfig() == null) {
            entity.setConfig("{}");
        }
        // 保存视图
        this.save(entity);
        log.info("[创建视图] 成功，视图ID: {}", entity.getId());
        return entity;
    }

    @Override
    public WorkspaceView updateView(Long id, WorkspaceView entity) {
        log.info("[更新视图] ID: {}, 数据: {}", id, entity);
        // 参数校验
        if (!StringUtils.hasText(entity.getName())) {
            throw new RuntimeException("视图名称不能为空");
        }
        if (!StringUtils.hasText(entity.getType())) {
            throw new RuntimeException("视图类型不能为空");
        }
        // 检查视图是否存在
        WorkspaceView existView = this.getById(id);
        if (existView == null) {
            throw new RuntimeException("视图不存在");
        }
        entity.setId(id);
        this.updateById(entity);
        log.info("[更新视图] 成功");
        return entity;
    }

    @Override
    public void deleteView(Long id) {
        log.info("[删除视图] ID: {}", id);
        // 检查视图是否存在
        WorkspaceView view = this.getById(id);
        if (view == null) {
            throw new RuntimeException("视图不存在");
        }
        this.removeById(id);
        log.info("[删除视图] 成功");
    }

    @Override
    public WorkspaceView getViewById(Long id) {
        log.info("[查询视图] ID: {}", id);
        WorkspaceView entity = this.getById(id);
        if (entity == null) {
            throw new RuntimeException("视图不存在");
        }
        return entity;
    }

    @Override
    public List<ViewTreeNodeVO> getViewTreeList(Long userId) {
        log.info("[查询视图树形列表] 开始，当前用户ID: {}", userId);
        // 1. 查询所有文件夹（按排序顺序）
        LambdaQueryWrapper<WorkspaceViewFolder> folderWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            folderWrapper.eq(WorkspaceViewFolder::getOwnerId, userId);
        }
        folderWrapper.orderByAsc(WorkspaceViewFolder::getSortOrder)
                    .orderByDesc(WorkspaceViewFolder::getCreateTime);
        List<WorkspaceViewFolder> folders = workspaceViewFolderService.list(folderWrapper);
        log.info("[查询视图树形列表] 查询到 {} 个文件夹", folders.size());
        // 2. 查询所有视图（按排序顺序）
        LambdaQueryWrapper<WorkspaceView> viewWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            viewWrapper.and(wrapper -> wrapper
                .eq(WorkspaceView::getOwnerId, userId)
                .or()
                .eq(WorkspaceView::getIsPublic, 1)
            );
        }
        viewWrapper.orderByAsc(WorkspaceView::getSortOrder)
                   .orderByDesc(WorkspaceView::getCreateTime);
        List<WorkspaceView> views = this.list(viewWrapper);
        log.info("[查询视图树形列表] 查询到 {} 个视图", views.size());
        // 3. 构建树形结构
        List<ViewTreeNodeVO> treeList = buildViewTree(folders, views);
        log.info("[查询视图树形列表] 成功，共 {} 个根节点", treeList.size());
        return treeList;
    }

    @Override
    public Page<WorkspaceView> pageView(WorkspaceViewQueryDTO query) {
        log.info("[分页查询视图] 查询参数: {}", query);
        Page<WorkspaceView> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceView> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getSpaceId() != null) {
            queryWrapper.eq(WorkspaceView::getSpaceId, query.getSpaceId());
        }
        if (query.getFolderId() != null) {
            queryWrapper.eq(WorkspaceView::getFolderId, query.getFolderId());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(WorkspaceView::getType, query.getType());
        }
        if (query.getOwnerId() != null) {
            queryWrapper.eq(WorkspaceView::getOwnerId, query.getOwnerId());
        }
        if (query.getIsPublic() != null) {
            queryWrapper.eq(WorkspaceView::getIsPublic, query.getIsPublic());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(WorkspaceView::getName, query.getKeyword())
                .or()
                .like(WorkspaceView::getDescription, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(WorkspaceView::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询视图] 成功，共 {} 条", page.getTotal());
        return page;
    }

    /**
     * 构建视图树形结构
     */
    private List<ViewTreeNodeVO> buildViewTree(List<WorkspaceViewFolder> folders, List<WorkspaceView> views) {
        List<ViewTreeNodeVO> result = new ArrayList<>();
        // 1. 将所有文件夹转换为树节点
        List<ViewTreeNodeVO> folderNodes = folders.stream()
            .map(this::convertFolderToNode)
            .collect(Collectors.toList());
        // 2. 将所有视图转换为树节点
        List<ViewTreeNodeVO> viewNodes = views.stream()
            .map(this::convertViewToNode)
            .collect(Collectors.toList());
        // 3. 按 folderId 分组视图
        Map<Long, List<ViewTreeNodeVO>> viewsByFolder = viewNodes.stream()
            .filter(node -> node.getFolderId() != null)
            .collect(Collectors.groupingBy(ViewTreeNodeVO::getFolderId));
        // 4. 构建文件夹树结构并添加视图
        for (ViewTreeNodeVO folderNode : folderNodes) {
            // 4.1 如果是根文件夹（parentId 为 null），添加到结果
            if (folderNode.getParentId() == null) {
                result.add(folderNode);
            }
            // 4.2 将属于该文件夹的视图添加为子节点
            List<ViewTreeNodeVO> folderViews = viewsByFolder.get(folderNode.getId());
            if (folderViews != null && !folderViews.isEmpty()) {
                if (folderNode.getChildren() == null) {
                    folderNode.setChildren(new ArrayList<>());
                }
                folderNode.getChildren().addAll(folderViews);
            }
        }
        // 5. 处理子文件夹（如果支持嵌套文件夹）
        Map<Long, List<ViewTreeNodeVO>> foldersByParent = folderNodes.stream()
            .filter(node -> node.getParentId() != null)
            .collect(Collectors.groupingBy(ViewTreeNodeVO::getParentId));
        for (ViewTreeNodeVO folderNode : folderNodes) {
            List<ViewTreeNodeVO> childFolders = foldersByParent.get(folderNode.getId());
            if (childFolders != null && !childFolders.isEmpty()) {
                if (folderNode.getChildren() == null) {
                    folderNode.setChildren(new ArrayList<>());
                }
                folderNode.getChildren().addAll(0, childFolders);
            }
        }
        // 6. 添加根级视图（folderId 为 null 的视图）
        List<ViewTreeNodeVO> rootViews = viewNodes.stream()
            .filter(node -> node.getFolderId() == null)
            .collect(Collectors.toList());
        result.addAll(rootViews);
        return result;
    }

    /**
     * 将文件夹实体转换为树节点
     */
    private ViewTreeNodeVO convertFolderToNode(WorkspaceViewFolder folder) {
        ViewTreeNodeVO node = new ViewTreeNodeVO();
        node.setId(folder.getId());
        node.setName(folder.getName());
        node.setType("folder");
        node.setOwnerId(folder.getOwnerId());
        node.setParentId(folder.getParentId());
        node.setSortOrder(folder.getSortOrder());
        node.setCreateTime(folder.getCreateTime());
        node.setUpdateTime(folder.getUpdateTime());
        node.setChildren(new ArrayList<>());
        return node;
    }

    /**
     * 将视图实体转换为树节点
     */
    private ViewTreeNodeVO convertViewToNode(WorkspaceView view) {
        ViewTreeNodeVO node = new ViewTreeNodeVO();
        node.setId(view.getId());
        node.setName(view.getName());
        node.setType("view");
        node.setViewType(view.getType());
        node.setDescription(view.getDescription());
        node.setSpaceId(view.getSpaceId());
        node.setOwnerId(view.getOwnerId());
        node.setIsPublic(view.getIsPublic());
        node.setFolderId(view.getFolderId());
        node.setConfig(view.getConfig());
        node.setSortOrder(view.getSortOrder());
        node.setCreateTime(view.getCreateTime());
        node.setUpdateTime(view.getUpdateTime());
        return node;
    }
}
