package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.context.UserContextHolder;
import com.dcp.common.dto.SpaceQueryDTO;
import com.dcp.common.exception.BusinessException;
import com.dcp.common.exception.DataPermissionException;
import com.dcp.common.request.SpaceRequest;
import com.dcp.common.util.BeanConverter;
import com.dcp.common.vo.SpaceVO;
import com.dcp.entity.Space;
import com.dcp.entity.SpaceMember;
import com.dcp.rbac.entity.SysUser;
import com.dcp.mapper.SpaceMapper;
import com.dcp.service.ISpaceMemberService;
import com.dcp.service.ISpaceService;
import com.dcp.rbac.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 空间服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@RequiredArgsConstructor
public class SpaceServiceImpl extends ServiceImpl<SpaceMapper, Space> implements ISpaceService {

    private final ISysUserService userService;
    private final ISpaceMemberService spaceMemberService;

    @Override
    public SpaceVO getSpaceById(Long id) {
        // 数据权限检查：只能查询当前用户是成员的空间
        Long currentUserId = UserContextHolder.getUserId();
        if (currentUserId != null && !isAdminUser(currentUserId)) {
            if (!spaceMemberService.isSpaceMember(id, currentUserId)) {
                throw new DataPermissionException("您没有权限访问该空间的数据");
            }
        }
        
        Space space = getById(id);
        if (space == null) {
            return null;
        }
        
        SpaceVO spaceVO = BeanConverter.convert(space, SpaceVO::new);
        // 填充负责人名称
        if (spaceVO != null && spaceVO.getOwnerId() != null) {
            SysUser sysUser = userService.getById(spaceVO.getOwnerId());
            if (sysUser != null) {
                spaceVO.setOwnerName(sysUser.getUsername() != null ? "dcp" : sysUser.getUsername());
            }
        }
        return spaceVO;
    }

    @Override
    public List<SpaceVO> listAllSpaces() {
        // 数据权限过滤：只返回当前用户是成员的空间
        Long currentUserId = UserContextHolder.getUserId();
        List<Space> list;
        
        if (currentUserId != null && !isAdminUser(currentUserId)) {
            // 普通用户：只查询用户是成员的空间
            List<Long> userSpaceIds = spaceMemberService.getUserSpaceIds(currentUserId);
            if (userSpaceIds.isEmpty()) {
                return Collections.emptyList();
            }
            LambdaQueryWrapper<Space> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Space::getId, userSpaceIds);
            list = list(queryWrapper);
        } else {
            // admin 用户：可以查询所有空间
            list = list();
        }
        
