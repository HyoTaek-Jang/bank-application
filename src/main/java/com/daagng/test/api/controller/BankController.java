package com.daagng.test.api.controller;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;
import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.request.bank.RegisterAccountRequest;
import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.api.response.bank.RegisterAccountResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemRegisterResponse;
import com.daagng.test.api.service.AccountService;
import com.daagng.test.api.service.BankService;
import com.daagng.test.common.exception.BankingSystemException;
import com.daagng.test.common.util.NumberUtil;
import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.Bank;
import com.daagng.test.db.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
public class BankController {

	private final BankService bankService;
	private final AccountService accountService;
	private final WebClient webClient;

	@PostMapping("/register")
	public ResponseEntity<? extends BaseResponse> registerAccount(HttpServletRequest request,@Valid @RequestBody RegisterAccountRequest registerAccountRequest) {
		User user = (User)request.getAttribute("user");

		if (!NumberUtil.isNumberic(registerAccountRequest.getAccountNumber()))
			return ResponseEntity.status(400).body(new BaseResponse(ACCOUNT_NUMBER_SIZE_MSG));
		Integer accountNumber = Integer.parseInt(registerAccountRequest.getAccountNumber());
		Bank bank = bankService.findBank(registerAccountRequest.getCode());
		if (bank == null)
			return ResponseEntity.status(400).body(new BaseResponse(NOT_EXIST_CODE));

		if (accountService.findAccount(accountNumber) != null)
			return ResponseEntity.status(400).body(new BaseResponse(EXIST_ACCOUNT_NUMBER));

		BankingSystemRegisterResponse response = webClient.post()
			.uri(REGISTER_PATH)
			.retrieve()
			.onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(BankingSystemErrorResponse.class)
				.map(body -> new BankingSystemException(body, clientResponse.statusCode())))
			.bodyToMono(BankingSystemRegisterResponse.class)
			.timeout(Duration.ofSeconds(BANK_TIMEOUT))
			.block();

		assert response != null;
		Account account = new Account(Integer.parseInt(response.getBank_account_id()), accountNumber, user, bank);
		accountService.save(account);

		return ResponseEntity.status(201).body(new RegisterAccountResponse(response.getBank_account_id(), SUCCESS_REGISTER));
	}
}
