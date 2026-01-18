package com.qianjisan.console.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianjisan.core.PageVO;
import com.qianjisan.core.context.UserContextHolder;
import com.qianjisan.console.entity.IssueDetail;
import com.qianjisan.console.mapper.IssueDetailMapper;
import com.qianjisan.console.request.IssueDetailQueryRequest;
import com.qianjisan.console.request.IssueDetailRequest;
import com.qianjisan.console.service.IssueDetailService;
import com.qianjisan.console.vo.IssueVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 事项主表 服务实现类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
@Service
@RequiredArgsConstructor
public class IssueDetailServiceImpl extends ServiceImpl<IssueDetailMapper, IssueDetail> implements IssueDetailService {

    @Override
    public void createIssueDetail(IssueDetailRequest request) {
        IssueDetail issueDetail = BeanUtil.copyProperties(request, IssueDetail.class);
        issueDetail.setCreateTime(LocalDateTime.now());
        issueDetail.setUpdateTime(LocalDateTime.now());

        // 设置创建人信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();
        if (userId != null) {
            issueDetail.setCreateById(userId);
            issueDetail.setCreateByCode(username);
            issueDetail.setCreateByName(nickname);
            issueDetail.setUpdateById(userId);
            issueDetail.setUpdateByCode(username);
            issueDetail.setUpdateByName(nickname);
        }

        this.save(issueDetail);
    }

    @Override
    public void updateIssueDetail(Long id, IssueDetailRequest request) {
        IssueDetail issueDetail = this.getById(id);
        if (issueDetail == null) {
            throw new RuntimeException("事项详情不存在");
        }

        BeanUtil.copyProperties(request, issueDetail);
        issueDetail.setUpdateTime(LocalDateTime.now());

        // 设置更新人信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();
        if (userId != null) {
            issueDetail.setUpdateById(userId);
            issueDetail.setUpdateByCode(username);
            issueDetail.setUpdateByName(nickname);
        }

        this.updateById(issueDetail);
    }

    @Override
    public void deleteIssueDetail(Long id) {
        IssueDetail issueDetail = this.getById(id);
        if (issueDetail == null) {
            throw new RuntimeException("事项详情不存在");
        }

        this.removeById(id);
    }

    @Override
    public IssueVO getIssueDetailById(Long id) {
        IssueDetail issueDetail = this.getById(id);
        if (issueDetail == null) {
            return null;
        }

        IssueVO vo = BeanUtil.copyProperties(issueDetail, IssueVO.class);

        // 查询相关的事项详情列表（根据企业ID查询同一企业的其他事项详情）
        List<IssueVO> relatedIssueDetails = getIssueDetailsByCompanyId(issueDetail.getCompanyId());
        // 过滤掉当前记录本身
        relatedIssueDetails = relatedIssueDetails.stream()
                .filter(item -> !item.getId().equals(id))
                .collect(Collectors.toList());
        vo.setIssueDetailList(relatedIssueDetails);

        return vo;
    }

    @Override
    public PageVO<IssueVO> getIssueDetailPage(IssueDetailQueryRequest request) {
        LambdaQueryWrapper<IssueDetail> queryWrapper = new LambdaQueryWrapper<>();

        // 构建查询条件
        if (request.getCompanyId() != null) {
            queryWrapper.eq(IssueDetail::getCompanyId, request.getCompanyId());
        }
        if (StringUtils.hasText(request.getCompanyCode())) {
            queryWrapper.eq(IssueDetail::getCompanyCode, request.getCompanyCode());
        }
        if (StringUtils.hasText(request.getCompanyName())) {
            queryWrapper.like(IssueDetail::getCompanyName, request.getCompanyName());
        }
        if (request.getTemplateFieldId() != null) {
            queryWrapper.eq(IssueDetail::getTemplateFieldId, request.getTemplateFieldId());
        }
        if (StringUtils.hasText(request.getValueString())) {
            queryWrapper.like(IssueDetail::getValueString, request.getValueString());
        }
        if (StringUtils.hasText(request.getValueJson())) {
            queryWrapper.like(IssueDetail::getValueJson, request.getValueJson());
        }

        // 按创建时间倒序
        queryWrapper.orderByDesc(IssueDetail::getCreateTime);

        // 分页查询
        IPage<IssueDetail> page = this.page(new Page<>(request.getCurrent(), request.getSize()), queryWrapper);

        // 转换为VO
        PageVO<IssueVO> pageVO = new PageVO<>();
        pageVO.setRecords(BeanUtil.copyToList(page.getRecords(), IssueVO.class));
        pageVO.setCurrent(page.getCurrent());
        pageVO.setSize(page.getSize());
        pageVO.setTotal(page.getTotal());
        pageVO.setPages(page.getPages());

        return pageVO;
    }

    @Override
    public List<IssueVO> getIssueDetailsByCompanyId(Long companyId) {
        LambdaQueryWrapper<IssueDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IssueDetail::getCompanyId, companyId)
                .orderByDesc(IssueDetail::getCreateTime);

        List<IssueDetail> issueDetails = this.list(queryWrapper);
        return BeanUtil.copyToList(issueDetails, IssueVO.class);
    }

    @Override
    public List<IssueVO> getIssueDetailsByTemplateFieldId(Long templateFieldId) {
        LambdaQueryWrapper<IssueDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IssueDetail::getTemplateFieldId, templateFieldId)
                .orderByDesc(IssueDetail::getCreateTime);

        List<IssueDetail> issueDetails = this.list(queryWrapper);
        return BeanUtil.copyToList(issueDetails, IssueVO.class);
    }
}
