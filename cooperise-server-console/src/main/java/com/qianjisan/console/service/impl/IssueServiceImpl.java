package com.qianjisan.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianjisan.console.vo.IssueVO;
import com.qianjisan.console.vo.IssuePageVO;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContext;
import com.qianjisan.core.context.UserContextHolder;

import com.qianjisan.core.utils.IssueNoGenerator;
import com.qianjisan.console.request.IssueRequest;
import com.qianjisan.console.request.IssueQueryRequest;

import com.qianjisan.console.entity.Issue;
import com.qianjisan.console.service.IIssueService;
import com.qianjisan.console.mapper.IssueMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 事项服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Service
@RequiredArgsConstructor
public class IssueServiceImpl extends ServiceImpl<IssueMapper, Issue> implements IIssueService {

    // TODO: 需要实现企业服务和企业成员服务
    // private final ISpaceService spaceService;
    // private final ISpaceMemberService spaceMemberService;

    /**
     * 创建事项
     * @param request 事项信息
     */
    @Override
    public void createIssue(IssueRequest request) {
        Issue workspaceIssue = new Issue();

        // 1. 处理企业信息
        if (request.getSpace() != null) {
            IssueRequest.SpaceInfo space = request.getSpace();
            workspaceIssue.setCompanyId(space.getSpaceId());
            workspaceIssue.setCompanyName(space.getSpaceName());
            workspaceIssue.setCompanyCode(space.getSpaceKeyword());

            // 自动生成事项单号
            if (StringUtils.isEmpty(request.getIssueNo())) {
                String issueNo = generateIssueNo(space);
                workspaceIssue.setIssueNo(issueNo);
            } else {
                workspaceIssue.setIssueNo(request.getIssueNo());
            }
        } else {
            // 没有企业信息时，使用默认方式生成事项单号
            if (StringUtils.isEmpty(request.getIssueNo())) {
                String issueNo = generateIssueNo(null);
                workspaceIssue.setIssueNo(issueNo);
            } else {
                workspaceIssue.setIssueNo(request.getIssueNo());
            }
        }

        // 2. 基本字段
        workspaceIssue.setIssueType(request.getIssueType());
        workspaceIssue.setSummary(request.getSummary());
        workspaceIssue.setDescription(request.getDescription());
        workspaceIssue.setStatus(request.getStatus());
        workspaceIssue.setPriority(request.getPriority());

        // 3. 处理经办人信息（必填）
        workspaceIssue.setAssigneeId(request.getAssignee().getAssigneeId());
        workspaceIssue.setAssigneeName(request.getAssignee().getAssigneeName());
        workspaceIssue.setAssigneeCode(request.getAssignee().getAssigneeCode());

        // 4. 处理报告人信息
        if (request.getReporter() == null || request.getReporter().getReporterId() == null) {
            // 从拦截器获取当前用户的信息存入报告人
            UserContext userContext = UserContextHolder.getUser();
            if (ObjectUtils.isNotEmpty(userContext)) {
                workspaceIssue.setReporterId(userContext.getUserId());
                workspaceIssue.setReporterName(userContext.getUserCode());
                workspaceIssue.setReporterCode(userContext.getUsername());
            }
        } else {
            // 使用请求中传入的报告人信息
            workspaceIssue.setReporterId(request.getReporter().getReporterId());
            workspaceIssue.setReporterName(request.getReporter().getReporterName());
            workspaceIssue.setReporterCode(request.getReporter().getReporterCode());
        }

        // 5. 时间相关字段
        workspaceIssue.setStartDate(request.getStartDate());
        workspaceIssue.setDueDate(request.getDueDate());

        // 6. 工时和进度
        workspaceIssue.setEstimatedHours(request.getEstimatedHours());
        workspaceIssue.setActualHours(request.getActualHours());
        workspaceIssue.setProgress(request.getProgress());

        // 7. 其他字段
        workspaceIssue.setParentId(request.getParentId());
        workspaceIssue.setTags(StringUtils.isNotEmpty(request.getTags()) ? request.getTags() : null);
        workspaceIssue.setSchedule(StringUtils.isNotEmpty(request.getSchedule()) ? request.getSchedule() : null);

        // 8. 保存到数据库
        save(workspaceIssue);
    }

