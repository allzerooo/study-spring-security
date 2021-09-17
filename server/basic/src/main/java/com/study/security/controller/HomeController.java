package com.study.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String index() {
        return "홈페이지";
    }

    @RequestMapping("/auth")
    public Authentication auth() {
        // 사용자가 어떤 authentication 정보를 가지고 접근했는지 꺼내볼 수 있다
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")   // user 권한이 있어야만 접근할 수 있다
    @RequestMapping("/user")
    public SecurityMessage user() {
        return SecurityMessage.builder()
                .auth(SecurityContextHolder.getContext().getAuthentication())
                .message("User 정보")
                .build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")  // admin 권한이 있어야만 접근할 수 있다
    @RequestMapping("/admin")
    public SecurityMessage admin() {
        return SecurityMessage.builder()
                .auth(SecurityContextHolder.getContext().getAuthentication())
                .message("관리자 정보")
                .build();
    }
}
