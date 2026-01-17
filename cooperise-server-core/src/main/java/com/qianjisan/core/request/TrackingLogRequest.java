package com.qianjisan.request;

import lombok.Data;

/**
 * 埋点日志请求对象
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class TrackingLogRequest {

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
     * 扩展数据(JSON格式)
     */
    private String extraData;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 浏览器
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
}
