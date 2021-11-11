package com.study.security.config;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class CustomAuthDetails implements AuthenticationDetailsSource<HttpServletRequest, RequestInfo> {    // 넘겨줄 객체인 RequestInfo는 원하는대로 생성한 것

    @Override
    public RequestInfo buildDetails(HttpServletRequest request) {
        // 로그인이 일어날 때 정보를 가져다가 RequestInfo에 넣어서 보내주기
        return RequestInfo.builder()
                .remoteIp(request.getRemoteAddr())
                .sessionId(request.getSession().getId())
                .loginTime(LocalDateTime.now())
                .build();
    }
}
