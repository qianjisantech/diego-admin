package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.WorkspaceViewFolderQueryDTO;
import com.dcp.entity.WorkspaceView;
import com.dcp.entity.WorkspaceViewFolder;
import com.dcp.mapper.WorkspaceViewFolderMapper;
import com.dcp.service.IWorkspaceViewFolderService;
import com.dcp.service.IWorkspaceViewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * WorkspaceViewFolder服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class WorkspaceViewFolderServiceImpl extends ServiceImpl<WorkspaceViewFolderMapper, WorkspaceViewFolder> implements IWorkspaceViewFolderService {

    private final IWorkspaceViewService workspaceViewService;

    public WorkspaceViewFolderServiceImpl(@Lazy IWorkspaceViewService workspaceViewService) {
        this.workspaceViewService = workspaceViewService;
    }

    @Override
    public WorkspaceViewFolder createFolder(WorkspaceViewFolder entity, Long userId) {
        log.info("[创建文件夹] 接收到请求: {}", entity);
        // 参数校验
        if (!StringUtils.hasText(entity.getName())) {
            throw new RuntimeException("文件夹名称不能为空");
        }
        // 设置默认值
        if (entity.getOwnerId() == null) {
            if (userId != null) {
                entity.setOwnerId(userId);
                log.info("[创建文件夹] 设置创建者ID: {}", userId);
            }
        }
        if (entity.getSortOrder() == null) {
            entity.setSortOrder(0);
        }
        // 保存文件夹
        this.save(entity);
        log.info("[创建文件夹] 成功，文件夹ID: {}", entity.getId());
        return entity;
    }

    @Override
    public WorkspaceViewFolder updateFolder(Long id, WorkspaceViewFolder entity) {
        log.info("[更新文件夹] ID: {}, 数据: {}", id, entity);
        // 参数校验
        if (!StringUtils.hasText(entity.getName())) {
            throw new RuntimeException("文件夹名称不能为空");
        }
        // 检查文件夹是否存在
        WorkspaceViewFolder existFolder = this.getById(id);
        if (existFolder == null) {
            throw new RuntimeException("文件夹不存在");
        }
        entity.setId(id);
        this.updateById(entity);
        log.info("[更新文件夹] 成功");
        return entity;
    }

    @Override
    public void deleteFolder(Long id) {
        log.info("[删除文件夹] ID: {}", id);
        // 检查文件夹是否存在
        WorkspaceViewFolder folder = this.getById(id);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在");
        }
        // 检查文件夹下是否有视图
        LambdaQueryWrapper<WorkspaceView> viewWrapper = new LambdaQueryWrapper<>();
        viewWrapper.eq(WorkspaceView::getFolderId, id);
        long viewCount = workspaceViewService.count(viewWrapper);
        if (viewCount > 0) {
            throw new RuntimeException("文件夹下还有 " + viewCount + " 个视图，请先删除或移动这些视图");
        }
        // 检查是否有子文件夹
        LambdaQueryWrapper<WorkspaceViewFolder> folderWrapper = new LambdaQueryWrapper<>();
        folderWrapper.eq(WorkspaceViewFolder::getParentId, id);
        long subFolderCount = this.count(folderWrapper);
        if (subFolderCount > 0) {
            throw new RuntimeException("文件夹下还有 " + subFolderCount + " 个子文件夹，请先删除这些子文件夹");
        }
        // 删除文件夹
        this.removeById(id);
        log.info("[删除文件夹] 成功");
    }

    @Override
    public WorkspaceViewFolder getFolderById(Long id) {
        log.info("[查询文件夹] ID: {}", id);
        WorkspaceViewFolder entity = this.getById(id);
        if (entity == null) {
            throw new RuntimeException("文件夹不存在");
        }
        return entity;
    }

    @Override
    public Page<WorkspaceViewFolder> pageFolder(WorkspaceViewFolderQueryDTO query) {
        log.info("[分页查询文件夹] 查询参数: {}", query);
        Page<WorkspaceViewFolder> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<WorkspaceViewFolder> queryWrapper = new LambdaQueryWrapper<>();
        if (query.getParentId() != null) {
            queryWrapper.eq(WorkspaceViewFolder::getParentId, query.getParentId());
        }
        if (query.getOwnerId() != null) {
            queryWrapper.eq(WorkspaceViewFolder::getOwnerId, query.getOwnerId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.like(WorkspaceViewFolder::getName, query.getKeyword());
        }
        queryWrapper.orderByDesc(WorkspaceViewFolder::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询文件夹] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
