package com.qianjisan.console.dto;

import cn.hutool.core.date.DateTime;
import lombok.Data;


@Data
public class SelfUserCompanyDTO {

    private Long id;

    private String companyName;

    private String companyCode;

    private  Integer isDefault;
    private DateTime createTime;
}
