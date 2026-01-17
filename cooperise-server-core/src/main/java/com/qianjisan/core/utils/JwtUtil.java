package com.qianjisan.core.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具�?
 * 用于生成和解析JWT token
 *
 * @author DCP Team
 * @since 2024-12-20
 */
public class JwtUtil {

    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * JWT密钥（生产环境应该从配置文件读取，并保证足够长度�?
     */
    private static final String SECRET_KEY = "dcp-admin-secret-key-for-jwt-token-generation-must-be-long-enough";

    /**
     * token过期时间�?天，单位：毫秒）
     */
    private static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 用户ID claim key
     */
    private static final String CLAIM_USER_ID = "userId";

    /**
     * 用户�?claim key
     */
    private static final String CLAIM_USERNAME = "username";

    /**
     * 昵称 claim key
     */
    private static final String CLAIM_NICKNAME = "nickname";

    /**
     * 获取密钥
     */
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成JWT token
     *
     * @param userId   用户ID
     * @param username 用户�?
     * @param nickname 昵称
     * @return JWT token
     */
    public static String generateToken(Long userId, String username, String nickname) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_USER_ID, userId);
        claims.put(CLAIM_USERNAME, username);
        claims.put(CLAIM_NICKNAME, nickname);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 从token中解析Claims
     *
     * @param token JWT token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析JWT token失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 从token中获取用户ID
     *
     * @param token JWT token
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        Object userId = claims.get(CLAIM_USER_ID);
        if (userId instanceof Integer) {
            return ((Integer) userId).longValue();
        }
        return (Long) userId;
    }

    /**
     * 从token中获取用户名
     *
     * @param token JWT token
     * @return 用户�?
     */
    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        return claims != null ? (String) claims.get(CLAIM_USERNAME) : null;
    }

    /**
     * 从token中获取昵�?
     *
     * @param token JWT token
     * @return 昵称
     */
    public static String getNickname(String token) {
        Claims claims = parseToken(token);
        return claims != null ? (String) claims.get(CLAIM_NICKNAME) : null;
    }

    /**
     * 验证token是否过期
     *
     * @param token JWT token
     * @return true-未过期，false-已过�?
     */
    public static boolean isTokenValid(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return false;
            }
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            log.error("验证JWT token失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 刷新token
     *
     * @param token 原token
     * @return 新token
     */
    public static String refreshToken(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }

        Long userId = getUserId(token);
        String username = getUsername(token);
        String nickname = getNickname(token);

        return generateToken(userId, username, nickname);
    }
}
