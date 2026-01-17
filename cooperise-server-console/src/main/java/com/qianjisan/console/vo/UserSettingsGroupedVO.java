package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户设置分组响应VO
 * 按照前端需要的格式分组返回
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UserSettingsGroupedVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号设置
     */
    private AccountSettings account;

    /**
     * 通知设置
     */
    private NotificationSettings notification;

    /**
     * 系统设置
     */
    private SystemSettings system;

    /**
     * 安全设置
     */
    private SecuritySettings security;

    /**
     * 账号设置
     */
    @Data
    public static class AccountSettings implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private Long userId;
        private String username;
        private String userCode;
        private String email;
        private String phone;
        private String avatar;
    }

    /**
     * 通知设置
     */
    @Data
    public static class NotificationSettings implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private Boolean task;
        private Boolean comment;
        private Boolean mention;
        private Boolean email;
        private Boolean system;
        private Boolean sms;
        private Boolean app;
        private String emailFrequency;
    }

    /**
     * 系统设置
     */
    @Data
    public static class SystemSettings implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private String language;
        private String theme;
        private String timezone;
        private String primaryColor;
    }

    /**
     * 安全设置
     */
    @Data
    public static class SecuritySettings implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        private Boolean twoFactorEnabled;
        private LocalDateTime lastLoginTime;
        private String lastLoginIp;
    }
}
