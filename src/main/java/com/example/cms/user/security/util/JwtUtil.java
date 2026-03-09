package com.example.cms.user.security.util;

import com.example.cms.user.domain.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.access.expiration}")
    private Duration accessExpiration;

    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    private Key accessKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] accessBytes = Base64.getDecoder().decode(accessSecret);
        accessKey = Keys.hmacShaKeyFor(accessBytes);
    }

    // Access 토큰 생성
    public String createAccessToken(String username, UserRoleEnum role){
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + accessExpiration.toMillis()))
                        .setIssuedAt(date)
                        .signWith(accessKey, signatureAlgorithm)
                        .compact();
    }

    public void addAccessTokenToHeader(String accessToken, HttpServletResponse res){
        res.setHeader(AUTHORIZATION_HEADER, accessToken);
    }

    public String substringToken(String tokenValue){
        if(StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)){
            return tokenValue.substring(BEARER_PREFIX.length());
        }
        throw new NullPointerException("Not Fount Token!");
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromAccessToken(String token){
        return Jwts.parserBuilder().setSigningKey(accessKey).build().parseClaimsJws(token).getBody();
    }

    public String getAccessTokenFromHeader(HttpServletRequest req){
        String token = req.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(token)){
            return token;
        }
        return null;
    }

}
