package com.daagng.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.common.constants.bank.BankingSystemConstant;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient WebClient() {
		return WebClient.builder().baseUrl(BankingSystemConstant.BANKING_SYSTEM_HOST).build();
	}
}
