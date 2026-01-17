package com.qianjisan.system.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 埋点日志查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysTrackingLogQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 用户�?*/
    private String username;

    /** 事件类型 */
    private String eventType;

    /** 事件名称 */
    private String eventName;

    /** 页面URL */
    private String pageUrl;

    /** 开始时�?*/
    private String startTime;

    /** 结束时间 */
    private String endTime;

    /** 时间类型（day/month/year�?*/
    private String timeType;

    /** 搜索关键�?*/
    private String keyword;
}
