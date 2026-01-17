package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置表
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_settings")
public class SysUserSettings extends BaseEntity {

    /**
     * 设置ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 语言设置
     */
    @TableField("language")
    private String language;

    /**
     * 主题：light-浅色，dark-深色
     */
    @TableField("theme")
    private String theme;

    /**
     * 时区
     */
    @TableField("timezone")
    private String timezone;

    /**
     * 邮件通知：1-开启，0-关闭
     */
    @TableField("notification_email")
    private Integer notificationEmail;

    /**
     * 系统通知：1-开启，0-关闭
     */
    @TableField("notification_system")
    private Integer notificationSystem;

    /**
     * 双重认证：1-开启，0-关闭
     */
    @TableField("two_factor_enabled")
    private Integer twoFactorEnabled;

    /**
     * 短信通知：1-开启，0-关闭
     */
    @TableField("notification_sms")
    private Integer notificationSms;

    /**
     * 应用内通知：1-开启，0-关闭
     */
    @TableField("notification_app")
    private Integer notificationApp;

    /**
     * 邮件通知频率：realtime-实时，daily-每日，weekly-每周
     */
    @TableField("notification_email_frequency")
    private String notificationEmailFrequency;

    /**
     * 主题色
     */
    @TableField("primary_color")
    private String primaryColor;
}
