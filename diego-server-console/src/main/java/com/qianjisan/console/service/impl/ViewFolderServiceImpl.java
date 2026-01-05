package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.qianjisan.console.entity.View;
import com.qianjisan.console.entity.ViewFolder;
import com.qianjisan.console.mapper.ViewFolderMapper;
import com.qianjisan.console.request.ViewFolderQueryRequest;
import com.qianjisan.console.request.ViewFolderRequest;
import com.qianjisan.console.service.IViewFolderService;
import com.qianjisan.console.service.IViewService;
import com.qianjisan.console.vo.ViewFolderPageVO;
import com.qianjisan.console.vo.ViewFolderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * WorkspaceViewFolder服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class ViewFolderServiceImpl extends ServiceImpl<ViewFolderMapper, ViewFolder> implements IViewFolderService {

    private final IViewService workspaceViewService;

    public ViewFolderServiceImpl(@Lazy IViewService workspaceViewService) {
        this.workspaceViewService = workspaceViewService;
    }

    @Override
    public void createFolder(ViewFolderRequest request, Long userId) {
        log.info("[创建文件夹] 接收到请求: {}", request);
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new RuntimeException("文件夹名称不能为空");
        }
        // 将 Request 转换为 Entity
        ViewFolder entity = new ViewFolder();
        entity.setName(request.getName());
        entity.setParentId(request.getParentId());
        entity.setSortOrder(request.getSortOrder());
        // 设置默认值
        if (userId != null) {
            entity.setOwnerId(userId);
            log.info("[创建文件夹] 设置创建者ID: {}", userId);
        }
        if (entity.getSortOrder() == null) {
            entity.setSortOrder(0);
        }
        // 保存文件夹
        this.save(entity);
        log.info("[创建文件夹] 成功，文件夹ID: {}", entity.getId());
    }

    @Override
    public void updateFolder(Long id, ViewFolderRequest request) {
        log.info("[更新文件夹] ID: {}, 数据: {}", id, request);
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new RuntimeException("文件夹名称不能为空");
        }
        // 检查文件夹是否存在
        ViewFolder existFolder = this.getById(id);
        if (existFolder == null) {
            throw new RuntimeException("文件夹不存在");
        }
        // 将 Request 转换为 Entity
        ViewFolder entity = new ViewFolder();
        entity.setId(id);
        entity.setName(request.getName());
        entity.setParentId(request.getParentId());
        entity.setSortOrder(request.getSortOrder());
        this.updateById(entity);
        log.info("[更新文件夹] 成功");
    }

    @Override
    public void deleteFolder(Long id) {
        log.info("[删除文件夹] ID: {}", id);
        // 检查文件夹是否存在
        ViewFolder folder = this.getById(id);
        if (folder == null) {
            throw new RuntimeException("文件夹不存在");
        }
        // 检查文件夹下是否有视图
        LambdaQueryWrapper<View> viewWrapper = new LambdaQueryWrapper<>();
        viewWrapper.eq(View::getFolderId, id);
        long viewCount = workspaceViewService.count(viewWrapper);
        if (viewCount > 0) {
            throw new RuntimeException("文件夹下还有 " + viewCount + " 个视图，请先删除或移动这些视图");
        }
        // 检查是否有子文件夹
        LambdaQueryWrapper<ViewFolder> folderWrapper = new LambdaQueryWrapper<>();
        folderWrapper.eq(ViewFolder::getParentId, id);
        long subFolderCount = this.count(folderWrapper);
        if (subFolderCount > 0) {
            throw new RuntimeException("文件夹下还有 " + subFolderCount + " 个子文件夹，请先删除这些子文件夹");
        }
        // 删除文件夹
        this.removeById(id);
        log.info("[删除文件夹] 成功");
    }

    @Override
    public ViewFolderVO getFolderById(Long id) {
        log.info("[查询文件夹] ID: {}", id);
        ViewFolder entity = this.getById(id);
        if (entity == null) {
            throw new RuntimeException("文件夹不存在");
        }
        return convertToVO(entity);
    }

    @Override
    public Page<ViewFolderPageVO> pageFolder(ViewFolderQueryRequest request) {
        log.info("[分页查询文件夹] 查询参数: {}", request);
        Page<ViewFolder> page = new Page<>(request.getCurrent(), request.getSize());
        LambdaQueryWrapper<ViewFolder> queryWrapper = new LambdaQueryWrapper<>();
        if (request.getParentId() != null) {
            queryWrapper.eq(ViewFolder::getParentId, request.getParentId());
        }
        if (request.getOwnerId() != null) {
            queryWrapper.eq(ViewFolder::getOwnerId, request.getOwnerId());
        }
        if (StringUtils.hasText(request.getKeyword())) {
            queryWrapper.like(ViewFolder::getName, request.getKeyword());
        }
        queryWrapper.orderByDesc(ViewFolder::getCreateTime);
        page = this.page(page, queryWrapper);
        log.info("[分页查询文件夹] 成功，共 {} 条", page.getTotal());
        // 转换为 ViewFolderPageVO
        Page<ViewFolderPageVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<ViewFolderPageVO> voList = page.getRecords().stream()
            .map(this::convertToPageVO)
            .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    public List<ViewFolderVO> listFolders() {
        log.info("[查询文件夹列表]");
        List<ViewFolder> folders = this.list();
        return folders.stream()
            .map(this::convertToVO)
            .collect(Collectors.toList());
    }

    /**
     * 将 ViewFolder 实体转换为 ViewFolderVO
     */
    private ViewFolderVO convertToVO(ViewFolder folder) {
        if (folder == null) {
            return null;
        }
        ViewFolderVO vo = new ViewFolderVO();
        vo.setId(folder.getId());
        vo.setName(folder.getName());
        vo.setOwnerId(folder.getOwnerId());
        vo.setParentId(folder.getParentId());
        vo.setSortOrder(folder.getSortOrder());
        vo.setCreateTime(folder.getCreateTime());
        vo.setUpdateTime(folder.getUpdateTime());
        return vo;
    }

    /**
     * 将 ViewFolder 实体转换为 ViewFolderPageVO
     */
    private ViewFolderPageVO convertToPageVO(ViewFolder folder) {
        if (folder == null) {
            return null;
        }
        ViewFolderPageVO vo = new ViewFolderPageVO();
        vo.setId(folder.getId());
        vo.setName(folder.getName());
        vo.setOwnerId(folder.getOwnerId());
        vo.setParentId(folder.getParentId());
        vo.setSortOrder(folder.getSortOrder());
        vo.setCreateTime(folder.getCreateTime());
        vo.setUpdateTime(folder.getUpdateTime());
        return vo;
    }
}
