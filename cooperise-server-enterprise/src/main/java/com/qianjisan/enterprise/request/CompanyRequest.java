package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * Company 请求�?
 */
@Data
public class CompanyRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "companyCode 不能为空")
    private String companyCode;

    @NotBlank(message = "companyName 不能为空")
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


