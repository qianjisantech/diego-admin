package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户设置完整响应VO
 * 包含账号信息、通知设置、系统设置
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UserSettingsResponseVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // ========== 账号信息 ==========
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称/姓名
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatar;

    // ========== 通知设置 ==========
    /**
     * 邮件通知开关
     */
    private Boolean notificationEmail;

    /**
     * 系统通知开关
     */
    private Boolean notificationSystem;

    /**
     * 短信通知开关
     */
    private Boolean notificationSms;

    /**
     * 应用内通知开关
     */
    private Boolean notificationApp;

    /**
     * 邮件通知频率
     */
    private String notificationEmailFrequency;

    // ========== 系统设置 ==========
    /**
     * 语言设置
     */
    private String language;

    /**
     * 主题
     */
    private String theme;

    /**
     * 时区
     */
    private String timezone;

    /**
     * 主题色
     */
    private String primaryColor;

    // ========== 安全设置 ==========
    /**
     * 双重认证开关
     */
    private Boolean twoFactorEnabled;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;
}
