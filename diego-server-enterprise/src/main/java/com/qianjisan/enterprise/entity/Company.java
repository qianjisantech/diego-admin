package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公司实体
 *
 * 对应表：company
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("company")
public class Company extends BaseEntity {

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 公司编码 */
    @TableField("company_code")
    private String companyCode;

    /** 公司名称 */
    @TableField("company_name")
    private String companyName;

    /** 公司简称 */
    @TableField("short_name")
    private String shortName;

    /** 统一社会信用代码 */
    @TableField("credit_code")
    private String creditCode;

    /** 公司描述 */
    @TableField("description")
    private String description;

    /** 联系人 */
    @TableField("contact_person")
    private String contactPerson;

    /** 联系电话 */
    @TableField("contact_phone")
    private String contactPhone;

    /** 联系邮箱 */
    @TableField("contact_email")
    private String contactEmail;

    /** 公司地址 */
    @TableField("address")
    private String address;

    /** 状态：1-启用，0-禁用 */
    @TableField("status")
    private Integer status;
}

