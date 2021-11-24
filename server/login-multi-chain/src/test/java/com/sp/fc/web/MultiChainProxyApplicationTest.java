package com.sp.fc.web;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.fc.web.student.Student;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MultiChainProxyApplicationTest {

	@LocalServerPort
	int port;

	RestTemplate client = new RestTemplate();

	@DisplayName("1. 학생 조사")
	@Test
	void test_1() throws JsonProcessingException {
		String url = format("http://localhost:%d/api/teacher/students", port);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, "Basic "+ Base64.getEncoder().encodeToString(
				"choi:1".getBytes()
		));
		HttpEntity entity = new HttpEntity(null, headers);
		ResponseEntity<String> resp = client.exchange(url, HttpMethod.GET, entity, String.class);

		List<Student> students = new ObjectMapper().readValue(resp.getBody(), new TypeReference<List<Student>>() {});

		assertEquals(3, students.size());
	}
}