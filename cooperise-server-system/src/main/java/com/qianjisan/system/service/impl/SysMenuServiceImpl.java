package com.qianjisan.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.system.request.SysMenuRequest;
import com.qianjisan.system.vo.SysMenuTreeVO;
import com.qianjisan.system.vo.SysMenuVO;
import com.qianjisan.system.entity.SysMenu;
import com.qianjisan.system.entity.SysUser;
import com.qianjisan.system.mapper.SysMenuMapper;
import com.qianjisan.system.service.ISysMenuService;
import com.qianjisan.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    private final SysMenuMapper menuMapper;
    private final ISysUserService userService;

    @Override
    public void saveMenu(SysMenuRequest request) {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(request, menu);
        menuMapper.insert(menu);
    }

    @Override
    public void updateMenuById(Long id, SysMenuRequest request) {
        SysMenu menu = new SysMenu();
        menu.setId(id);
        BeanUtils.copyProperties(request, menu);
        menuMapper.updateById(menu);
    }

    @Override
    public List<SysMenuTreeVO> getMenuTree() {
        // 查询所有菜�?
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSortOrder);
        List<SysMenu> allMenus = menuMapper.selectList(wrapper);

        // 转换为VO
        List<SysMenuTreeVO> allMenuVOs = allMenus.stream().map(menu -> {
            SysMenuTreeVO vo = new SysMenuTreeVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 构建树形结构
        return buildMenuTree(allMenuVOs, 0L);
    }

    @Override
    public List<SysMenuVO> getMenuList() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSortOrder);
        List<SysMenu> menus = menuMapper.selectList(wrapper);

        return menus.stream().map(menu -> {
            SysMenuVO vo = new SysMenuVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public SysMenuVO getMenuDetail(Long id) {
        SysMenu menu = menuMapper.selectById(id);
        if (menu == null) {
            return null;
        }

        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(menu, vo);
        return vo;
    }

    @Override
    public List<SysMenuTreeVO> getUserMenuTree(Long userId) {
        List<SysMenu> userMenus;

        // 查询用户的菜单（通过角色关联�?
        userMenus = menuMapper.selectMenusByUserId(userId);

        if (userMenus == null || userMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 转换为VO
        List<SysMenuTreeVO> menuVOs = userMenus.stream().map(menu -> {
            SysMenuTreeVO vo = new SysMenuTreeVO();
            BeanUtils.copyProperties(menu, vo);
            return vo;
        }).collect(Collectors.toList());

        // 构建树形结构
        return buildMenuTree(menuVOs, 0L);
    }

    @Override
    public List<String> getUserMenuPermissions(Long userId) {
        List<SysMenu> userMenus;

        // 查询用户的菜�?
        userMenus = menuMapper.selectMenusByUserId(userId);

        if (userMenus == null || userMenus.isEmpty()) {
            return new ArrayList<>();
        }

        // 提取权限标识（过滤掉null和空字符串）
        return userMenus.stream()
                .map(SysMenu::getPermission)
                .filter(permission -> permission != null && !permission.isEmpty())
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkUserUriPermission(Long userId, String requestUri) {
        // admin用户拥有所有权�?
        if ("admin".equalsIgnoreCase(getUserCodeById(userId))) {
            return true;
        }

        // 检查用户是否有该URI的访问权�?
        int count = menuMapper.checkUserUriPermission(userId, requestUri);
        return count > 0;
    }

    /**
     * 根据用户ID获取用户编码
     *
     * @param userId 用户ID
     * @return 用户编码
     */
    private String getUserCodeById(Long userId) {
        try {
            SysUser user = userService.getById(userId);
            return user != null ? user.getUserCode() : null;
        } catch (Exception e) {
            log.error("获取用户编码失败，用户ID: {}", userId, e);
            return null;
        }
    }

    /**
     * 递归构建菜单�?
     *
     * @param allMenus 所有菜�?
     * @param parentId 父菜单ID
     * @return 菜单�?
     */
    private List<SysMenuTreeVO> buildMenuTree(List<SysMenuTreeVO> allMenus, Long parentId) {
        List<SysMenuTreeVO> result = new ArrayList<>();

        for (SysMenuTreeVO menu : allMenus) {
            if (menu.getParentId().equals(parentId)) {
                // 递归查找子菜�?
                List<SysMenuTreeVO> children = buildMenuTree(allMenus, menu.getId());
                if (!children.isEmpty()) {
                    menu.setChildren(children);
                }
                result.add(menu);
            }
        }

        return result;
    }
}
