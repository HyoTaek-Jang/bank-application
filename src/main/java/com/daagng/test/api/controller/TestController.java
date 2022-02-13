package com.daagng.test.api.controller;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.response.BankingHTTP.TestRes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

	private final WebClient testWebClient;

	@GetMapping("/auth")
	public String testAuthInterceptor() {
		return "pass auth interceptor";
	}

	@GetMapping("/test")
	public void webClientTest() {
		try {
			testWebClient.get().uri("/todos/1").retrieve().bodyToMono(TestRes.class).timeout(Duration.ofSeconds(5));
		} catch (TimeoutException e) {
			System.out.println("timeout");
		}

	}
}
