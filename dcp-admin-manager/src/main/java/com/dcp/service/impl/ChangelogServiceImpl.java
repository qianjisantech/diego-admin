package com.dcp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dcp.common.dto.ChangelogQueryDTO;
import com.dcp.entity.SysChangelog;
import com.dcp.mapper.ChangelogMapper;
import com.dcp.service.IChangelogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Changelog服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class ChangelogServiceImpl extends ServiceImpl<ChangelogMapper, SysChangelog> implements IChangelogService {

    @Override
    public Page<SysChangelog> pageChangelog(ChangelogQueryDTO query) {
        log.info("[分页查询发布日志管理] 查询参数: {}", query);
        Page<SysChangelog> page = new Page<>(query.getCurrent(), query.getSize());
        LambdaQueryWrapper<SysChangelog> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(query.getVersion())) {
            queryWrapper.eq(SysChangelog::getVersion, query.getVersion());
        }
        if (StringUtils.hasText(query.getType())) {
            queryWrapper.eq(SysChangelog::getType, query.getType());
        }
        if (query.getPublisherId() != null) {
            queryWrapper.eq(SysChangelog::getPublisherId, query.getPublisherId());
        }
        if (StringUtils.hasText(query.getKeyword())) {
            queryWrapper.and(wrapper -> wrapper
                .like(SysChangelog::getTitle, query.getKeyword())
                .or()
                .like(SysChangelog::getContent, query.getKeyword())
            );
        }
        queryWrapper.orderByDesc(SysChangelog::getPublishDate);
        page = this.page(page, queryWrapper);
        log.info("[分页查询发布日志管理] 成功，共 {} 条", page.getTotal());
        return page;
    }
}
