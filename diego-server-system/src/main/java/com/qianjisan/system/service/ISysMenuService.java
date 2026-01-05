package com.qianjisan.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.system.request.SysMenuRequest;
import com.qianjisan.system.vo.SysMenuVO;
import com.qianjisan.system.entity.SysMenu;

import java.util.List;

/**
 * 菜单服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface ISysMenuService extends IService<SysMenu> {

    /**
     * 保存菜单
     *
     * @param request 菜单请求
     */
    void saveMenu(SysMenuRequest request);

    /**
     * 更新菜单
     *
     * @param id 菜单ID
     * @param request 菜单请求
     */
    void updateMenuById(Long id, SysMenuRequest request);

    /**
     * 获取菜单树
     *
     * @return 菜单树
     */
    List<SysMenuVO> getMenuTree();

    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    List<SysMenuVO> getMenuList();

    /**
     * 获取菜单详情
     *
     * @param id 菜单ID
     * @return 菜单VO
     */
    SysMenuVO getMenuDetail(Long id);

    /**
     * 根据用户ID获取菜单树
     *
     * @param userId 用户ID
     * @return 菜单树
     */
    List<SysMenuVO> getUserMenuTree(Long userId);

    /**
     * 根据用户ID获取菜单权限标识列表
     *
     * @param userId 用户ID
     * @return 权限标识列表
     */
    List<String> getUserMenuPermissions(Long userId);

    /**
     * 检查用户是否有访问指定URI的权限
     *
     * @param userId 用户ID
     * @param requestUri 请求URI
     * @return 是否有权限
     */
    boolean checkUserUriPermission(Long userId, String requestUri);
}
