package com.qianjisan.system.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 用户请求DTO（新�?编辑�?
 *
 * @author DCP Team
 * @since 2024-11-15
 */
@Data
public class SysUserRequest {

    /**
     * 用户�?
     */
    @NotBlank(message = "用户名不能为�?)
    @Size(min = 3, max = 50, message = "用户名长度必须在3-50个字符之�?)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划�?)
    private String name;

    /**
     * 用户编码
     */
    @NotBlank(message = "用户编码不能为空")
    @Size(max = 50, message = "用户编码不能超过50个字�?)
    private String userCode;

    /**
     * 邮箱
     */
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$",
            message = "邮箱格式不正�?)
    @Size(max = 100, message = "邮箱不能超过100个字�?)
    private String email;

    /**
     * 手机�?
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 状态：1-正常�?-禁用
     */
    private Integer status;

    /**
     * 头像URL
     */
    @Size(max = 500, message = "头像URL不能超过500个字�?)
    private String avatar;
}
