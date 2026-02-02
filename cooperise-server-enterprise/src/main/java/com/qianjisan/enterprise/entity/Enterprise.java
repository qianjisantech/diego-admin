package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 企业实体
 *
 * 对应表：enterprise
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enterprise")
public class Enterprise extends BaseEntity {

    /** 主键ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 企业编码 */
    @TableField("code")
    private String code;

    /** 企业名称 */
    @TableField("name")
    private String name;

    /** 企业简称 */
    @TableField("short_name")
    private String shortName;


    /** 企业描述 */
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

    /** 企业地址 */
    @TableField("address")
    private String address;

    /** 状态：1-启用，0-禁用 */
    @TableField("status")
    private Integer status;

    /** 企业规模 */
    @TableField("size")
    private String size;


    /** 所属行业 */
    @TableField("industry")
    private String industry;
}