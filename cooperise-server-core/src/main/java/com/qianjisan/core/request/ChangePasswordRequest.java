package com.qianjisan.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ChangePasswordRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前密码
     */
    @NotBlank(message = "当前密码不能为空")
    private String currentPassword;

    /**
     * 新密�?
     */
    @NotBlank(message = "新密码不能为�?)
    @Size(min = 6, max = 20, message = "密码长度必须�?-20个字符之�?)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{6,20}$",
             message = "密码必须包含大小写字母和数字")
    private String newPassword;

    /**
     * 确认新密�?
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;
}
