package com.qianjisan.enterprise.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SelfUserEnterpriseDTO {

    private Long id;

    private String name;

    private String code;

    private Integer isDefault;

    private LocalDateTime createTime;
}