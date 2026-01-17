package com.qianjisan.console.vo;

import com.qianjisan.core.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子任务VO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IssueSubtaskVO extends BaseVO {

    /**
     * 所属事项ID
     */
    private Long issueId;

    /**
     * 子任务标�?
     */
    private String title;

    /**
     * 是否完成�?-未完成，1-已完�?
     */
    private Integer completed;

    /**
     * 排序顺序
     */
    private Integer sortOrder;
}




