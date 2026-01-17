package com.qianjisan.console.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 反馈评论VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackCommentVO extends BaseVO {

    /**
     * 反馈ID
     */
    private Long feedbackId;

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
}
