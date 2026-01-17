package com.qianjisan.console.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 事项评论请求�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class IssueCommentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论ID（更新时使用�?
     */
    private Long id;

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID（用于回复）
     */
    private Long parentId;
}

