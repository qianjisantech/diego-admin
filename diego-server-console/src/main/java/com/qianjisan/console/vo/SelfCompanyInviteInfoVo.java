package com.qianjisan.console.vo;

import lombok.Data;

@Data
public class SelfCompanyInviteInfoVo {


    private Long companyId;
    private String companyName;
    private String companyCode;
    private Long invitePersonId;
    private String invitePersonName;
    private String invitePersonCode;
    private  String inviteTraceCode; //代表本次的邀请码

}
