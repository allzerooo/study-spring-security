package com.sp.fc.web.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    private String id;
    private String username;

    // GrantedAuthority는 JSON으로 serialize, deserialize 하기 어렵다
    @JsonIgnore
    private Set<GrantedAuthority> role;

    private String teacherId;
}
