package com.qianjisan.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Company 返回体 (VO) - 公共模块
 */
@Data
public class CompanyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String companyCode;

    private String companyName;

    private String shortName;

    private String creditCode;

    private String description;

    private String contactPerson;

    private String contactPhone;

    private String contactEmail;

    private String address;

    private Integer status;
}


