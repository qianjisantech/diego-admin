package com.qianjisan.system.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 埋点日志VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class SysTrackingLogVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户�?
     */
    private String username;

    /**
     * 用户工号
     */
    private String userCode;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件分类
     */
    private String eventCategory;

    /**
     * 页面URL
     */
    private String pageUrl;

    /**
     * 页面标题
     */
    private String pageTitle;

    /**
     * 来源页面
     */
    private String pageReferrer;

    /**
     * 元素ID
     */
    private String elementId;

    /**
     * 元素Class
     */
    private String elementClass;

    /**
     * 元素文本
     */
    private String elementText;

    /**
     * 扩展数据
     */
    private String extraData;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 浏览�?
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 页面停留时间(毫秒)
     */
    private Long stayTime;

    /**
     * 页面加载时间(毫秒)
     */
    private Long loadTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
