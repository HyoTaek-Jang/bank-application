package com.daagng.test.common;

import static com.daagng.test.common.constants.CommonConstant.*;
import static com.daagng.test.common.constants.bank.TransferConstant.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;
import com.daagng.test.api.request.bank.TransferMoneyRequest;
import com.daagng.test.api.db.entity.Account;
import com.daagng.test.api.db.entity.Bank;
import com.daagng.test.api.db.entity.Transfer;
import com.daagng.test.api.db.repository.AccountRepository;
import com.daagng.test.api.db.repository.BankRepository;
import com.daagng.test.api.db.repository.TransferRepository;

public class LazyTransferTest extends BaseTest {

	private final TransferRepository transferRepository;
	private final BankRepository bankRepository;
	private final AccountRepository accountRepository;

	@Autowired
	public LazyTransferTest(TransferRepository transferRepository,
		BankRepository bankRepository, AccountRepository accountRepository) {
		this.transferRepository = transferRepository;
		this.bankRepository = bankRepository;
		this.accountRepository = accountRepository;
	}

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
		Bank bank = bankRepository.findByCode("D001").orElse(null);
		Account account = accountRepository.findByAccountId(12312312L).orElse(null);
		transferRepository.save(new Transfer(1112223334L, 2, 10000L, 87654321L, bank, account));

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
