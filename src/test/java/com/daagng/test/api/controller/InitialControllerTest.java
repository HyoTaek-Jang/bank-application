package com.daagng.test.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;

class InitialControllerTest extends BaseTest {
	@Test
	@DisplayName("auth interceptor test - success")
	void authInterceptorSuccessTest() throws Exception {
		//When
		ResultActions resultActions = this.mockMvc.perform(
			get("/test/auth").header("Authorization", 1));

		//Then
		resultActions.andExpect(
			content().string("pass auth interceptor")).andExpect(status().isOk());
	}

	@Test
	@DisplayName("auth interceptor test - fail")
	void authInterceptorFailTest() throws Exception {
		//When
		ResultActions resultActions = this.mockMvc.perform(
			get("/test/auth").header("Authorization", "a123"));

		//Then
		resultActions.andExpect(status().isUnauthorized());
	}
}