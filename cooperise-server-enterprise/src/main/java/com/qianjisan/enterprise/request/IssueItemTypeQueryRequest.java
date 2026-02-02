package com.qianjisan.enterprise.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IssueItemTypeQueryRequest extends PageQuery {

    private String name;

    private String code;

    private String status;

    private Long departmentId;

    private Long companyId;
}
