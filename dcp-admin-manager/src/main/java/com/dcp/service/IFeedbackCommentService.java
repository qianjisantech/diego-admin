package com.dcp.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dcp.common.dto.FeedbackCommentQueryDTO;
import com.dcp.entity.FeedbackComment;

/**
 * FeedbackComment服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IFeedbackCommentService extends IService<FeedbackComment> {

    /**
     * 分页查询反馈评论管理
     *
     * @param query 查询条件
     * @return 分页结果
     */
    Page<FeedbackComment> pageFeedbackComment(FeedbackCommentQueryDTO query);
}
