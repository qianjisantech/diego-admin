package com.qianjisan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.system.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜单Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据用户ID查询菜单列表（包含父级菜单）
     *
     * 查询逻辑：
     * 1. 查询用户直接拥有权限的菜单
     * 2. 递归查询这些菜单的所有父级菜单
     * 3. 合并去重，返回完整的菜单树所需的所有节点
     *
     * @param userId 用户ID
     * @return 菜单列表（包含用户菜单及其所有父级菜单）
     */
    @Select("WITH RECURSIVE menu_tree AS ( " +
            "    SELECT DISTINCT m.* FROM sys_menu m " +
            "    INNER JOIN sys_role_menu srm ON m.id = srm.menu_id " +
            "    INNER JOIN sys_user_role sur ON srm.role_id = sur.role_id " +
            "    WHERE sur.user_id = #{userId} AND m.status = 1 AND m.is_deleted = 0 " +
            "    UNION " +
            "    SELECT p.* FROM sys_menu p " +
            "    INNER JOIN menu_tree c ON p.id = c.parent_id " +
            "    WHERE p.status = 1 AND p.is_deleted = 0 " +
            ") " +
            "SELECT DISTINCT * FROM menu_tree ORDER BY sort_order ASC")
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    /**
     * 查询所有启用的菜单
     *
     * @return 菜单列表
     */
    @Select("SELECT * FROM sys_menu WHERE status = 1 AND is_deleted = 0 ORDER BY sort_order ASC")
    List<SysMenu> selectAllActiveMenus();

    /**
     * 根据URI查询菜单
     *
     * @param uri 请求URI
     * @return 菜单信息
     */
    @Select("SELECT * FROM sys_menu WHERE path = #{uri} AND status = 1 AND is_deleted = 0 LIMIT 1")
    SysMenu selectMenuByUri(@Param("uri") String uri);

    /**
     * 检查用户是否有访问指定URI的权限
     *
     * @param userId 用户ID
     * @param uri 请求URI
     * @return 是否有权限（0-无权限，1-有权限）
     */
    @Select("SELECT COUNT(1) FROM sys_menu m " +
            "INNER JOIN sys_role_menu srm ON m.id = srm.menu_id " +
            "INNER JOIN sys_user_role sur ON srm.role_id = sur.role_id " +
            "WHERE sur.user_id = #{userId} AND m.path = #{uri} AND m.status = 1 AND m.is_deleted = 0")
    int checkUserUriPermission(@Param("userId") Long userId, @Param("uri") String uri);
}
