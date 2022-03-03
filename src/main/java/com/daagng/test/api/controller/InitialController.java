package com.daagng.test.api.controller;

import static com.daagng.test.common.constants.bank.BankingSystemConstant.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.common.exception.BankingSystemException;
import com.daagng.test.common.exception.BankingSystemTimeoutException;

import lombok.RequiredArgsConstructor;

// Auth 인터셉터, Webclient 예외처리를 테스트하기 위한 컨트롤러
@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class InitialController {

	@GetMapping("/auth")
	public String authInterceptor() {
		return "pass auth interceptor";
	}

	@GetMapping("/bankingSystem/exception")
	public void bankingSystemException() {
		BankingSystemErrorResponse bankingSystemErrorResponse = new BankingSystemErrorResponse("BANKING_ERROR_100",
			WRONG_ACCOUNT_INFO);

		throw new BankingSystemException(bankingSystemErrorResponse,
			HttpStatus.valueOf(BANK_USER_ERR));
	}

	@GetMapping("/bankingSystem/timeout")
	public void bankingSystemTimeoutException() throws BankingSystemTimeoutException {
		throw new BankingSystemTimeoutException();
	}

}
