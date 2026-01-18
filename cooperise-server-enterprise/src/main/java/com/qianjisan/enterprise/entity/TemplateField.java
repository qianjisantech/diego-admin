package com.qianjisan.enterprise.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 事项表
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Getter
@Setter
@TableName("template_field")
public class TemplateField implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模板字段ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模板id
     */
    @TableField("template_id")
    private Long templateId;


    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除：0-否，1-是
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean isDeleted;

    /**
     * 创建人ID
     */
    @TableField("create_by_id")
    private Long createById;

    /**
     * 创建人用户名
     */
    @TableField("create_by_code")
    private String createByCode;

    /**
     * 创建人昵称
     */
    @TableField("create_by_name")
    private String createByName;

    /**
     * 更新人ID
     */
    @TableField("update_by_id")
    private Long updateById;

    /**
     * 更新人用户名
     */
    @TableField("update_by_code")
    private String updateByCode;

    /**
     * 更新人昵称
     */
    @TableField("update_by_name")
    private String updateByName;

    /**
     * 字段名称
     */
    @TableField("field_name")
    private String fieldName;

    /**
     * 字段编码
     */
    @TableField("field_code")
    private String fieldCode;

    /**
     * 字段类型
     */
    @TableField("field_type")
    private String fieldType;

    /**
     * 字段默认值
     */
    @TableField("field_default_value")
    private String fieldDefaultValue;

    /**
     * 是否可编辑
     */
    @TableField("is_edit")
    private Byte isEdit;

    /**
     * 是否必填
     */
    @TableField("is_required")
    private Byte isRequired;

    /**
     * 提示内容
     */
    @TableField("prompt_content")
    private String promptContent;

    /**
     * 字段顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 位置
     */
    @TableField("position")
    private String position;
}
