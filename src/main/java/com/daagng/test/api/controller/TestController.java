package com.daagng.test.api.controller;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.response.BankingHTTP.TestRes;
import com.daagng.test.common.util.HttpStatusPredicate;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

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
	@ResponseBody
	public TestRes webClientTest() {
		try {
			return testWebClient.get()
				.uri("/todos/1")
				.retrieve()
				.onStatus(httpStatus -> HttpStatusPredicate.test(httpStatus, 200), clientResponse -> Mono.error(new RuntimeException()))
				.bodyToMono(TestRes.class)
				.timeout(Duration.ofSeconds(1))
				.onErrorMap(TimeoutException.class, e -> new TimeoutException("timeout!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"))
				.block();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
