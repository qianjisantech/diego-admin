package com.qianjisan.console.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 子任务请求类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class IssueSubtaskRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 子任务ID（更新时使用�?
     */
    private Long id;

    /**
     * 所属事项ID
     */
    private Long issueId;

    /**
     * 子任务标�?
     */
    private String title;

    /**
     * 是否完成�?-未完成，1-已完�?
     */
    private Integer completed;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}

