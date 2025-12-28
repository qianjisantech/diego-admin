package com.qianjisan.issue.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class IssueQueryRequest extends PageQuery {

    /**
     * 事项单号
     */
    private String issueNo;

    /**
     * 标题
     */
    private  String summary;
}




