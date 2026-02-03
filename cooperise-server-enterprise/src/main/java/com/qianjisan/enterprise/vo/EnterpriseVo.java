package com.qianjisan.enterprise.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Enterprise 返回体 (VO) - 公共模块
 */
@Data
public class EnterpriseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private String name;

    private String shortName;
}


