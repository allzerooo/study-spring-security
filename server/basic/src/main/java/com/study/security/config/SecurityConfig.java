package com.study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)  // pre post로 권한 체크를 하겠다 -> 권한 체크 모듈이 작동됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // user 만들기 - 추가하면 yml에 등록한 유저는 먹히지 않음
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(User.builder()
                            .username("user2")
                            .password(passwordEncoder().encode("2222"))   // 패스워드를 인코딩하지 않으면 에러가 난다
                            .roles("USER"))
                .withUser(User.builder()
                            .username("admin")
                            .password(passwordEncoder().encode("3333"))
                            .roles("ADMIN"));
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티는 기본적으로 모든 페이지를 다 막아둔다
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) ->
//                requests.anyRequest().authenticated() // 모든 페이지를 다 인증해라
                requests.antMatchers("/").permitAll()   // / 주소는 사용자에게 다 접근을 허락해라
                        .anyRequest().authenticated()
        );
        http.formLogin();
        http.httpBasic();
    }
}
