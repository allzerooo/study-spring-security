package com.study.security.student;

import org.apache.catalina.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
// 통행증을 발급해줄 provider
public class StudentManager implements AuthenticationProvider, InitializingBean {

    // StudentManager는 Student리스트를 가지고 있어야 된다. 따라서, 원래는 여기에서 DB 조회를 한다
    private HashMap<String, Student> studentDB = new HashMap<>();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 우리가 UsernamePasswordAuthenticationFilter를 통해서 토큰을 받을것이기 때문에
        // UsernamePasswordAuthenticationToken을 받으면 검증해주는 provider로 동작하겠다고 선언
        return authentication == UsernamePasswordAuthenticationToken.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // InitializingBean에 의해 빈이 초기화되었을 때 실핼되는 코드

    }
}
