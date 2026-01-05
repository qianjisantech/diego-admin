package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.entity.FeedbackLike;
import org.apache.ibatis.annotations.Mapper;


/**
 * 反馈点赞 Mapper 接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface FeedbackLikeMapper extends BaseMapper<FeedbackLike> {

}