    @Override
    public void updateIssue(Long id, IssueRequest request) {
        Issue workspaceIssue = new Issue();
        workspaceIssue.setId(id);

        // 1. 处理企业信息
        if (request.getSpace() != null) {
            IssueRequest.SpaceInfo space = request.getSpace();
            workspaceIssue.setCompanyId(space.getSpaceId());
            workspaceIssue.setCompanyName(space.getSpaceName());
            workspaceIssue.setCompanyCode(space.getSpaceKeyword());

            // 自动生成事项单号
            if (StringUtils.isEmpty(request.getIssueNo())) {
                String issueNo = generateIssueNo(space);
                workspaceIssue.setIssueNo(issueNo);
            } else {
                workspaceIssue.setIssueNo(request.getIssueNo());
            }
        } else {
            // 没有企业信息时，使用默认方式生成事项单号
            if (StringUtils.isEmpty(request.getIssueNo())) {
                String issueNo = generateIssueNo(null);
                workspaceIssue.setIssueNo(issueNo);
            } else {
                workspaceIssue.setIssueNo(request.getIssueNo());
            }
        }

        // 2. 基本字段
        workspaceIssue.setIssueType(request.getIssueType());
        workspaceIssue.setSummary(request.getSummary());
        workspaceIssue.setDescription(request.getDescription());
        workspaceIssue.setStatus(request.getStatus());
        workspaceIssue.setPriority(request.getPriority());

        // 3. 处理经办人信息（必填）
        workspaceIssue.setAssigneeId(request.getAssignee().getAssigneeId());
        workspaceIssue.setAssigneeName(request.getAssignee().getAssigneeName());
        workspaceIssue.setAssigneeCode(request.getAssignee().getAssigneeCode());

        // 4. 处理报告人信息
        if (request.getReporter() != null && request.getReporter().getReporterId() != null) {
            // 使用请求中传入的报告人信息
            workspaceIssue.setReporterId(request.getReporter().getReporterId());
            workspaceIssue.setReporterName(request.getReporter().getReporterName());
            workspaceIssue.setReporterCode(request.getReporter().getReporterCode());
        }

        // 5. 时间相关字段
        workspaceIssue.setStartDate(request.getStartDate());
        workspaceIssue.setDueDate(request.getDueDate());

        // 6. 工时和进度
        workspaceIssue.setEstimatedHours(request.getEstimatedHours());
        workspaceIssue.setActualHours(request.getActualHours());
        workspaceIssue.setProgress(request.getProgress());

        // 7. 其他字段
        workspaceIssue.setParentId(request.getParentId());
        workspaceIssue.setTags(StringUtils.isNotEmpty(request.getTags()) ? request.getTags() : null);
        workspaceIssue.setSchedule(StringUtils.isNotEmpty(request.getSchedule()) ? request.getSchedule() : null);

        // 8. 保存到数据库
        updateById(workspaceIssue);
    }

    @Override
    public IssueVO getIssueDetailById(Long id) {
        // 1. 查询事项
        Issue issue = getById(id);
        if (issue == null) {
            return null;
        }

        // 2. 数据权限校验: 检查用户是否有权访问该事项所在的企业
        // TODO: 需要实现企业权限校验
        // UserContext userContext = UserContextHolder.getUser();
        // if (userContext != null && userContext.getUserId() != null && issue.getSpaceId() != null) {
        //     spaceMemberService.checkSpacePermission(issue.getSpaceId(), userContext.getUserId());
        // }

        // 3. 创建详情VO对象
        IssueVO detailVO = new IssueVO();

        // 4. 复制基本字段
        BeanUtils.copyProperties(issue, detailVO);

        // 5. 添加企业名称（如果数据库中没有冗余存储）
        // TODO: 如果数据库中没有冗余存储企业名称，需要从企业服务获取
        // if (issue.getSpaceId() != null && StringUtils.isBlank(detailVO.getSpaceName())) {
        //     Space space = spaceService.getById(issue.getSpaceId());
        //     if (space != null) {
        //         detailVO.setSpaceName(space.getName());
        //     }
        // }

        // 6. 这里可以添加子任务、评论、附件、活动记录等扩展信息
        // TODO: 如果需要返回子任务、评论等信息，可以在这里查询并设置
        // detailVO.setSubtasks(subtaskService.listByIssueId(id));
        // detailVO.setComments(commentService.listByIssueId(id));
        // detailVO.setAttachments(attachmentService.listByIssueId(id));
        // detailVO.setActivities(activityService.listByIssueId(id));

        return detailVO;
    }

    @Override
    public PageVO<IssuePageVO> pageQuery(IssueQueryRequest request) {
        // 1. 创建分页对象
        Page<Issue> page = new Page<>(request.getCurrent(), request.getSize());

        // 2. 构建查询条件
        LambdaQueryWrapper<Issue> queryWrapper = buildQueryWrapper(request);

        // 3. 执行分页查询
        Page<Issue> entityPage = page(page, queryWrapper);

        // 4. 转换 Entity 到 VO
        List<IssuePageVO> voList = entityPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        // 5. 构建 PageVO
        PageVO<IssuePageVO> pageVO = new PageVO<>();
        pageVO.setRecords(voList);
        pageVO.setTotal(entityPage.getTotal());
        pageVO.setSize(entityPage.getSize());
        pageVO.setCurrent(entityPage.getCurrent());
        pageVO.setPages(entityPage.getPages());
        return pageVO;
    }

