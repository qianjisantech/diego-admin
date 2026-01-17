package com.qianjisan.console.request;

import com.qianjisan.core.request.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户设置查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserSettingsQueryRequest extends PageRequest {

    /** 用户ID */
    private Long userId;

    /** 用户名（模糊查询，可选） */
    private String username;

    /** 语言偏好 */
    private String language;

    /** 主题偏好 */
    private String theme;
}
