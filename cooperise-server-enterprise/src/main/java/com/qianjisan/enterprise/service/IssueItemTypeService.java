package com.qianjisan.enterprise.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qianjisan.core.PageVO;
import com.qianjisan.enterprise.entity.IssueItemType;
import com.qianjisan.enterprise.request.IssueItemTypeQueryRequest;
import com.qianjisan.enterprise.request.IssueItemTypeRequest;
import com.qianjisan.enterprise.vo.IssueItemTypeVo;

public interface IssueItemTypeService extends IService<IssueItemType> {

    boolean createIssueItemType(IssueItemTypeRequest request);

    boolean updateIssueItemType(Long id, IssueItemTypeRequest request);

    boolean deleteIssueItemType(Long id);

    IssueItemTypeVo getIssueItemTypeById(Long id);

    PageVO<IssueItemTypeVo> getIssueItemTypePage(IssueItemTypeQueryRequest request);

    IssueItemType convertToEntity(IssueItemTypeRequest request);

    IssueItemTypeVo convertToVo(IssueItemType entity);
}
