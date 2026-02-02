package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class IssueItemTypePageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String code;

    private String status;

    private String description;

    private Long departmentId;

    private Long companyId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String createByCode;

    private String updateByCode;

    private Integer isDeleted;

    private String createByName;

    private String updateByName;
}
