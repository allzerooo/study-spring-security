package com.study.security.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecurityMessage {

    private Authentication auth;    // 사용자가 어떤 Authentication으로 접근했는지
    private String message; // 어떤 페이지인지를 알려주는 메시지

}
