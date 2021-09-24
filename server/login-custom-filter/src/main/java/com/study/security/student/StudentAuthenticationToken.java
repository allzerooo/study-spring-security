package com.study.security.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 사이트에 들어온 Student에게 발급해줄 통행증
public class StudentAuthenticationToken implements Authentication {

    // 구현해야되는 getter들을 아래와 같이 선언해서 lombok으로 해결

    private Student principal;
    private String credentials;
    private String details;
    private boolean authenticated; // 통행증 도장을 받을 장소

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // student가 grantedAuthority를 가지고 있기 때문에
        return principal == null ? new HashSet<>() : principal.getRole();
    }

    @Override
    public String getName() {
        return principal == null ? "" : principal.getUsername();
    }
}
