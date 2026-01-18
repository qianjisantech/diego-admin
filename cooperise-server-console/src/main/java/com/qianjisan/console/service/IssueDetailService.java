package com.qianjisan.console.service;

import com.qianjisan.core.PageVO;
import com.qianjisan.console.entity.IssueDetail;
import com.qianjisan.console.request.IssueDetailQueryRequest;
import com.qianjisan.console.request.IssueDetailRequest;
import com.qianjisan.console.vo.IssueVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 事项主表 服务类
 * </p>
 *
 * @author cooperise
 * @since 2026-01-17
 */
public interface IssueDetailService extends IService<IssueDetail> {

    /**
     * 创建事项详情
     */
    void createIssueDetail(IssueDetailRequest request);

    /**
     * 更新事项详情
     */
    void updateIssueDetail(Long id, IssueDetailRequest request);

    /**
     * 删除事项详情
     */
    void deleteIssueDetail(Long id);

    /**
     * 根据ID查询事项详情
     */
    IssueVO getIssueDetailById(Long id);

    /**
     * 分页查询事项详情
     */
    PageVO<IssueVO> getIssueDetailPage(IssueDetailQueryRequest request);

    /**
     * 根据企业ID查询事项详情列表
     */
    List<IssueVO> getIssueDetailsByCompanyId(Long companyId);

    /**
     * 根据模板字段ID查询事项详情列表
     */
    List<IssueVO> getIssueDetailsByTemplateFieldId(Long templateFieldId);
}
