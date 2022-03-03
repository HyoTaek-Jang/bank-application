package com.daagng.test.api.service;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;

import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.common.exception.BankingSystemException;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WebClientService {

	private final WebClient webClient;

	public <T> T bankingServicePost(Class<T> responseType, Object requestBody, String path) {
		return webClient.post()
			.uri(path)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.bodyValue(requestBody)
			.retrieve()
			.onStatus(HttpStatus::isError,
				clientResponse -> clientResponse.bodyToMono(BankingSystemErrorResponse.class)
					.map(body -> new BankingSystemException(body, clientResponse.statusCode())).onErrorResume(
						Mono::error))
			.bodyToMono(responseType)
			.timeout(Duration.ofSeconds(BANK_TIMEOUT))
			.block();
	}

	public <T> T bankingServiceGet(Class<T> responseType, String path) {
		return webClient.get()
			.uri(path)
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.onStatus(HttpStatus::isError,
				clientResponse -> clientResponse.bodyToMono(BankingSystemErrorResponse.class)
					.map(body -> new BankingSystemException(body, clientResponse.statusCode())))
			.bodyToMono(responseType)
			.timeout(Duration.ofSeconds(BANK_TIMEOUT))
			.block();
	}
}
