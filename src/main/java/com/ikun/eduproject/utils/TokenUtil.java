package com.ikun.eduproject.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @Author zzhay
 * @Date 2023/8/5/005
 */
public class TokenUtil {
    // 密钥
    private static final String SECRET_KEY = "ikun-edu";
    // Token有效期，这里设置为1天（单位：毫秒）
    private static final long EXPIRATION_TIME = 86400000;

    //生成token
    public static String generateToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date now = new Date();

            // 检查Token是否过期
            return expirationDate.before(now);
        } catch (Exception e) {
            // 解析Token失败，可能是无效的Token
            return true;
        }
    }
}
