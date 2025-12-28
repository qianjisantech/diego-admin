package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 分配企业请求
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class AssignEnterpriseRequest {

    /**
     * 企业ID列表
     */
    @NotEmpty(message = "企业ID列表不能为空")
    private List<Long> enterpriseIds;
}






