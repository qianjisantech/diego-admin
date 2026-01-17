package com.qianjisan.request;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingsVO extends BaseVO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 语言设置
     */
    private String language;

    /**
     * 主题：light-浅色，dark-深色
     */
    private String theme;

    /**
     * 时区
     */
    private String timezone;

    /**
     * 邮件通知：1-开启，0-关闭
     */
    private Integer notificationEmail;

    /**
     * 系统通知：1-开启，0-关闭
     */
    private Integer notificationSystem;

    /**
     * 双重认证：1-开启，0-关闭
     */
    private Integer twoFactorEnabled;
}
