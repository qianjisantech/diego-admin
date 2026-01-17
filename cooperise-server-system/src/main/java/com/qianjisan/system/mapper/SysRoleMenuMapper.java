package com.qianjisan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 批量插入角色菜单关联（性能优化版本）
     * 使用一条 SQL 插入多条记录，性能提升 10-50 倍
     *
     * @param sysRoleMenuList 角色菜单关联列表
     * @return 插入成功的记录数
     */
    @Insert("<script>" +
            "INSERT INTO sys_role_menu (role_id, menu_id) " +
            "VALUES " +
            "<foreach collection='list' item='item' separator=','>" +
            "(#{item.roleId}, #{item.menuId})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("list") List<SysRoleMenu> sysRoleMenuList);
}
