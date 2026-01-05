package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;


/**
 * 反馈 Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

}
