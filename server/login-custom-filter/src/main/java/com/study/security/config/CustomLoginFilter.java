package com.study.security.config;

import com.study.security.student.StudentAuthenticationToken;
import com.study.security.teacher.TeacherAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

	public CustomLoginFilter(final AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
		String username = obtainUsername(request);
		username = (username != null) ? username : "";
		username = username.trim();
		String password = obtainPassword(request);
		password = (password != null) ? password : "";

		String type = request.getParameter("type"); // 학생인지, 선생님인지

		if (type == null || !type.equals("teacher")) {
			// student
			StudentAuthenticationToken token = StudentAuthenticationToken.builder()
					.credentials(username)
					.build();

			return this.getAuthenticationManager().authenticate(token);
		} else {
			// teacher
			TeacherAuthenticationToken token = TeacherAuthenticationToken.builder()
					.credentials(username)
					.build();

			return this.getAuthenticationManager().authenticate(token);
		}
	}
}
