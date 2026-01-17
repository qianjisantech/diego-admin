package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qianjisan.console.entity.View;
import com.qianjisan.console.entity.ViewFolder;
import com.qianjisan.console.mapper.ViewMapper;
import com.qianjisan.console.request.ViewQueryRequest;
import com.qianjisan.console.request.ViewRequest;
import com.qianjisan.console.service.IViewFolderService;
import com.qianjisan.console.service.IViewService;
import com.qianjisan.console.vo.ViewPageVO;
import com.qianjisan.console.vo.ViewTreeNodeVO;
import com.qianjisan.console.vo.ViewVO;
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
public class ViewServiceImpl extends ServiceImpl<ViewMapper, View> implements IViewService {

    private final IViewFolderService workspaceViewFolderService;

    @Override
    public ViewVO createView(ViewRequest request, Long userId) {
        log.info("[创建视图] 接收到请求: {}", request);
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new RuntimeException("视图名称不能为空");
        }
        if (!StringUtils.hasText(request.getType())) {
            throw new RuntimeException("视图类型不能为空");
        }
        // 将 Request 转换为 Entity
        View entity = new View();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setType(request.getType());
        entity.setSpaceId(request.getSpaceId());
        entity.setIsPublic(request.getIsPublic());
        entity.setFolderId(request.getFolderId());
        entity.setConfig(request.getConfig());
        entity.setSortOrder(request.getSortOrder());
        // 设置默认值
        if (userId != null) {
            entity.setOwnerId(userId);
            log.info("[创建视图] 设置创建者ID: {}", userId);
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
        return convertToVO(entity);
    }

    @Override
    public ViewVO updateView(Long id, ViewRequest request) {
        log.info("[更新视图] ID: {}, 数据: {}", id, request);
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new RuntimeException("视图名称不能为空");
        }
        if (!StringUtils.hasText(request.getType())) {
            throw new RuntimeException("视图类型不能为空");
        }
        // 检查视图是否存在
        View existView = this.getById(id);
        if (existView == null) {
            throw new RuntimeException("视图不存在");
        }
        // 将 Request 转换为 Entity
        View entity = new View();
        entity.setId(id);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setType(request.getType());
        entity.setSpaceId(request.getSpaceId());
        entity.setIsPublic(request.getIsPublic());
        entity.setFolderId(request.getFolderId());
        entity.setConfig(request.getConfig());
        entity.setSortOrder(request.getSortOrder());
        this.updateById(entity);
        log.info("[更新视图] 成功");
        return convertToVO(entity);
    }

    @Override
    public void deleteView(Long id) {
        log.info("[删除视图] ID: {}", id);
        // 检查视图是否存在
        View view = this.getById(id);
        if (view == null) {
            throw new RuntimeException("视图不存在");
        }
        this.removeById(id);
        log.info("[删除视图] 成功");
    }

    @Override
    public ViewVO getViewById(Long id) {
        log.info("[查询视图] ID: {}", id);
        View entity = this.getById(id);
        if (entity == null) {
            throw new RuntimeException("视图不存在");
        }
        return convertToVO(entity);
    }

    @Override
    public List<ViewTreeNodeVO> getViewTreeList(Long userId) {
        log.info("[查询视图树形列表] 开始，当前用户ID: {}", userId);
        // 1. 查询所有文件夹（按排序顺序）
        LambdaQueryWrapper<ViewFolder> folderWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            folderWrapper.eq(ViewFolder::getOwnerId, userId);
        }
        folderWrapper.orderByAsc(ViewFolder::getSortOrder)
                    .orderByDesc(ViewFolder::getCreateTime);
        List<ViewFolder> folders = workspaceViewFolderService.list(folderWrapper);
        log.info("[查询视图树形列表] 查询到 {} 个文件夹", folders.size());
        // 2. 查询所有视图（按排序顺序）
        LambdaQueryWrapper<View> viewWrapper = new LambdaQueryWrapper<>();
        if (userId != null) {
            viewWrapper.and(wrapper -> wrapper
                .eq(View::getOwnerId, userId)
                .or()
                .eq(View::getIsPublic, 1)
            );
        }
        viewWrapper.orderByAsc(View::getSortOrder)
                   .orderByDesc(View::getCreateTime);
        List<View> views = this.list(viewWrapper);
        log.info("[查询视图树形列表] 查询到 {} 个视图", views.size());
        // 3. 构建树形结构
        List<ViewTreeNodeVO> treeList = buildViewTree(folders, views);
        log.info("[查询视图树形列表] 成功，共 {} 个根节点", treeList.size());
        return treeList;
    }

    @Override
    public Page<ViewPageVO> pageView(ViewQueryRequest query) {
        log.info("[分页查询视图] 查询参数: {}", query);
        Page<View> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<View> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getSpaceId() != null) {
            queryWrapper.eq(View::getSpaceId, query.getSpaceId());
        }
        if (query.getFolderId() != null) {
            queryWrapper.eq(View::getFolderId, query.getFolderId());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(View::getType, query.getType());
        }
        if (query.getOwnerId() != null) {
            queryWrapper.eq(View::getOwnerId, query.getOwnerId());
        }
        if (query.getIsPublic() != null) {
            queryWrapper.eq(View::getIsPublic, query.getIsPublic());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(View::getName, query.getKeyword())
                .or()
                .like(View::getDescription, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(View::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询视图] 成功，共 {} 条", page.getTotal());
        // 转换为 ViewPageVO
        Page<ViewPageVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<ViewPageVO> voList = page.getRecords().stream()
            .map(this::convertToPageVO)
            .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<ViewVO> listViews() {
        log.info("[查询视图列表]");
        List<View> views = this.list();
        return views.stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
    }

    /**
     * 构建视图树形结构
     */
    private List<ViewTreeNodeVO> buildViewTree(List<ViewFolder> folders, List<View> views) {
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
    private ViewTreeNodeVO convertFolderToNode(ViewFolder folder) {
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
    private ViewTreeNodeVO convertViewToNode(View view) {
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

    /**
     * 将 View 实体转换为 ViewVO
     */
    private ViewVO convertToVO(View view) {
        if (view == null) {
            return null;
        }
        ViewVO vo = new ViewVO();
        vo.setId(view.getId());
        vo.setName(view.getName());
        vo.setDescription(view.getDescription());
        vo.setType(view.getType());
        vo.setSpaceId(view.getSpaceId());
        vo.setOwnerId(view.getOwnerId());
        vo.setIsPublic(view.getIsPublic());
        vo.setFolderId(view.getFolderId());
        vo.setConfig(view.getConfig());
        vo.setSortOrder(view.getSortOrder());
        vo.setCreateTime(view.getCreateTime());
        vo.setUpdateTime(view.getUpdateTime());
        return vo;
    }

    /**
     * 将 View 实体转换为 ViewPageVO
     */
    private ViewPageVO convertToPageVO(View view) {
        if (view == null) {
            return null;
        }
        ViewPageVO vo = new ViewPageVO();
        vo.setId(view.getId());
        vo.setName(view.getName());
        vo.setDescription(view.getDescription());
        vo.setType(view.getType());
        vo.setSpaceId(view.getSpaceId());
        vo.setOwnerId(view.getOwnerId());
        vo.setIsPublic(view.getIsPublic());
        vo.setFolderId(view.getFolderId());
        vo.setSortOrder(view.getSortOrder());
        vo.setCreateTime(view.getCreateTime());
        vo.setUpdateTime(view.getUpdateTime());
        return vo;
    }
}
