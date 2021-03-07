package com.study.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BasicApplicationTest {

    @DisplayName("1. 애플리케이션 세팅 테스트")
    @Test
    void setting_test() {

        assertEquals("test", "test");
    }
}
