package com.qianjisan.issue.request;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 事项活动记录VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueActivityVO extends BaseVO {

    /**
     * 事项ID
     */
    private Long issueId;

    /**
     * 操作用户ID
     */
    private Long userId;

    /**
     * 操作用户名
     */
    private String username;

    /**
     * 操作用户昵称
     */
    private String nickname;

    /**
     * 操作用户头像
     */
    private String avatar;

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

    /**
     * 活动类型
     */
    private String activityType;

    /**
     * 活动描述
     */
    private String description;
}




