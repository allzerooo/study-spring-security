package com.study.security.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Domain에서의 Principal
public class Student {

    private String id;
    private String username;
    // Authentication 인증을 하려면 GrantedAuthority가 필요하고 Student는 ROLE_STUDENT라는 Authority를 가져야 특정 페이지에 접근할 수 있다
    private Set<GrantedAuthority> role;
}
