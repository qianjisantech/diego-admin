package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.UserSettingsQueryDTO;
import com.dcp.entity.SysUserSettings;

/**
 * UserSettings服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IUserSettingsService extends IService<SysUserSettings> {

    /**
     * 分页查询用户设置
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<SysUserSettings> pageUserSettings(UserSettingsQueryDTO query);
}