    @Override
    public boolean deleteIssue(Long id) {
        // 这里可以添加删除前的业务逻辑
        // 例如：检查是否有子任务、是否可以删除等

        return removeById(id);
    }

    /**
     * 生成事项单号
     *
     * @param space 事项对象
     */
    private String generateIssueNo(IssueRequest.SpaceInfo space) {

        if (ObjectUtils.isNotEmpty(space)) {
            return IssueNoGenerator.generateWithKeyword(space.getSpaceKeyword());
        }else {
            return  IssueNoGenerator.generateWithKeyword("DCP");
        }

    }

    /**
     * 构建查询条件
     *
     * @param request 查询DTO
     * @return 查询条件包装器
     */
    private LambdaQueryWrapper<Issue> buildQueryWrapper(IssueQueryRequest request) {
        LambdaQueryWrapper<Issue> queryWrapper = new LambdaQueryWrapper<>();

        // 数据权限过滤: 只查询用户有权访问的企业的事项
        // TODO: 需要实现企业权限过滤
        // UserContext userContext = UserContextHolder.getUser();
        // if (userContext != null && userContext.getUserId() != null) {
        //     // 获取用户所在的所有企业ID
        //     List<Long> userSpaceIds = spaceMemberService.getUserSpaceIds(userContext.getUserId());
        //
        //     if (userSpaceIds != null && !userSpaceIds.isEmpty()) {
        //         // 只查询用户所在企业的事项
        //         queryWrapper.in(Issue::getSpaceId, userSpaceIds);
        //     } else {
        //         // 用户不在任何企业中,返回空结果
        //         queryWrapper.eq(Issue::getId, -1L);
        //     }
        // }

        // 事项单号（精确匹配）
        if (StringUtils.isNotBlank(request.getIssueNo())) {
            queryWrapper.eq(Issue::getIssueNo, request.getIssueNo());
        }

        // 标题（模糊查询）
        if (StringUtils.isNotBlank(request.getSummary())) {
            queryWrapper.like(Issue::getSummary, request.getSummary());
        }

        // 默认按创建时间倒序
        queryWrapper.orderByDesc(Issue::getCreateTime);

        return queryWrapper;
    }

    /**
     * 将 Entity 转换为 PageVO
     *
     * @param entity 实体对象
     * @return PageVO对象
     */
    private IssuePageVO convertToVO(Issue entity) {
        IssuePageVO vo = new IssuePageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * 搜索事项（支持事项单号和标题搜索）
     * 用于顶部搜索框的自动提示
     *
     * @param keyword 搜索关键词
     * @return 搜索结果列表（包含id, issueNo, summary）
     */
    @Override
    public List<Map<String, Object>> searchIssues(String keyword) {
        LambdaQueryWrapper<Issue> wrapper = new LambdaQueryWrapper<>();

        // 数据权限过滤: 只搜索用户有权访问的企业的事项
        // TODO: 需要实现企业权限过滤
        // UserContext userContext = UserContextHolder.getUser();
        // if (userContext != null && userContext.getUserId() != null) {
        //     // 获取用户所在的所有企业ID
        //     List<Long> userSpaceIds = spaceMemberService.getUserSpaceIds(userContext.getUserId());
        //
        //     if (userSpaceIds != null && !userSpaceIds.isEmpty()) {
        //         // 只搜索用户所在企业的事项
        //         wrapper.in(Issue::getSpaceId, userSpaceIds);
        //     } else {
        //         // 用户不在任何企业中,返回空结果
        //         return List.of();
        //     }
        // }

        // 支持事项单号或标题的模糊搜索
        wrapper.and(w -> w.like(Issue::getIssueNo, keyword)
                          .or()
                          .like(Issue::getSummary, keyword));

        // 只返回未删除的
        wrapper.eq(Issue::getIsDeleted, 0);

        // 按创建时间倒序
        wrapper.orderByDesc(Issue::getCreateTime);

        // 限制返回10条
        wrapper.last("LIMIT 10");

        List<Issue> issues = baseMapper.selectList(wrapper);

        // 转换为简化的Map格式
        return issues.stream().map(issue -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", issue.getId());
            map.put("issueNo", issue.getIssueNo());
            map.put("summary", issue.getSummary());
            map.put("status", issue.getStatus());
            map.put("priority", issue.getPriority());
            return map;
        }).collect(Collectors.toList());
    }
}
