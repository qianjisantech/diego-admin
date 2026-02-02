package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业与部门关联实体
 *
 * 对应表：enterprise_department
 */
@Data
@TableName("enterprise_department")
public class EnterpriseDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 企业ID */
    @TableField("enterprise_id")
    private Long enterpriseId;

    /** 部门ID */
    @TableField("department_id")
    private Long departmentId;
}