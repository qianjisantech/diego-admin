package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * IssueDetail 返回体 (VO)
 */
@Data
public class IssueVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 事项ID
     */
    private Long id;

    /**
     * 企业id
     */
    private Long companyId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 企业编码
     */
    private String companyCode;


    /**
     * 事项单号
     */
    private String issueCode;

    /**
     * 扩展字段：相关事项详情列表
     */
    private List<IssueVO> issueDetailList;
}