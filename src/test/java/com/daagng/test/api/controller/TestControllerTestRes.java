package com.daagng.test.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;
import com.daagng.test.api.service.UserService;
import com.daagng.test.db.entity.Bank;
import com.daagng.test.db.entity.User;
import com.daagng.test.db.repository.BankRepository;
import com.daagng.test.db.repository.UserRepository;

class TestControllerTestRes extends BaseTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BankRepository bankRepository;

	@Test
	@DisplayName("auth interceptor test - success")
	void authInterceptorSuccessTest() throws Exception {
		//When
		ResultActions authorization = this.mockMvc.perform(
			get("/test/auth").header("Authorization", 1));

		//Then
		authorization.andExpect(
			content().string("pass auth interceptor")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("auth interceptor test - fail")
	void authInterceptorFailTest() throws Exception {
		//When
		ResultActions authorization = this.mockMvc.perform(
			get("/test/auth").header("Authorization", "a123"));

		//Then
		authorization.andExpect(status().isUnauthorized());
	}


	@Test
	@DisplayName("SQL init test")
	void sqlInitTest() {
		//given
		User user = userRepository.findById(1L).orElse(null);
		Bank bank = bankRepository.findByCode("D001").orElse(null);

		//Then
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(bank).isNotNull();
	}

}