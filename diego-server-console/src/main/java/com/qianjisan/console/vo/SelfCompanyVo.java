package com.qianjisan.console.vo;

import lombok.Data;

@Data
public class SelfCompanyVo {

    private Long id;

    private String companyName;

    private String companyCode;

    private  Boolean isDefault;
}
