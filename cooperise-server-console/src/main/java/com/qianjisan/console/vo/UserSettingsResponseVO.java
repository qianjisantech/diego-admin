package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户设置完整响应VO
 * 包含账号信息、通知设置、系统设�?
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
     * 用户�?
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
     * 手机�?
     */
    private String phone;

    /**
     * 头像地址
     */
    private String avatar;

    // ========== 通知设置 ==========
    /**
     * 邮件通知开�?
     */
    private Boolean notificationEmail;

    /**
     * 系统通知开�?
     */
    private Boolean notificationSystem;

    /**
     * 短信通知开�?
     */
    private Boolean notificationSms;

    /**
     * 应用内通知开�?
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
     * 主题�?
     */
    private String primaryColor;

    // ========== 安全设置 ==========
    /**
     * 双重认证开�?
     */
    private Boolean twoFactorEnabled;

    /**
     * 最后登录时�?
     */
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    private String lastLoginIp;
}
