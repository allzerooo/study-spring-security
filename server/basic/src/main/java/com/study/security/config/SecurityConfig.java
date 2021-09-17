package com.study.security.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  // pre post로 권한 체크를 하겠다 -> 권한 체크 모듈이 작동됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {
}
