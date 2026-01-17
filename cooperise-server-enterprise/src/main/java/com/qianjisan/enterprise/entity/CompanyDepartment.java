package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 公司与部门关联实�?
 *
 * 对应表：company_department
 */
@Data
@TableName("company_department")
public class CompanyDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 公司ID */
    @TableField("company_id")
    private Long companyId;

    /** 部门ID */
    @TableField("department_id")
    private Long departmentId;
}

