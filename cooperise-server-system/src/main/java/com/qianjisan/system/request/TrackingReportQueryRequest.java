package com.qianjisan.system.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 埋点报表查询请求
 */
@Data
public class TrackingReportQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 时间类型（day/month/year） */
    private String timeType;

    /** 开始时间 */
    private String startTime;

    /** 结束时间 */
    private String endTime;
}

