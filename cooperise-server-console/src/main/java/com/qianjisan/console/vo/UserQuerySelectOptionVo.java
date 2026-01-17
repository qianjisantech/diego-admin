package com.qianjisan.console.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserQuerySelectOptionVo {


    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户�?
     */
    private String name;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机�?
     */
    private String phone;

    /**
     * 头像URL
     */
    private String avatar;


}
