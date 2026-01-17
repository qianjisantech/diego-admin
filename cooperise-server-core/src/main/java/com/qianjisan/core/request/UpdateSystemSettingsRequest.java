package com.qianjisan.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 更新系统设置请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UpdateSystemSettingsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 语言设置：zh-CN, en-US等
     */
    private String language;

    /**
     * 主题：light-亮色, dark-暗色
     */
    private String theme;

    /**
     * 时区：Asia/Shanghai等
     */
    private String timezone;

    /**
     * 主题色
     */
    private String primaryColor;
}
