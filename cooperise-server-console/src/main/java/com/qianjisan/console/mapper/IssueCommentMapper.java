package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.entity.IssueComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事项评论 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface IssueCommentMapper extends BaseMapper<IssueComment> {

}
