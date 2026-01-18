package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * IssueDetail 返回体 (VO)
 */
@Data
public class IssueDetailVO implements Serializable {

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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建人ID
     */
    private Long createById;

    /**
     * 创建人用户名
     */
    private String createByCode;

    /**
     * 创建人昵称
     */
    private String createByName;

    /**
     * 更新人ID
     */
    private Long updateById;

    /**
     * 更新人用户名
     */
    private String updateByCode;

    /**
     * 更新人昵称
     */
    private String updateByName;

    /**
     * 模板字段id
     */
    private Long templateFieldId;

    /**
     * 模板值
     */
    private String valueString;

    /**
     * 模板值json
     */
    private String valueJson;

    /**
     * 扩展字段：相关事项详情列表
     */
    private List<IssueDetailVO> issueDetailList;
}