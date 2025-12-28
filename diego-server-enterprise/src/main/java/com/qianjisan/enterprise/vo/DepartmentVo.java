package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Department 返回体 (VO)
 */
@Data
public class DepartmentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String deptCode;

    private String deptName;

    private Long parentId;

    private String description;

    private Integer sortOrder;

    private Integer status;

    private Long leaderId;

    private String leaderName;

    private String leaderCode;
}


