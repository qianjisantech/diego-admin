package com.qianjisan.console.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 视图树节�?VO（包含文件夹和视图的树形结构�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewTreeNodeVO {

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点类型：folder-文件夹、view-视图
     */
    private String type;

    /**
     * 视图类型（仅�?type=view 时有值）：gantt、kanban、table、calendar、resource-gantt
     */
    private String viewType;

    /**
     * 描述（仅视图有）
     */
    private String description;

    /**
     * 所属空间ID（仅视图有）
     */
    private Long spaceId;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 是否公共（仅视图有）�?-私有�?-公共
     */
    private Integer isPublic;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 所属文件夹ID（视图的 folder_id�?
     */
    private Long folderId;

    /**
     * 视图配置（JSON格式，仅视图有）
     */
    private String config;

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

    /**
     * 子节点列表（文件夹包含的子文件夹和视图）
     */
    private List<ViewTreeNodeVO> children;
}
