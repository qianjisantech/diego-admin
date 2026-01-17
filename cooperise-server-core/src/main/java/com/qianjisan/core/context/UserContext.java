package com.qianjisan.core.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户上下文信�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserContext {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户�?
     */
    private String username;

    /**
     * 用戶編碼
     */
    private String userCode;
}
