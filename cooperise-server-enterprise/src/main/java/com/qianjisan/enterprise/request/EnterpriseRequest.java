package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * Enterprise 请求体
 */
@Data
public class EnterpriseRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "enterpriseCode 不能为空")
    private String enterpriseCode;

    @NotBlank(message = "enterpriseName 不能为空")
    private String enterpriseName;

    private String shortName;

    private String creditCode;

    private String description;

    private String contactPerson;

    private String contactPhone;

    private String contactEmail;

    private String address;

    private Integer status;
}