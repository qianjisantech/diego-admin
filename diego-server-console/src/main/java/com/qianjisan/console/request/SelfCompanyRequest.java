package com.qianjisan.console.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Company 请求体
 */
@Data
public class SelfCompanyRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @NotBlank(message = "companyName 不能为空")
    private String companyName;

    @NotBlank(message = "companySize 不能为空")
    private String companySize; //规模

    @NotBlank(message = "industry 不能为空")
    private String industry;  //行业

}


