package com.qianjisan.console.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianjisan.console.entity.Issue;
import org.apache.ibatis.annotations.Mapper;

/**
 * 事项Mapper接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Mapper
public interface IssueMapper extends BaseMapper<Issue> {

}
