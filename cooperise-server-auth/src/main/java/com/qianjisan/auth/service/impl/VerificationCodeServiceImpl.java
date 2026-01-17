package com.qianjisan.auth.service.impl;

import com.qianjisan.auth.service.IVerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 验证码服务实现类
 *
 * @author DCP Team
 * @since 2024-12-20
 */
@Slf4j
@Service
public class VerificationCodeServiceImpl implements IVerificationCodeService {

    /**
     * 验证码存储Map
     * key: email
     * value: CodeInfo(验证�? 过期时间)
     */
    private final Map<String, CodeInfo> codeStore = new ConcurrentHashMap<>();

    /**
     * 验证码有效期（分钟）
     */
    private static final int CODE_EXPIRE_MINUTES = 5;

    /**
     * 验证码长�?
     */
    private static final int CODE_LENGTH = 6;

    @Override
    public String generateCode(String email) {
        // 生成6位随机数字验证码
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }

        String verificationCode = code.toString();
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(CODE_EXPIRE_MINUTES);

        // 存储验证�?
        codeStore.put(email, new CodeInfo(verificationCode, expireTime));

        log.info("生成验证码成功，邮箱: {}, 验证�? {}, 过期时间: {}", email, verificationCode, expireTime);

        return verificationCode;
    }

    @Override
    public boolean verifyCode(String email, String code) {
        CodeInfo codeInfo = codeStore.get(email);

        if (codeInfo == null) {
            log.warn("验证码不存在，邮�? {}", email);
            return false;
        }

        // 检查是否过�?
        if (LocalDateTime.now().isAfter(codeInfo.getExpireTime())) {
            log.warn("验证码已过期，邮�? {}", email);
            codeStore.remove(email);
            return false;
        }

        // 验证码比�?
        boolean isValid = codeInfo.getCode().equals(code);
        if (isValid) {
            log.info("验证码验证成功，邮箱: {}", email);
        } else {
            log.warn("验证码错误，邮箱: {}, 输入: {}, 正确: {}", email, code, codeInfo.getCode());
        }

        return isValid;
    }

    @Override
    public void removeCode(String email) {
        codeStore.remove(email);
        log.info("删除验证码，邮箱: {}", email);
    }

    /**
     * 验证码信息内部类
     */
    private static class CodeInfo {
        private final String code;
        private final LocalDateTime expireTime;

        public CodeInfo(String code, LocalDateTime expireTime) {
            this.code = code;
            this.expireTime = expireTime;
        }

        public String getCode() {
            return code;
        }

        public LocalDateTime getExpireTime() {
            return expireTime;
        }
    }
}
