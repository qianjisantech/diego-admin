package com.qianjisan.console.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视图创建/更新请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 视图名称
     */
    @NotBlank(message = "视图名称不能为空")
    @Size(max = 200, message = "视图名称长度不能超过200个字�?)
    private String name;

    /**
     * 视图描述
     */
    @Size(max = 1000, message = "视图描述长度不能超过1000个字�?)
    private String description;

    /**
     * 视图类型：gantt-甘特图、kanban-看板、table-表格、calendar-日历、resource-gantt-资源甘特�?
     */
    @NotBlank(message = "视图类型不能为空")
    private String type;

    /**
     * 所属空间ID（空间视图）
     */
    private Long spaceId;

    /**
     * 是否公共�?-私有�?-公共
     */
    private Integer isPublic;

    /**
     * 所属文件夹ID
     */
    private Long folderId;

    /**
     * 视图配置（JSON格式�?
     */
    private String config;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}

