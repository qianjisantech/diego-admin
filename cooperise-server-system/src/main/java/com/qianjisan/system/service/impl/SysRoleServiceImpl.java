package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.core.utils.PageUtils;
import com.qianjisan.system.request.AssignPermissionRequest;
import com.qianjisan.system.request.SysRoleQueryRequest;
import com.qianjisan.system.request.SysRoleRequest;
import com.qianjisan.system.vo.SysRoleVO;
import com.qianjisan.system.entity.SysRole;
import com.qianjisan.system.entity.SysRoleMenu;
import com.qianjisan.system.mapper.SysRoleMapper;
import com.qianjisan.system.mapper.SysRoleMenuMapper;
import com.qianjisan.system.service.ISysRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public void saveRole(SysRoleRequest request) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(request, sysRole);
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void updateRoleById(Long id, SysRoleRequest request) {
        SysRole sysRole = new SysRole();
        sysRole.setId(id);
        BeanUtils.copyProperties(request, sysRole);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public Page<SysRoleVO> queryPage(SysRoleQueryRequest request) {
        Page<SysRole> page = new Page<>(request.getCurrent(), request.getPageSize());

        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(request.getRoleCode()), SysRole::getRoleCode, request.getRoleCode())
                .like(StringUtils.hasText(request.getRoleName()), SysRole::getRoleName, request.getRoleName())
                .eq(request.getStatus() != null, SysRole::getStatus, request.getStatus())
                .orderByAsc(SysRole::getId)
                .orderByDesc(SysRole::getCreateTime);

        Page<SysRole> entityPage = sysRoleMapper.selectPage(page, wrapper);

        return PageUtils.convertPage(entityPage, sysRole -> {
            SysRoleVO vo = new SysRoleVO();
            BeanUtils.copyProperties(sysRole, vo);
            return vo;
        });
    }

    @Override
    public List<SysRoleVO> getRoleList() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getStatus, 1)
                .orderByAsc(SysRole::getId);

        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);

        return sysRoles.stream().map(sysRole -> {
            SysRoleVO vo = new SysRoleVO();
            BeanUtils.copyProperties(sysRole, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public SysRoleVO getRoleDetail(Long id) {
        SysRole sysRole = sysRoleMapper.selectById(id);
        if (sysRole == null) {
            return null;
        }

        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(sysRole, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignPermissions(Long roleId, AssignPermissionRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("[分配权限] 开始为角色 {} 分配 {} 个权�?, roleId, request.getMenuIds().size());

        // 步骤 1: 删除该角色的所有权�?
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId, roleId);
        int deletedCount = sysRoleMenuMapper.delete(wrapper);

        // 根据删除数量输出不同的日�?
        if (deletedCount > 0) {
            log.info("[分配权限] 删除�?{} 条旧权限记录", deletedCount);
        } else {
            log.info("[分配权限] 该角色暂无权限，无需删除");
        }

        // 步骤 2: 批量插入新权�?
        if (!request.getMenuIds().isEmpty()) {
            // 构建批量插入的数�?
            List<SysRoleMenu> sysRoleMenuList = request.getMenuIds().stream()
                .map(menuId -> {
                    SysRoleMenu sysRoleMenu = new SysRoleMenu();
                    sysRoleMenu.setRoleId(roleId);
                    sysRoleMenu.setMenuId(menuId);
                    return sysRoleMenu;
                })
                .collect(Collectors.toList());

            // 使用真正的批量插入（性能提升 10-50 倍）
            // 如果数据量超�?500 条，分批插入避免 SQL 过长
            int batchSize = 500;
            int totalSize = sysRoleMenuList.size();
            int totalInserted = 0;

            if (totalSize <= batchSize) {
                // 小于 500 条，一次性插�?
                int inserted = sysRoleMenuMapper.batchInsert(sysRoleMenuList);
                totalInserted += inserted;
                log.info("[分配权限] 批量插入 {} 条记�?, inserted);
            } else {
                // 大于 500 条，分批插入
                int batchCount = (totalSize + batchSize - 1) / batchSize;
                for (int i = 0; i < totalSize; i += batchSize) {
                    int end = Math.min(i + batchSize, totalSize);
                    List<SysRoleMenu> batch = sysRoleMenuList.subList(i, end);

                    int inserted = sysRoleMenuMapper.batchInsert(batch);
                    totalInserted += inserted;

                    int currentBatch = (i / batchSize) + 1;
                    log.info("[分配权限] 批次 {}/{} 完成，本批插�?{} 条，累计 {}/{}",
                        currentBatch, batchCount, inserted, totalInserted, totalSize);
                }
            }

            if (totalInserted != totalSize) {
                throw new RuntimeException(
                    String.format("批量插入权限失败：预期插�?%d 条，实际插入 %d �?, totalSize, totalInserted));
            }

            long endTime = System.currentTimeMillis();
            log.info("[分配权限] 成功为角�?{} 分配 {} 个权限，耗时: {} ms",
                roleId, request.getMenuIds().size(), (endTime - startTime));
        } else {
            // 更清晰的日志描述
            if (deletedCount > 0) {
                long endTime = System.currentTimeMillis();
                log.info("[分配权限] 菜单列表为空，已清空角色 {} 的所有权限，耗时: {} ms",
                    roleId, (endTime - startTime));
            } else {
                long endTime = System.currentTimeMillis();
                log.info("[分配权限] 菜单列表为空，角�?{} 保持无权限状态，耗时: {} ms",
                    roleId, (endTime - startTime));
            }
        }
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        return sysRoleMapper.selectMenuIdsByRoleId(roleId);
    }
}
