package com.daagng.test.common;

import static com.daagng.test.common.constants.CommonConstant.*;
import static com.daagng.test.common.constants.bank.TransferConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;
import com.daagng.test.api.request.bank.TransferMoneyRequest;

public class LazyTransferTest extends BaseTest {
	@Test
	@DisplayName("lazy transfer scheduler test - before scheduler")
	void beforeSchedulerTest() throws Exception {
		//given
		TransferMoneyRequest request = TransferMoneyRequest.builder()
			.amount(10000L)
			.fromAccountId("12312312")
			.toAccountNumber("0987654321")
			.toCode("D001")
			.build();

		//When
		ResultActions resultActions = this.mockMvc.perform(
			post("/bank/transfer").header("Authorization", 2).contentType(MediaType.APPLICATION_JSON).content(
				this.objectMapper.writeValueAsString(request)));

		//Then
		resultActions.andExpect(status().is4xxClientError()).andExpect(jsonPath("message", String.class).value(EXIST_WAITING_TRANSFER));
	}

	@Test
	@DisplayName("lazy transfer scheduler test - after scheduler")
	void afterSchedulerTest() throws Exception {
		//given
		TimeUnit.SECONDS.sleep(SCHEDULER_INTERVAL_TIME);
		TransferMoneyRequest request = TransferMoneyRequest.builder()
			.amount(10000L)
			.fromAccountId("12312312")
			.toAccountNumber("0987654321")
			.toCode("D001")
			.build();

		//When
		ResultActions resultActions = this.mockMvc.perform(
			post("/bank/transfer").header("Authorization", 2).contentType(MediaType.APPLICATION_JSON).content(
				this.objectMapper.writeValueAsString(request)));

		//Then
		resultActions.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("message", String.class).value(FINISHED_REQUEST));
	}
}
