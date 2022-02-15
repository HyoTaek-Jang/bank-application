package com.daagng.test.api.controller.bank;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;
import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.daagng.test.api.request.bank.RegisterAccountRequest;
import com.daagng.test.api.request.bank.TransferMoneyRequest;
import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.api.response.bank.RegisterAccountResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.api.response.bankingSystem.BankingSystemRegisterResponse;
import com.daagng.test.api.service.AccountService;
import com.daagng.test.api.service.BankService;
import com.daagng.test.api.service.ValidationService;
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
	@Value("${isRealBankingSystem}")
	boolean isRealBankingSystem;

	private final BankService bankService;
	private final AccountService accountService;
	private final WebClient webClient;
	private final ValidationService validationService;

	@PostMapping("/register")
	public ResponseEntity<? extends BaseResponse> registerAccount(HttpServletRequest request,@Valid @RequestBody RegisterAccountRequest registerAccountRequest) {
		User user = (User)request.getAttribute("user");

		// request 유효성 검사
		Long accountNumber = validationService.numbericTest(registerAccountRequest.getAccountNumber(), ACCOUNT_NUMBER_SIZE_MSG);
		Bank bank = validationService.bankCodeTest(registerAccountRequest.getCode(), NOT_EXIST_CODE);
		if (accountService.findAccountByAccountNumber(accountNumber) != null)
			return ResponseEntity.status(400).body(new BaseResponse(EXIST_ACCOUNT_NUMBER));
		
		BankingSystemRegisterResponse response;
		if (isRealBankingSystem){
			 response = webClient.post()
				.uri(REGISTER_PATH)
				.retrieve()
				.onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(BankingSystemErrorResponse.class)
					.map(body -> new BankingSystemException(body, clientResponse.statusCode())))
				.bodyToMono(BankingSystemRegisterResponse.class)
				.timeout(Duration.ofSeconds(BANK_TIMEOUT))
				.block();
		}else{
			// 뱅킹시스템이 작동을 안한다면, 정상적으로 작동이 됐다고 가정하고, 현재 존재하지 않는 랜덤한 Account Id를 제공 받는다.
			Account existAccount = new Account();
			while (existAccount != null) {
				String temp = NumberUtil.makeRandomNumbers(ACCOUNT_ID_SIZE);
				existAccount = accountService.findAccountByAccountId(Long.parseLong(temp));
			}
			response = new BankingSystemRegisterResponse(NumberUtil.makeRandomNumbers(ACCOUNT_ID_SIZE));
		}

		assert response != null;
		Account account = new Account(Long.parseLong(response.getBank_account_id()), accountNumber, user, bank);
		accountService.save(account);

		return ResponseEntity.status(201).body(new RegisterAccountResponse(response.getBank_account_id(), SUCCESS_REGISTER));
	}

	@PostMapping("/transfer")
	public ResponseEntity<? extends BaseResponse> transferMoney(HttpServletRequest request, @Valid @RequestBody
		TransferMoneyRequest moneyRequest) {
		User user = (User)request.getAttribute("user");

		// request 유효성 검사
		Long toAccountNumber = validationService.numbericTest(moneyRequest.getToAccountNumber(), ACCOUNT_NUMBER_SIZE_MSG);
		Long fromAccountId = validationService.numbericTest(moneyRequest.getFromAccountId(), ACCOUNT_ID_SIZE_MSG);
		Bank bank = validationService.bankCodeTest(moneyRequest.getToCode(), NOT_EXIST_CODE);
		Account fromAccount = accountService.findAccountByAccountId(fromAccountId);
		if (fromAccount == null || !Objects.equals(fromAccount.getUser().getId(), user.getId()))
			return ResponseEntity.status(400).body(new BaseResponse(NOT_MATCHING_USER));

		return null;
	}
}
