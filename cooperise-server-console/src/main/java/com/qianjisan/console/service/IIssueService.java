package com.qianjisan.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.console.vo.IssueDetailVO;
import com.qianjisan.console.vo.IssuePageVO;
import com.qianjisan.core.PageVO;
import com.qianjisan.console.entity.Issue;

import com.qianjisan.console.request.IssueQueryRequest;
import com.qianjisan.console.request.WorkSpaceIssueRequest;

import java.util.List;
import java.util.Map;

/**
 * 事项服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IIssueService extends IService<Issue> {

    /**
     * 创建事项（包含业务逻辑�?
     * - 自动生成事项单号
     * - 设置默认报告�?
     * - 设置默认经办�?
     * - 设置默认状�?
     *
     * @param request 事项信息
     * @return 创建后的事项
     */
    void createIssue(WorkSpaceIssueRequest request);

    /**
     * 更新事项（包含业务逻辑�?
     *
     * @param id    事项ID
     * @param request 更新的事项信�?
     * @return 更新后的事项
     */
    void updateIssue(Long id, WorkSpaceIssueRequest request);

    /**
     * 根据ID查询事项详情（包含扩展字段）
     * - 包含空间名称
     * - 包含经办人、报告人姓名
     *
     * @param id 事项ID
     * @return 事项详情VO（包含扩展字段）
     */
    IssueDetailVO getIssueDetailById(Long id);

    /**
     * 分页查询事项（包含复杂查询条件）
     *
     * @param request 查询条件
     * @return 分页结果
     */
    PageVO<IssuePageVO> pageQuery(IssueQueryRequest request);

    /**
     * 删除事项（包含业务逻辑�?
     *
     * @param id 事项ID
     * @return 是否删除成功
     */
    boolean deleteIssue(Long id);

    /**
     * 搜索事项（支持事项单号和标题搜索�?
     * 用于顶部搜索框的自动提示
     *
     * @param keyword 搜索关键�?
     * @return 搜索结果列表（包含id, issueNo, summary�?
     */
    List<Map<String, Object>> searchIssues(String keyword);
}
