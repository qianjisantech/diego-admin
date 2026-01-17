package com.qianjisan.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 埋点类型统计VO
 * 用于柱状图展示年月日每个埋点类型的量
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class EventTypeStatisticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 时间维度（年-�?�?�?�?�?�?年）
     */
    private String timeDimension;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 数量
     */
    private Long count;
}


