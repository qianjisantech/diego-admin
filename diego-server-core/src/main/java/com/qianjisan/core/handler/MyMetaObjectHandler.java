package com.qianjisan.core.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;


import com.qianjisan.core.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 元数据填充处理器
 * 自动填充创建人、更新人、创建时间、更新时间等字段
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("开始插入填充...");

        // 填充创建时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 填充更新时间
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 填充加入时间（用于 SpaceMember 表）
        this.strictInsertFill(metaObject, "joinTime", LocalDateTime.class, LocalDateTime.now());

        // 从用户上下文获取当前登录用户信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();

        if (userId != null) {
            // 填充创建人ID
            this.strictInsertFill(metaObject, "createById", Long.class, userId);
            // 填充创建人用户名
            this.strictInsertFill(metaObject, "createByCode", String.class, username);
            // 填充创建人昵称
            this.strictInsertFill(metaObject, "createByName", String.class, nickname);

            // 填充更新人ID
            this.strictInsertFill(metaObject, "updateById", Long.class, userId);
            // 填充更新人用户名
            this.strictInsertFill(metaObject, "updateByCode", String.class, username);
            // 填充更新人昵称
            this.strictInsertFill(metaObject, "updateByName", String.class, nickname);

            log.debug("插入填充完成 - 用户ID: {}, 用户名: {}, 昵称: {}", userId, username, nickname);
        } else {
            log.warn("用户上下文为空，无法填充创建人和更新人信息");
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("开始更新填充...");

        // 填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());

        // 从用户上下文获取当前登录用户信息
        Long userId = UserContextHolder.getUserId();
        String username = UserContextHolder.getUsername();
        String nickname = UserContextHolder.getUserCode();

        if (userId != null) {
            // 填充更新人ID
            this.strictUpdateFill(metaObject, "updateById", Long.class, userId);
            // 填充更新人用户名
            this.strictUpdateFill(metaObject, "updateByCode", String.class, username);
            // 填充更新人昵称
            this.strictUpdateFill(metaObject, "updateByName", String.class, nickname);

            log.debug("更新填充完成 - 用户ID: {}, 用户名: {}, 昵称: {}", userId, username, nickname);
        } else {
            // 用户上下文为空（可能是登录前操作），记录调试信息但不警告
            log.debug("用户上下文为空，无法填充更新人信息（可能为登录前操作）");
        }
    }
}
