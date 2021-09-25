package com.study.security.config;

import com.study.security.student.StudentManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final StudentManager studentManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(studentManager);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests(request->
                request.antMatchers("/").permitAll()
                .anyRequest().authenticated()
        )
        .formLogin(
                // login page를 통해서 usernamePasswordAuthenticationFilter가 동작하고
                // usernamePasswordAuthenticationFilter는 usernamePasswordAuthenticationToken을 발행하기 때문에
                // usernamePasswordAuthenticationToken을 대상으로 한 StudentManager가 발행을 해주게 된다
                login -> login.loginPage("/login")
                .permitAll()
        )
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                ;
    }
}
