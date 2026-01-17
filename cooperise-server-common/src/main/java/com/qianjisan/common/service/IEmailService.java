package com.qianjisan.common.service;

/**
 * 邮件服务接口
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public interface IEmailService {

    /**
     * 发送简单文本邮�?
     *
     * @param to      收件人邮�?
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML格式邮件
     *
     * @param to      收件人邮�?
     * @param subject 邮件主题
     * @param content HTML格式的邮件内�?
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送验证码邮件
     *
     * @param to   收件人邮�?
     * @param code 验证�?
     */
    void sendVerificationCode(String to, String code);
}
