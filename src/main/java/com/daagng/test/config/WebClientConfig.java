package com.daagng.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.common.constants.WebClientConstant;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient BankingWebClient() {
		return WebClient.builder().baseUrl(WebClientConstant.BANKING_HOST).build();
	}

	@Bean
	public WebClient TestWebClient() {
		return WebClient.builder().baseUrl(WebClientConstant.TEST_HOST).build();
	}
}
