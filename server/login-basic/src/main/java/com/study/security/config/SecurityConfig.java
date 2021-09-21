package com.study.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                // usernamepassword를 formLogin으로 설정. login 페이지를 정해주지 않으면 DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 가 동작을 하게 된다
                .formLogin(
                        // permitAll을 하지 않으면 anyRequest().authenticated() 에 의해 로그인 페이지로 이동하고, 로그인 페이지는 접근할 수 없기 때문에 무한루프에 빠질 수 있다
                        login -> login.loginPage("/login").permitAll()
                )
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // request에 인증을 걸었을 때 css와 같은 리소스도 접근되지 않기 때문에 제외시켜줌
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                );
    }
}
