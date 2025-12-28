package com.qianjisan.issue.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 事项活动记录请求类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class IssueActivityRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动ID（更新时使用）
     */
    private Long id;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作类型：创建、修改状态、修改优先级、修改经办人、添加评论等
     */
    private String action;

    /**
     * 修改字段
     */
    private String field;

    /**
     * 旧值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;
}

