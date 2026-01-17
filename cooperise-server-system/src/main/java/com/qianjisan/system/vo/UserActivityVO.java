package com.qianjisan.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户活跃量VO
 * 用于展示用户的活跃量
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UserActivityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 时间维度（年-�?�?�?�?�?�?年）
     */
    private String timeDimension;

    /**
     * 活跃用户数（去重后的用户数）
     */
    private Long activeUserCount;

    /**
     * 总访问次�?
     */
    private Long totalCount;
}


