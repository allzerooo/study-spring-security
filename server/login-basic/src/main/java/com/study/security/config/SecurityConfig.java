package com.study.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;

@EnableWebSecurity(debug = true)
// 컨트롤러에 설정해둔 ROLE 대로( @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") ) 페이지에 접근되도록 제한
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthDetails customAuthDetails;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser(
                        // withDefaultPasswordEncoder는 Deprecated 되었지만 테스트에 한정해서 사용
                        // withDefaultPasswordEncoder를 사용하면 따로 passwordEncoder를 정의하지 않아도 된다
                        User.withDefaultPasswordEncoder()
                                .username("user1")
                                .password("1111")
                                .roles("USER")
                ).withUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("2222")
                        .roles("ADMIN")
        );

    }

    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // 관리자는 USER가 할 수 있는걸 다 할 수 있게 부여
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(request->{
                    request
                            .antMatchers("/").permitAll() // 루트 페이지 접근은 모두 허용
                            .anyRequest().authenticated()   // anyRequest에 대해서는 허락을 받고 들어오도록
                            ;
                })
                // UsernamePasswordAuthenticationFilter는 formLogin으로 설정. login 페이지를 정해주지 않으면 DefaultLoginPageGeneratingFilter, DefaultLogoutPageGeneratingFilter 가 동작을 하게 된다
                .formLogin(
                        login -> login.loginPage("/login")  // loginPage를 설정하게 되면 default login page도 없고, default logout page도 없다. 따라서 default logout url인 "/logout"을 처리해줄 페이지나 버튼을 따로 만들어야 한다
                        .permitAll()  // permitAll을 하지 않으면 anyRequest().authenticated() 에 의해 로그인 페이지로 이동하고, 로그인 페이지는 접근할 수 없는 페이지이기 때문에 루트 페이지로 이동하면서 무한루프에 빠질 수 있다
                        // 로그인 성공 시 이동할 페이지. false로 하면 특정 페이지에 접근 중 로그인이 필요할 때 로그인 후 다시 그 페이지로 이동한다
                        .defaultSuccessUrl("/", false)
                        .failureUrl("/login-error") // default URL은 "/login?error"
                        .authenticationDetailsSource(customAuthDetails)
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                // 권한이없는 페이지에 접근했을 때의 페이지
                .exceptionHandling(exception -> exception.accessDeniedPage("/access-denied"))
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // request에 인증을 걸었을 때 css와 같은 리소스도 접근되지 않기 때문에 제외시켜줌
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations() // static 디렉토리 밑에 있는 리소스들이 해당됨
                );
    }
}
