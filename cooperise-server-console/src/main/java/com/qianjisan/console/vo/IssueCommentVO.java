package com.qianjisan.console.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项评论VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueCommentVO extends BaseVO {

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 评论用户ID
     */
    private Long userId;

    /**
     * 评论用户�?
     */
    private String username;

    /**
     * 评论用户昵称
     */
    private String nickname;

    /**
     * 评论用户头像
     */
    private String avatar;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论ID（用于回复）
     */
    private Long parentId;
}
