package com.qianjisan.console.dto;

import lombok.Data;

@Data
public class SelfUserCompanyDTO {

    private Long id;

    private String companyName;

    private String companyCode;

    private  Integer isDefault;
}
