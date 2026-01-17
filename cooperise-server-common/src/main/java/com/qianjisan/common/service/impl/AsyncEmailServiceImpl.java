package com.qianjisan.common.service.impl;

import com.qianjisan.common.service.IAsyncEmailService;
import com.qianjisan.common.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 异步邮件服务实现�?
 *
 * 注意：此类专门用于异步邮件发送，与AuthServiceImpl分离�?
 * 确保@Async注解能够正常工作（避免同类调用导致的代理失效问题�?
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncEmailServiceImpl implements IAsyncEmailService {

    private final IEmailService emailService;

    /**
     * 异步发送验证码邮件
     *
     * @Async 注解使此方法在单独的线程中执行，不会阻塞主线�?
     */
    @Async
    @Override
    public void sendVerificationCodeAsync(String email, String code) {
        try {
            log.info("[AsyncEmailService] 开始异步发送验证码邮件，邮�? {}", email);
            emailService.sendVerificationCode(email, code);
            log.info("[AsyncEmailService] 异步发送验证码邮件成功，邮�? {}", email);
            log.info("[AsyncEmailService] 异步发送验证码邮件成功，验证码�? {}", code);
        } catch (Exception e) {
            log.error("[AsyncEmailService] 异步发送验证码邮件失败，邮�? {}, 错误: {}", email, e.getMessage(), e);
            // 异步方法中的异常不会影响主流程，只记录日志即�?
        }
    }

    /**
     * 异步发送反馈状态变更通知邮件
     *
     * @Async 注解使此方法在单独的线程中执行，不会阻塞主线�?
     */
    @Async
    @Override
    public void sendFeedbackStatusChangeNotificationAsync(String email, Long feedbackId, String title, Integer oldStatus, Integer newStatus) {
        try {
            log.info("[AsyncEmailService] 开始异步发送反馈状态变更通知邮件，邮�? {}, 反馈ID: {}", email, feedbackId);
            String subject = "【DCP需求管理平台】反馈状态已更新";
            String content = buildFeedbackStatusChangeHtml(feedbackId, title, oldStatus, newStatus);
            emailService.sendHtmlMail(email, subject, content);
            log.info("[AsyncEmailService] 异步发送反馈状态变更通知邮件成功，邮�? {}, 反馈ID: {}", email, feedbackId);
        } catch (Exception e) {
            log.error("[AsyncEmailService] 异步发送反馈状态变更通知邮件失败，邮�? {}, 反馈ID: {}, 错误: {}", email, feedbackId, e.getMessage(), e);
            // 异步方法中的异常不会影响主流程，只记录日志即�?
        }
    }

    /**
     * 构建反馈状态变更邮件HTML内容
     */
    private String buildFeedbackStatusChangeHtml(Long feedbackId, String title, Integer oldStatus, Integer newStatus) {
        String oldStatusText = getStatusText(oldStatus);
        String newStatusText = getStatusText(newStatus);
        String statusColor = getStatusColor(newStatus);

        return "<!DOCTYPE html>" +
                "<html lang='zh-CN'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>反馈状态更�?/title>" +
                "</head>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5;'>" +
                "    <div style='max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>" +
                "        <!-- Header -->" +
                "        <div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; text-align: center;'>" +
                "            <h1 style='color: #ffffff; margin: 0; font-size: 24px; font-weight: 600;'>DCP 需求管理平�?/h1>" +
                "            <p style='color: rgba(255,255,255,0.9); margin: 8px 0 0 0; font-size: 14px;'>Demand Control Platform</p>" +
                "        </div>" +
                "        " +
                "        <!-- Content -->" +
                "        <div style='padding: 40px 30px;'>" +
                "            <h2 style='color: #333333; font-size: 20px; margin: 0 0 20px 0;'>您的反馈状态已更新</h2>" +
                "            <p style='color: #666666; font-size: 14px; line-height: 1.6; margin: 0 0 30px 0;'>" +
                "                您提交的反馈状态已发生变化，详情如下：" +
                "            </p>" +
                "            " +
                "            <!-- Feedback Info -->" +
                "            <div style='background-color: #f8f9fa; border-radius: 8px; padding: 20px; margin: 20px 0;'>" +
                "                <p style='margin: 0 0 10px 0; color: #333333; font-size: 14px;'><strong>反馈ID�?/strong>#" + feedbackId + "</p>" +
                "                <p style='margin: 0 0 10px 0; color: #333333; font-size: 14px;'><strong>反馈标题�?/strong>" + title + "</p>" +
                "            </div>" +
                "            " +
                "            <!-- Status Change -->" +
                "            <div style='margin: 30px 0;'>" +
                "                <p style='color: #666666; font-size: 14px; margin: 0 0 15px 0;'>状态变更：</p>" +
                "                <div style='display: flex; align-items: center; gap: 15px;'>" +
                "                    <span style='background-color: #e9ecef; color: #495057; padding: 8px 16px; border-radius: 4px; font-size: 14px;'>" + oldStatusText + "</span>" +
                "                    <span style='color: #999999; font-size: 18px;'>�?/span>" +
                "                    <span style='background-color: " + statusColor + "; color: #ffffff; padding: 8px 16px; border-radius: 4px; font-size: 14px; font-weight: 600;'>" + newStatusText + "</span>" +
                "                </div>" +
                "            </div>" +
                "            " +
                "            <div style='background-color: #e7f3ff; border-left: 4px solid #2196F3; padding: 15px; margin: 30px 0; border-radius: 4px;'>" +
                "                <p style='color: #0c5460; font-size: 13px; margin: 0; line-height: 1.6;'>" +
                "                    <strong>提示�?/strong><br>" +
                "                    �?您可以登录系统查看反馈的详细信息<br>" +
                "                    �?如有疑问，请联系系统管理�? +
                "                </p>" +
                "            </div>" +
                "        </div>" +
                "        " +
                "        <!-- Footer -->" +
                "        <div style='background-color: #f8f9fa; padding: 20px 30px; text-align: center; border-top: 1px solid #e9ecef;'>" +
                "            <p style='color: #999999; font-size: 12px; margin: 0; line-height: 1.6;'>" +
                "                此邮件由系统自动发送，请勿直接回复<br>" +
                "                © 2024 DCP Team. All rights reserved." +
                "            </p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    /**
     * 获取状态文�?
     */
    private String getStatusText(Integer status) {
        if (status == null) {
            return "未知";
        }
        // 根据实际的状态值定义，这里假设�?-open, 2-closed
        switch (status) {
            case 1:
                return "待处�?;
            case 2:
                return "已关�?;
            default:
                return "未知";
        }
    }

    /**
     * 获取状态颜�?
     */
    private String getStatusColor(Integer status) {
        if (status == null) {
            return "#6c757d";
        }
        // 根据实际的状态值定义颜�?
        switch (status) {
            case 1:
                return "#28a745"; // 绿色 - 待处�?
            case 2:
                return "#6c757d"; // 灰色 - 已关�?
            default:
                return "#6c757d";
        }
    }
}
