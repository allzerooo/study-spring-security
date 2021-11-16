package com.sp.fc.web.teacher;

import com.sp.fc.web.student.Student;
import java.util.List;
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
public class Teacher {

    private String id;
    private String username;
    private Set<GrantedAuthority> role;

    private List<Student> studentList;
}
