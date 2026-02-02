package com.qianjisan.console.vo;

import lombok.Data;

@Data
public class EnterpriseInviteInfoVo {


    private Long id;
    private String name;
    private String code;
    private Long invitePersonId;
    private String invitePersonName;
    private String invitePersonCode;
    private  String inviteTraceCode; //代表本次的邀请码

}
