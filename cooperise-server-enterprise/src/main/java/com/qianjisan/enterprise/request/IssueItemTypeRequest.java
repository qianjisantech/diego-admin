package com.qianjisan.enterprise.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class IssueItemTypeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "类型名称不能为空")
    @Size(max = 100, message = "类型名称长度不能超过100个字符")
    private String name;

    @NotBlank(message = "类型编码不能为空")
    @Size(max = 50, message = "类型编码长度不能超过50个字符")
    private String code;

    @NotBlank(message = "状态不能为空")
    private String status;

    @Size(max = 500, message = "描述长度不能超过500个字符")
    private String description;

    @NotNull(message = "关联部门ID不能为空")
    private Long departmentId;

    @NotNull(message = "企业ID不能为空")
    private Long companyId;
}
