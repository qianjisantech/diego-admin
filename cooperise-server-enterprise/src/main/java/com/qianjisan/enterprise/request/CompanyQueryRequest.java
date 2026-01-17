package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyQueryRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(1)
    private Long current = 1L;

    @Min(1)
    private Long size = 10L;

    private String companyName;

    private String companyCode;
}


