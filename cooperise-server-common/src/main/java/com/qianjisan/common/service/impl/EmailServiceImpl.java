package com.qianjisan.common.service.impl;

import com.qianjisan.common.service.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

/**
 * 邮件服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            log.info("简单邮件发送成功，收件人: {}, 主题: {}", to, subject);
        } catch (Exception e) {
            log.error("简单邮件发送失败，收件人: {}, 主题: {}, 错误信息: {}", to, subject, e.getMessage(), e);
            throw new RuntimeException("邮件发送失败: " + e.getMessage());
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
            log.info("HTML邮件发送成功，收件人: {}, 主题: {}", to, subject);
        } catch (MessagingException e) {
            log.error("HTML邮件发送失败，收件人: {}, 主题: {}, 错误信息: {}", to, subject, e.getMessage(), e);
            throw new RuntimeException("邮件发送失败: " + e.getMessage());
        }
    }

    @Override
    public void sendVerificationCode(String to, String code) {
        String subject = "【DCP需求管理平台】验证码";
        String content = buildVerificationCodeHtml(code);
        sendHtmlMail(to, subject, content);
    }

    /**
     * 构建验证码邮件HTML内容
     */
    private String buildVerificationCodeHtml(String code) {
        return "<!DOCTYPE html>" +
                "<html lang='zh-CN'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>验证码</title>" +
                "</head>" +
                "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f5f5f5;'>" +
                "    <div style='max-width: 600px; margin: 40px auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 8px rgba(0,0,0,0.1);'>" +
                "        <!-- Header -->" +
                "        <div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; text-align: center;'>" +
                "            <h1 style='color: #ffffff; margin: 0; font-size: 24px; font-weight: 600;'>DCP 需求管理平台</h1>" +
                "            <p style='color: rgba(255,255,255,0.9); margin: 8px 0 0 0; font-size: 14px;'>Demand Control Platform</p>" +
                "        </div>" +
                "        " +
                "        <!-- Content -->" +
                "        <div style='padding: 40px 30px;'>" +
                "            <h2 style='color: #333333; font-size: 20px; margin: 0 0 20px 0;'>您的验证码</h2>" +
                "            <p style='color: #666666; font-size: 14px; line-height: 1.6; margin: 0 0 30px 0;'>" +
                "                您正在注册 DCP 需求管理平台账号，验证码如下：" +
                "            </p>" +
                "            " +
                "            <!-- Verification Code Box -->" +
                "            <div style='background-color: #f8f9fa; border: 2px dashed #667eea; border-radius: 8px; padding: 30px; text-align: center; margin: 30px 0;'>" +
                "                <div style='font-size: 36px; font-weight: 700; color: #667eea; letter-spacing: 8px; font-family: \"Courier New\", monospace;'>" +
                "                    " + code +
                "                </div>" +
                "            </div>" +
                "            " +
                "            <div style='background-color: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 30px 0; border-radius: 4px;'>" +
                "                <p style='color: #856404; font-size: 13px; margin: 0; line-height: 1.6;'>" +
                "                    <strong>安全提示：</strong><br>" +
                "                    • 验证码有效期为 <strong>5分钟</strong><br>" +
                "                    • 请勿将验证码透露给他人<br>" +
                "                    • 如非本人操作，请忽略此邮件" +
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
}
