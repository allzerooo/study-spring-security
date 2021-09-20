package com.study.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request->{
                    request
                            .antMatchers("/").permitAll() // 루트 페이지 접근은 모두 허용
                            .anyRequest().authenticated()   // anyRequest에 대해서는 허락을 받고 들어오도록
                            ;
                })
                ;
    }
}