        List<SpaceVO> voList = BeanConverter.convertList(list, SpaceVO::new);
        // 批量填充负责人名称
        fillOwnerNames(voList);
        return voList;
    }

    @Override
    public Page<SpaceVO> pageQuery(SpaceQueryDTO query) {
        Page<Space> page = new Page<>(query.getCurrent(), query.getSize());

        LambdaQueryWrapper<Space> queryWrapper = new LambdaQueryWrapper<>();

        // 数据权限过滤：只查询当前用户是成员的空间
        Long currentUserId = UserContextHolder.getUserId();
        if (currentUserId != null && !isAdminUser(currentUserId)) {
            List<Long> userSpaceIds = spaceMemberService.getUserSpaceIds(currentUserId);
            if (userSpaceIds.isEmpty()) {
                // 用户不是任何空间的成员，返回空结果
                Page<SpaceVO> emptyPage = new Page<>(query.getCurrent(), query.getSize(), 0);
                return emptyPage;
            }
            queryWrapper.in(Space::getId, userSpaceIds);
        }

        // 空间名称条件
        if (StringUtils.hasText(query.getName())) {
            queryWrapper.eq(Space::getName, query.getName());
        }

        // 空间代码条件
        if (StringUtils.hasText(query.getCode())) {
            queryWrapper.eq(Space::getKeyword, query.getCode());
        }

        // 负责人ID条件
        if (query.getOwnerId() != null) {
            queryWrapper.eq(Space::getOwnerId, query.getOwnerId());
        }

        // 关键词搜索
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(Space::getName, query.getKeyword())
                .or()
                .like(Space::getKeyword, query.getKeyword())
                .or()
                .like(Space::getDescription, query.getKeyword())
            );
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(Space::getCreateTime);

        Page<Space> resultPage = page(page, queryWrapper);
        Page<SpaceVO> voPage = BeanConverter.convertPage(resultPage, SpaceVO::new);
        // 批量填充负责人名称
        fillOwnerNames(voPage.getRecords());
        return voPage;
    }

    @Override
    public List<SpaceVO> listByOwner(Long ownerId) {
        LambdaQueryWrapper<Space> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Space::getOwnerId, ownerId);
        
        // 数据权限过滤：只返回当前用户是成员的空间
        Long currentUserId = UserContextHolder.getUserId();
        if (currentUserId != null && !isAdminUser(currentUserId)) {
            List<Long> userSpaceIds = spaceMemberService.getUserSpaceIds(currentUserId);
            if (userSpaceIds.isEmpty()) {
                return Collections.emptyList();
            }
            queryWrapper.in(Space::getId, userSpaceIds);
        }
        
        List<Space> list = list(queryWrapper);
        List<SpaceVO> voList = BeanConverter.convertList(list, SpaceVO::new);
        // 批量填充负责人名称
        fillOwnerNames(voList);
        return voList;
    }

    /**
     * 判断用户是否是管理员
     */
    private boolean isAdminUser(Long userId) {
        if (userId == null) {
            return false;
        }
        SysUser user = userService.getById(userId);
        if (user == null) {
            return false;
        }
        // 判断是否是 admin 用户
        return "admin".equalsIgnoreCase(user.getUsername()) || "admin".equalsIgnoreCase(user.getUserCode());
    }

    /**
     * 批量填充负责人名称
     */
    private void fillOwnerNames(List<SpaceVO> spaceVOList) {
        if (spaceVOList == null || spaceVOList.isEmpty()) {
            return;
        }

        // 收集所有的负责人ID
        Set<Long> ownerIds = spaceVOList.stream()
                .map(SpaceVO::getOwnerId)
                .filter(ownerId -> ownerId != null)
                .collect(Collectors.toSet());

        if (ownerIds.isEmpty()) {
            return;
        }

        // 批量查询用户信息
        List<SysUser> sysUsers = userService.listByIds(ownerIds);
        Map<Long, String> userNameMap = sysUsers.stream()
                .collect(Collectors.toMap(
                        SysUser::getId,
                        user -> user.getUsername()
                ));

        // 填充负责人名称
        spaceVOList.forEach(spaceVO -> {
            if (spaceVO.getOwnerId() != null) {
                spaceVO.setOwnerName(userNameMap.get(spaceVO.getOwnerId()));
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createSpaceWithMember(Space space, Long creatorId) {
        // 1. 创建空间
        boolean saved = save(space);
        if (!saved) {
            return false;
        }

        // 2. 将空间负责人（ownerId）作为管理员添加到成员表
        // 如果没有设置 ownerId，则使用 creatorId
        Long ownerId = space.getOwnerId() != null ? space.getOwnerId() : creatorId;

        SpaceMember member = new SpaceMember();
        member.setSpaceId(space.getId());
        member.setUserId(ownerId);
        member.setRole("admin");  // 负责人角色为管理员

        return spaceMemberService.save(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSpace(SpaceRequest request, Long currentUserId) {
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new BusinessException("空间名称不能为空");
        }

        // 转换为实体
        Space space = new Space();
        BeanUtils.copyProperties(request, space);

        // 创建空间并自动添加负责人为管理员
        boolean success = createSpaceWithMember(space, currentUserId);
        if (!success) {
            throw new BusinessException("创建空间失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpace(Long id, SpaceRequest request) {
        // 参数校验
        if (!StringUtils.hasText(request.getName())) {
            throw new BusinessException("空间名称不能为空");
        }

        // 检查空间是否存在
        Space existSpace = getById(id);
        if (existSpace == null) {
            throw new BusinessException("空间不存在");
        }

        // 转换为实体并更新
        Space space = new Space();
        BeanUtils.copyProperties(request, space);
        space.setId(id);

        boolean success = updateById(space);
        if (!success) {
            throw new BusinessException("更新空间失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpace(Long id) {
        // 检查空间是否存在
        Space space = getById(id);
        if (space == null) {
            throw new BusinessException("空间不存在");
        }

        // 删除空间（逻辑删除）
        boolean success = removeById(id);
        if (!success) {
            throw new BusinessException("删除空间失败");
        }
    }
}
