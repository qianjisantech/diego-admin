package com.qianjisan.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新账号设置请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class UpdateAccountSettingsRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Size(max = 50, message = "姓名不能超过50个字�?)
    private String name;

    /**
     * 用户编码
     */
    @Size(max = 50, message = "姓名不能超过50个字�?)
    private String userCode;
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正�?)
    @Size(max = 100, message = "邮箱长度不能超过100个字�?)
    private String email;

    /**
     * 手机�?
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
}
