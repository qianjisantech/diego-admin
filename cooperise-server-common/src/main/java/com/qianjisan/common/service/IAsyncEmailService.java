package com.qianjisan.common.service;

/**
 * 异步邮件服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IAsyncEmailService {

    /**
     * 异步发送验证码邮件
     *
     * @param email 邮箱地址
     * @param code  验证码
     */
    void sendVerificationCodeAsync(String email, String code);

    /**
     * 异步发送反馈状态变更通知邮件
     *
     * @param email      收件人邮箱
     * @param feedbackId 反馈ID
     * @param title      反馈标题
     * @param oldStatus  旧状态
     * @param newStatus  新状态
     */
    void sendFeedbackStatusChangeNotificationAsync(String email, Long feedbackId, String title, Integer oldStatus, Integer newStatus);
}
