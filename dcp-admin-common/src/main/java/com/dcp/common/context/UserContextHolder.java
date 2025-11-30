package com.dcp.common.context;

/**
 * 用户上下文持有者
 * 使用ThreadLocal存储当前请求的用户信息
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class UserContextHolder {

    private static final ThreadLocal<UserContext> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置当前用户信息
     *
     * @param userContext 用户上下文
     */
    public static void setUser(UserContext userContext) {
        CONTEXT_HOLDER.set(userContext);
    }

    /**
     * 设置当前用户信息
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param userCode
     */
    public static void setUser(Long userId, String username, String userCode) {
        UserContext userContext = new UserContext(userId, username, userCode);
        CONTEXT_HOLDER.set(userContext);
    }

    /**
     * 获取当前用户信息
     *
     * @return 用户上下文
     */
    public static UserContext getUser() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        UserContext userContext = CONTEXT_HOLDER.get();
        return userContext != null ? userContext.getUserId() : null;
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        UserContext userContext = CONTEXT_HOLDER.get();
        return userContext != null ? userContext.getUsername() : null;
    }

    /**
     * 获取当前用户昵称
     *
     * @return 昵称
     */
    public static String getUserCode() {
        UserContext userContext = CONTEXT_HOLDER.get();
        return userContext != null ? userContext.getUserCode() : null;
    }

    /**
     * 清除当前用户信息
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
