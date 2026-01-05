package com.qianjisan.core.context;

import com.qianjisan.core.exception.BusinessException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户上下文持有者
 * 使用ThreadLocal存储当前请求的用户信息
 *
 * @author DCP Team
 * @since 2024-12-20
 */

public class UserContextHolder {

    private static final ThreadLocal<UserContext> CONTEXT_HOLDER = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(UserContextHolder.class);

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
        UserContext userContext = CONTEXT_HOLDER.get();
        System.out.println("获取当前用户信息:"+ userContext);
        if (userContext==null) {
            throw new BusinessException("未登录或登录已过期");
        }
        return userContext;
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        UserContext userContext = getUser();
        if (userContext.getUserId()==null){
            throw new BusinessException("未登录或登录已过期");
        }
        return  userContext.getUserId();
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    public static String getUsername() {
        UserContext userContext = CONTEXT_HOLDER.get();

        if (StringUtils.isBlank(userContext.getUsername())){
            throw new BusinessException("未登录或登录已过期");
        }
        return userContext.getUsername();
    }

    /**
     * 获取当前用户昵称
     *
     * @return 昵称
     */
    public static String getUserCode() {
        UserContext userContext = CONTEXT_HOLDER.get();

        if (StringUtils.isBlank(userContext.getUserCode())){
            throw new BusinessException("未登录或登录已过期");
        }
        return userContext.getUserCode() ;
    }

    /**
     * 清除当前用户信息
     */
    public static void clear() {
        CONTEXT_HOLDER.remove();
    }
}
