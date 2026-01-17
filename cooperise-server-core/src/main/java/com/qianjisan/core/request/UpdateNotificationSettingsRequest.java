package com.qianjisan.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 更新通知设置请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UpdateNotificationSettingsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
     * 邮件通知频率：realtime-实时, daily-每日, weekly-每周
     */
    private String notificationEmailFrequency;
}
