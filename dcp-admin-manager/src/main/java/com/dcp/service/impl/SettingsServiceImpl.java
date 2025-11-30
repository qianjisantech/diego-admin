package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dcp.common.exception.BusinessException;
import com.dcp.common.request.ChangePasswordRequest;
import com.dcp.common.request.UpdateAccountSettingsRequest;
import com.dcp.common.request.UpdateNotificationSettingsRequest;
import com.dcp.common.request.UpdateSystemSettingsRequest;
import com.dcp.common.util.PasswordUtil;
import com.dcp.common.vo.LoginLogVO;
import com.dcp.common.vo.PageVO;
import com.dcp.common.vo.UserSettingsGroupedVO;
import com.dcp.common.vo.UserSettingsResponseVO;
import com.dcp.entity.SysLoginLog;
import com.dcp.rbac.entity.SysUser;
import com.dcp.entity.SysUserSettings;
import com.dcp.mapper.LoginLogMapper;
import com.dcp.rbac.mapper.SysUserMapper;
import com.dcp.mapper.UserSettingsMapper;
import com.dcp.service.ISettingsService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 设置服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements ISettingsService {

    private final SysUserMapper sysUserMapper;
    private final UserSettingsMapper userSettingsMapper;
    private final LoginLogMapper loginLogMapper;

    @Override
    public UserSettingsResponseVO getUserSettings(Long userId) {
        // 1. 查询用户信息
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 查询用户设置
        LambdaQueryWrapper<SysUserSettings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserSettings::getUserId, userId);
        SysUserSettings settings = userSettingsMapper.selectOne(wrapper);

        // 3. 如果用户设置不存在，创建默认设置
        if (settings == null) {
            settings = createDefaultSettings(userId);
        }

        // 4. 组装响应VO
        UserSettingsResponseVO vo = new UserSettingsResponseVO();
        vo.setUserId(sysUser.getId());
        vo.setUsername(sysUser.getUsername());
        vo.setNickname(sysUser.getUserCode());
        vo.setEmail(sysUser.getEmail());
        vo.setPhone(sysUser.getPhone());
        vo.setAvatar(sysUser.getAvatar());
        vo.setLastLoginTime(sysUser.getLastLoginTime());
        vo.setLastLoginIp(sysUser.getLastLoginIp());

        // 设置通知配置
        vo.setNotificationEmail(settings.getNotificationEmail() == 1);
        vo.setNotificationSystem(settings.getNotificationSystem() == 1);
        vo.setNotificationSms(settings.getNotificationSms() != null && settings.getNotificationSms() == 1);
        vo.setNotificationApp(settings.getNotificationApp() != null && settings.getNotificationApp() == 1);
        vo.setNotificationEmailFrequency(settings.getNotificationEmailFrequency());

        // 设置系统配置
        vo.setLanguage(settings.getLanguage());
        vo.setTheme(settings.getTheme());
        vo.setTimezone(settings.getTimezone());
        vo.setPrimaryColor(settings.getPrimaryColor());

        // 设置安全配置
        vo.setTwoFactorEnabled(settings.getTwoFactorEnabled() == 1);

        return vo;
    }

    @Override
    public UserSettingsGroupedVO getUserSettingsGrouped(Long userId) {
        // 1. 查询用户信息
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 2. 查询用户设置
        LambdaQueryWrapper<SysUserSettings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserSettings::getUserId, userId);
        SysUserSettings settings = userSettingsMapper.selectOne(wrapper);

        // 3. 如果用户设置不存在，创建默认设置
        if (settings == null) {
            settings = createDefaultSettings(userId);
        }

        // 4. 组装分组响应VO
        UserSettingsGroupedVO vo = new UserSettingsGroupedVO();

        // 账号设置
        UserSettingsGroupedVO.AccountSettings account = new UserSettingsGroupedVO.AccountSettings();
        account.setUserId(sysUser.getId());
        account.setUsername(sysUser.getUsername());
        account.setUserCode(sysUser.getUserCode());
        account.setEmail(sysUser.getEmail());
        account.setPhone(sysUser.getPhone());
        account.setAvatar(sysUser.getAvatar());
        vo.setAccount(account);

        // 通知设置
        UserSettingsGroupedVO.NotificationSettings notification = new UserSettingsGroupedVO.NotificationSettings();
        notification.setTask(settings.getNotificationSystem() == 1);
        notification.setComment(settings.getNotificationSystem() == 1);
        notification.setMention(settings.getNotificationSystem() == 1);
        notification.setEmail(settings.getNotificationEmail() == 1);
        notification.setSystem(settings.getNotificationSystem() == 1);
        notification.setSms(settings.getNotificationSms() != null && settings.getNotificationSms() == 1);
        notification.setApp(settings.getNotificationApp() != null && settings.getNotificationApp() == 1);
        notification.setEmailFrequency(settings.getNotificationEmailFrequency() != null ? settings.getNotificationEmailFrequency() : "realtime");
        vo.setNotification(notification);

        // 系统设置
        UserSettingsGroupedVO.SystemSettings system = new UserSettingsGroupedVO.SystemSettings();
        system.setLanguage(settings.getLanguage());
        system.setTheme(settings.getTheme());
        system.setTimezone(settings.getTimezone());
        system.setPrimaryColor(settings.getPrimaryColor() != null ? settings.getPrimaryColor() : "#0052d9");
        vo.setSystem(system);

        // 安全设置
        UserSettingsGroupedVO.SecuritySettings security = new UserSettingsGroupedVO.SecuritySettings();
        security.setTwoFactorEnabled(settings.getTwoFactorEnabled() == 1);
        security.setLastLoginTime(sysUser.getLastLoginTime());
        security.setLastLoginIp(sysUser.getLastLoginIp());
        vo.setSecurity(security);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAccountSettings(Long userId, UpdateAccountSettingsRequest request) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new BusinessException("用户不存在");
        }

        if (request.getEmail() != null) {
            sysUser.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            sysUser.setPhone(request.getPhone());
        }
        if (StringUtils.isNotEmpty(request.getUsername())){
            sysUser.setUsername(request.getUsername());
        }
        sysUserMapper.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNotificationSettings(Long userId, UpdateNotificationSettingsRequest request) {
        SysUserSettings settings = getUserSettingsByUserId(userId);

        // 更新通知设置
        if (request.getNotificationEmail() != null) {
            settings.setNotificationEmail(request.getNotificationEmail() ? 1 : 0);
        }
        if (request.getNotificationSystem() != null) {
            settings.setNotificationSystem(request.getNotificationSystem() ? 1 : 0);
        }
        if (request.getNotificationSms() != null) {
            settings.setNotificationSms(request.getNotificationSms() ? 1 : 0);
        }
        if (request.getNotificationApp() != null) {
            settings.setNotificationApp(request.getNotificationApp() ? 1 : 0);
        }
        if (request.getNotificationEmailFrequency() != null) {
            settings.setNotificationEmailFrequency(request.getNotificationEmailFrequency());
        }

        userSettingsMapper.updateById(settings);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSystemSettings(Long userId, UpdateSystemSettingsRequest request) {
        SysUserSettings settings = getUserSettingsByUserId(userId);

        // 更新系统设置
        if (request.getLanguage() != null) {
            settings.setLanguage(request.getLanguage());
        }
        if (request.getTheme() != null) {
            settings.setTheme(request.getTheme());
        }
        if (request.getTimezone() != null) {
            settings.setTimezone(request.getTimezone());
        }
        if (request.getPrimaryColor() != null) {
            settings.setPrimaryColor(request.getPrimaryColor());
        }

        userSettingsMapper.updateById(settings);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(Long userId, ChangePasswordRequest request) {
        // 1. 验证新密码和确认密码是否一致
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 2. 查询用户
        SysUser sysUser = sysUserMapper.selectById(userId);
        if (sysUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 3. 验证当前密码
        if (!PasswordUtil.matches(request.getCurrentPassword(), sysUser.getPassword())) {
            throw new RuntimeException("当前密码错误");
        }

        // 4. 加密新密码并更新
        sysUser.setPassword(PasswordUtil.encode(request.getNewPassword()));
        sysUserMapper.updateById(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String uploadAvatar(Long userId, MultipartFile file) {
        // TODO: 实现文件上传逻辑
        // 1. 验证文件类型和大小
        // 2. 生成文件名
        // 3. 保存文件到服务器或OSS
        // 4. 更新用户头像URL
        throw new RuntimeException("头像上传功能待实现");
    }

    @Override
    public PageVO<LoginLogVO> getLoginLogs(Long userId, Long current, Long size) {
        // 1. 构建查询条件
        LambdaQueryWrapper<SysLoginLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysLoginLog::getUserId, userId);
        wrapper.orderByDesc(SysLoginLog::getLoginTime);

        // 2. 执行分页查询
        Page<SysLoginLog> page = new Page<>(current, size);
        Page<SysLoginLog> entityPage = loginLogMapper.selectPage(page, wrapper);

        // 3. 转换为VO
        List<LoginLogVO> voList = entityPage.getRecords().stream()
                .map(this::convertToLoginLogVO)
                .collect(Collectors.toList());

        // 4. 构建PageVO
        PageVO<LoginLogVO> pageVO = new PageVO<>();
        pageVO.setRecords(voList);
        pageVO.setTotal(entityPage.getTotal());
        pageVO.setSize(entityPage.getSize());
        pageVO.setCurrent(entityPage.getCurrent());
        pageVO.setPages(entityPage.getPages());
        pageVO.setHasPrevious(entityPage.hasPrevious());
        pageVO.setHasNext(entityPage.hasNext());

        return pageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void enableTwoFactor(Long userId) {
        SysUserSettings settings = getUserSettingsByUserId(userId);
        settings.setTwoFactorEnabled(1);
        userSettingsMapper.updateById(settings);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void disableTwoFactor(Long userId) {
        SysUserSettings settings = getUserSettingsByUserId(userId);
        settings.setTwoFactorEnabled(0);
        userSettingsMapper.updateById(settings);
    }

    /**
     * 获取用户设置，如果不存在则创建默认设置
     */
    private SysUserSettings getUserSettingsByUserId(Long userId) {
        LambdaQueryWrapper<SysUserSettings> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserSettings::getUserId, userId);
        SysUserSettings settings = userSettingsMapper.selectOne(wrapper);

        if (settings == null) {
            settings = createDefaultSettings(userId);
        }

        return settings;
    }

    /**
     * 创建默认用户设置
     */
    private SysUserSettings createDefaultSettings(Long userId) {
        SysUserSettings settings = new SysUserSettings();
        settings.setUserId(userId);
        settings.setLanguage("zh-CN");
        settings.setTheme("light");
        settings.setTimezone("Asia/Shanghai");
        settings.setPrimaryColor("#0052d9");
        settings.setNotificationEmail(1);
        settings.setNotificationSystem(1);
        settings.setNotificationSms(1);
        settings.setNotificationApp(1);
        settings.setNotificationEmailFrequency("realtime");
        settings.setTwoFactorEnabled(0);

        userSettingsMapper.insert(settings);
        return settings;
    }

    /**
     * 转换LoginLog Entity到VO
     */
    private LoginLogVO convertToLoginLogVO(SysLoginLog entity) {
        LoginLogVO vo = new LoginLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
