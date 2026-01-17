package com.qianjisan.console.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 视图文件夹分页VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewFolderPageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件夹ID
     */
    private Long id;

    /**
     * 文件夹名�?
     */
    private String name;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 父文件夹ID
     */
    private Long parentId;

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

