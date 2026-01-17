package com.qianjisan.system.request;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.util.List;

/**
 * 分配权限请求DTO
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
public class AssignPermissionRequest {
    /**
     * 菜单ID列表（可以为空数组，表示取消所有权限）
     */
    @NotNull(message = "菜单ID列表不能为null")
    private List<Long> menuIds;
}
