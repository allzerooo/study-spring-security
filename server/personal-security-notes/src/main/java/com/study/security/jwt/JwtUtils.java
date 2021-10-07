package com.study.security.jwt;

import com.study.security.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import org.springframework.data.util.Pair;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    /**
     * 토큰을 파싱해서 username 찾기
     *
     * @param token 토큰
     * @return username
     */
    public static String getUsername(String token) {
        // jwtToken에서 username을 찾습니다.
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance) // Secret Key를 찾아서 Signature를 검증할 수 있도록 세팅
                .build()
                .parseClaimsJws(token)  // Signature 검증 실패 시 SignatureException 발생, Signature 검증은 했는데 토큰이 만료되었으면 ExpiredJwtException 발생
                .getBody()  // payload
                .getSubject(); // username
    }

    /**
     * user로 JWT 토큰 생성
     * HEADER : alg(알고리즘 종류), kid
     * PAYLOAD : sub, iat(토큰 발행시간), exp(토큰 만료시간)
     * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512(암호화) 해시
     *
     * @param user 유저
     * @return jwt token
     */
    public static String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUsername()); // subject
        Date now = new Date(); // 현재 시간
        Pair<String, Key> key = JwtKey.getRandomKey();  // Kid : Secret Key
        // JWT Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(key.getSecond()) // signature
                .compact();
    }
}
