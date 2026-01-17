package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serializable;

/**
 * Department 请求�?
 */
@Data
public class DepartmentRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "deptCode 不能为空")
    private String deptCode;

    @NotBlank(message = "deptName 不能为空")
    private String deptName;

    private Long parentId;

    private String description;

    private Integer sortOrder;

    private Integer status;

    private Long leaderId;

    private String leaderName;

    private String leaderCode;
}


