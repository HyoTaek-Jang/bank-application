package com.daagng.test.api.controller;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.api.response.bankingSystem.TestRes;
import com.daagng.test.common.constants.BankingConstant;
import com.daagng.test.common.exception.BankingException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class InitialController {

	private final WebClient testWebClient;

	@GetMapping("/auth")
	public String testAuthInterceptor() {
		return "pass auth interceptor";
	}

	@GetMapping("/test")
	@ResponseBody
	public TestRes webClientTest() {
		return testWebClient.get()
			.uri("/todos/1")
			.retrieve()
			.onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(BankingSystemErrorResponse.class).map(body -> new BankingException(body, clientResponse.statusCode())))
			.bodyToMono(TestRes.class)
			.timeout(Duration.ofSeconds(BankingConstant.BANK_TIMEOUT))
			.block();
	}
}
