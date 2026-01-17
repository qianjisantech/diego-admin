package com.qianjisan.console.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 视图文件夹查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class ViewFolderQueryRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Integer current = 1;

    /**
     * 每页大小
     */
    private Integer size = 10;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 搜索关键词（文件夹名称）
     */
    private String keyword;
}
