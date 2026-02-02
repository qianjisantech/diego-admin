package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_enterprise")
public class UserEnterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("enterprise_id")
    private Long enterpriseId;

    @TableField("user_id")
    private Long userId;

    @TableField("is_default")
    private Integer isDefault;

    @TableField("created_time")
    private LocalDateTime createdTime;
}