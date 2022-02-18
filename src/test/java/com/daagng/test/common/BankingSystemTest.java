package com.daagng.test.common;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;

public class BankingSystemTest extends BaseTest {

	@Test
	@DisplayName("bankingService exception handle")
	void bankingServiceErrorHandlingTest() throws Exception {
		//When
		ResultActions resultActions = this.mockMvc.perform(
			get("/test/bankingSystem/exception").header("Authorization", 1));

		//Then
		resultActions.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("message", String.class).value(WRONG_ACCOUNT_INFO));
	}

	@Test
	@DisplayName("bankingService timeout handle")
	void bankingServiceTimeoutHandlingTest() throws Exception {
		//When
		ResultActions resultActions = this.mockMvc.perform(
			get("/test/bankingSystem/timeout").header("Authorization", 1));

		//Then
		resultActions.andExpect(status().is5xxServerError())
			.andExpect(jsonPath("message", String.class).value(LATE_RESPONSE_MSG));
	}
}
