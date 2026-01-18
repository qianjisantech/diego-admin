package com.qianjisan.console.request;

import com.qianjisan.core.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 工作企业视图查询请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ViewQueryRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 企业ID
     */
    private Long spaceId;

    /**
     * 文件夹ID
     */
    private Long folderId;

    /**
     * 视图类型
     */
    private String type;

    /**
     * 所有者ID
     */
    private Long ownerId;

    /**
     * 是否公开
     */
    private Boolean isPublic;

    /**
     * 搜索关键词（视图名称或描述）
     */
    private String keyword;
}

