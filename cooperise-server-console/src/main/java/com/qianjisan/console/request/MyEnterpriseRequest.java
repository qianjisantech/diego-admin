package com.qianjisan.console.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Company 请求体
 */
@Data
public class MyEnterpriseRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotBlank(message = "企业名称不能为空")
    private String name;

    @NotBlank(message = "企业规模不能为空")
    private String size;

    @NotBlank(message = "企业所在行业不能为空")
    private String industry;

}


