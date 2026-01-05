package com.qianjisan.console.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图文件夹VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ViewFolderVO extends BaseVO {

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 创建者ID
     */
    private Long ownerId;

    /**
     * 创建者工号
     */
    private String ownerCode;

    /**
     * 创建者姓名
     */
    private String ownerName;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}

