package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.entity.SysUserSettings;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户设置 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface UserSettingsMapper extends BaseMapper<SysUserSettings> {

}
