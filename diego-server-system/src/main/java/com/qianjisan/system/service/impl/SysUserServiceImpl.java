package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.PageVO;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.request.SysUserQueryRequest;
import com.qianjisan.system.request.SysUserRequest;

import com.qianjisan.system.vo.SysRoleVO;
import com.qianjisan.system.vo.SysUserVO;
import com.qianjisan.system.entity.SysRole;
import com.qianjisan.system.entity.SysUserRole;
import com.qianjisan.system.mapper.SysUserMapper;
import com.qianjisan.system.mapper.SysRoleMapper;
import com.qianjisan.system.mapper.SysUserRoleMapper;
import com.qianjisan.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户Service实现
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;

    @Override
    public PageVO<SysUserVO> page(SysUserQueryRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();

        if (StringUtils.hasText(request.getName())) {
            wrapper.like(SysUser::getName, request.getName());
        }

        if (request.getStatus() != null) {
            wrapper.eq(SysUser::getStatus, request.getStatus());
        }

        wrapper.orderByDesc(SysUser::getCreateTime);

        // 执行分页查询
        Page<SysUser> page = new Page<>(request.getCurrent(), request.getSize());
        IPage<SysUser> userPage = this.page(page, wrapper);

        // 转换为VO
        List<SysUserVO> voList = new ArrayList<>();
        for (SysUser sysUser : userPage.getRecords()) {
            SysUserVO vo = convertToVO(sysUser);

            // 查询用户角色
            LambdaQueryWrapper<SysUserRole> urWrapper = new LambdaQueryWrapper<>();
            urWrapper.eq(SysUserRole::getUserId, sysUser.getId());
            List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(urWrapper);

            if (!sysUserRoles.isEmpty()) {
                List<Long> roleIds = sysUserRoles.stream()
                        .map(SysUserRole::getRoleId)
                        .collect(Collectors.toList());

                List<SysRole> sysRoles = sysRoleMapper.selectBatchIds(roleIds);
                List<SysRoleVO> sysRoleVOS = sysRoles.stream()
                        .map(this::convertRoleToVO)
                        .collect(Collectors.toList());
                vo.setRoles(sysRoleVOS);
            }

            voList.add(vo);
        }

        PageVO<SysUserVO> pageVO = new PageVO<>();
        pageVO.setRecords(voList);
        pageVO.setTotal(userPage.getTotal());
        pageVO.setCurrent(userPage.getCurrent());
        pageVO.setSize(userPage.getSize());
        pageVO.setPages(userPage.getPages());
        return pageVO;
    }

    @Override
    public SysUserVO getUserDetail(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        SysUserVO vo = convertToVO(sysUser);

        // 查询用户角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectList(wrapper);

        if (!sysUserRoles.isEmpty()) {
            List<Long> roleIds = sysUserRoles.stream()
                    .map(SysUserRole::getRoleId)
                    .collect(Collectors.toList());

            List<SysRole> sysRoles = sysRoleMapper.selectBatchIds(roleIds);
            List<SysRoleVO> sysRoleVOS = sysRoles.stream()
                    .map(this::convertRoleToVO)
                    .collect(Collectors.toList());
            vo.setRoles(sysRoleVOS);
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 删除用户现有角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(wrapper);

        // 分配新角色
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Long roleId : roleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(userId);
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 重置密码为 123456
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        sysUser.setPassword(encoder.encode("123456"));
        this.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, Integer status) {
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        sysUser.setStatus(status);
        this.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(Long userId, SysUserRequest request) {
        // 检查用户是否存在
        SysUser existingSysUser = this.getById(userId);
        if (existingSysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查用户名是否被其他用户使用
        if (!existingSysUser.getName().equals(request.getUserCode())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUserCode, request.getUserCode());
            wrapper.ne(SysUser::getId, userId);
            long count = this.count(wrapper);
            if (count > 0) {
                throw new RuntimeException("用户名已存在");
            }
        }

        // 检查用户编码是否被其他用户使用
        if (!existingSysUser.getUserCode().equals(request.getUserCode())) {
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getUserCode, request.getUserCode());
            wrapper.ne(SysUser::getId, userId);
            long count = this.count(wrapper);
            if (count > 0) {
                throw new RuntimeException("用户编码已存在");
            }
        }

        // 更新用户信息
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        BeanUtils.copyProperties(request, sysUser);

        // 如果没有提供状态，保留原状态
        if (request.getStatus() == null) {
            sysUser.setStatus(null);
        }

        this.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // 检查用户是否存在
        SysUser sysUser = this.getById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 删除用户（逻辑删除）
        this.removeById(userId);

        // 删除用户角色关联
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        sysUserRoleMapper.delete(wrapper);
    }

    @Override
    public void updateLastLoginTime(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLastLoginTime(LocalDateTime.now());
        this.updateById(sysUser);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getEmail, email);
        return this.getOne(queryWrapper);
    }

    @Override
    public SysUser getUserByUserCode(String userCode) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserCode, userCode);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<SysUser> getSimpleUserList() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        // 只查询需要的字段：id, username, userCode, email
        queryWrapper.select(SysUser::getId, SysUser::getName, SysUser::getUserCode, SysUser::getEmail);
        // MyBatis Plus 会自动过滤逻辑删除的数据（因为 BaseEntity 中有 @TableLogic）
        queryWrapper.orderByAsc(SysUser::getLastLoginTime);
        return this.list(queryWrapper);
    }

    /**
     * 转换为VO
     */
    private SysUserVO convertToVO(SysUser sysUser) {
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(sysUser, vo);
        return vo;
    }

    /**
     * 转换角色为VO
     */
    private SysRoleVO convertRoleToVO(SysRole sysRole) {
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, vo);
        return vo;
    }
}
