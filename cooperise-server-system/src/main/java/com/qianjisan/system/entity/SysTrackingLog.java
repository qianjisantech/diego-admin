package com.qianjisan.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 埋点日志实体类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@TableName("tracking_log")
public class SysTrackingLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 用户工号
     */
    @TableField("user_code")
    private String userCode;

    /**
     * 事件类型
     */
    @TableField("event_type")
    private String eventType;

    /**
     * 事件名称
     */
    @TableField("event_name")
    private String eventName;

    /**
     * 事件分类
     */
    @TableField("event_category")
    private String eventCategory;

    /**
     * 页面URL
     */
    @TableField("page_url")
    private String pageUrl;

    /**
     * 页面标题
     */
    @TableField("page_title")
    private String pageTitle;

    /**
     * 来源页面
     */
    @TableField("page_referrer")
    private String pageReferrer;

    /**
     * 元素ID
     */
    @TableField("element_id")
    private String elementId;

    /**
     * 元素Class
     */
    @TableField("element_class")
    private String elementClass;

    /**
     * 元素文本
     */
    @TableField("element_text")
    private String elementText;

    /**
     * 扩展数据(JSON格式)
     */
    @TableField("extra_data")
    private String extraData;

    /**
     * 会话ID
     */
    @TableField("session_id")
    private String sessionId;

    /**
     * IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 浏览器
     */
    @TableField("browser")
    private String browser;

    /**
     * 操作系统
     */
    @TableField("os")
    private String os;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;

    /**
     * 页面停留时间(毫秒)
     */
    @TableField("stay_time")
    private Long stayTime;

    /**
     * 页面加载时间(毫秒)
     */
    @TableField("load_time")
    private Long loadTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-否，1-是
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
