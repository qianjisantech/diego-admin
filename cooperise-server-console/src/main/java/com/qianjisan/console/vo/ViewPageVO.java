package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视图分页VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 视图ID
     */
    private Long id;

    /**
     * 视图名称
     */
    private String name;

    /**
     * 视图描述
     */
    private String description;

    /**
     * 视图类型：gantt-甘特图、kanban-看板、table-表格、calendar-日历、resource-gantt-资源甘特图
     */
    private String type;

    /**
     * 所属空间ID（空间视图）
     */
    private Long spaceId;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 是否公共：0-私有，1-公共
     */
    private Integer isPublic;

    /**
     * 所属文件夹ID
     */
    private Long folderId;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}

