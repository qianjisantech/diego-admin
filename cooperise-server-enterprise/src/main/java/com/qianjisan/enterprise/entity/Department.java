package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qianjisan.core.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门实体
 *
 * 对应表：department
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("department")
public class Department extends BaseEntity {

    /** 部门ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 部门编码 */
    @TableField("dept_code")
    private String deptCode;

    /** 部门名称 */
    @TableField("dept_name")
    private String deptName;

    /** 父部门ID，0表示顶级部门 */
    @TableField("parent_id")
    private Long parentId;

    /** 部门描述 */
    @TableField("description")
    private String description;

    /** 排序 */
    @TableField("sort_order")
    private Integer sortOrder;

    /** 状态：1-启用，0-禁用 */
    @TableField("status")
    private Integer status;

    /** 负责人ID */
    @TableField("leader_id")
    private Long leaderId;

    /** 负责人姓名 */
    @TableField("leader_name")
    private String leaderName;

    /** 负责人工号 */
    @TableField("leader_code")
    private String leaderCode;
}

