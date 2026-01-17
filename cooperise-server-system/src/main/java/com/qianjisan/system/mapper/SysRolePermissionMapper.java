package com.qianjisan.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.system.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

}
