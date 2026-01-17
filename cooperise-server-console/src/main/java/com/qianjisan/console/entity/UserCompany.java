package com.qianjisan.console.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户-企业关联表实�?
 *
 * 根据SQL自动生成�?
 * CREATE TABLE `user_company` (
 *   `id` bigint NOT NULL AUTO_INCREMENT,
 *   `company_id` bigint NOT NULL COMMENT '企业ID',
 *   `user_id` bigint NOT NULL COMMENT '用户ID',
 *   `is_default` tinyint NOT NULL DEFAULT '0' COMMENT '是否默认部门 0为否 1为是',
 *   `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 *   PRIMARY KEY (`id`) USING BTREE,
 *   UNIQUE KEY `uk_company_dept` (`company_id`,`user_id`) USING BTREE,
 *   KEY `department_id` (`user_id`) USING BTREE
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
 *
 * @author Auto Generated from SQL
 * @since 2024-12-20
 */
@Data
@TableName("user_company")
public class UserCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 企业ID
     */
    @TableField("company_id")
    private Long companyId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否默认部门 0为否 1为是
     */
    @TableField("is_default")
    private Integer isDefault;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private LocalDateTime createdTime;
}
