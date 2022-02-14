package com.daagng.test.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daagng.test.api.request.bank.RegisterAccountRequest;
import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.api.service.AccountService;
import com.daagng.test.api.service.BankService;
import com.daagng.test.common.constants.CommonConstant;
import com.daagng.test.common.constants.bank.RegisterConstant;
import com.daagng.test.common.util.NumberUtil;
import com.daagng.test.db.entity.Bank;
import com.daagng.test.db.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
public class BankController {

	private final BankService bankService;
	private final AccountService accountService;

	@PostMapping("/register")
	public ResponseEntity<? extends BaseResponse> registerAccount(HttpServletRequest request,@Valid @RequestBody RegisterAccountRequest registerAccountRequest) {
		User user = (User)request.getAttribute("user");

		if (!NumberUtil.isNumberic(registerAccountRequest.getAccountNumber()))
			return ResponseEntity.status(400).body(new BaseResponse("계좌번호 길이는 10자리입니다."));
		Integer accountNumber = Integer.parseInt(registerAccountRequest.getAccountNumber());
		Bank bank = bankService.findBank(registerAccountRequest.getCode());
		if (bank == null)
			return ResponseEntity.status(400).body(new BaseResponse("존재하지 않는 은행코드입니다."));

		if (accountService.findAccount(accountNumber) != null)
			return ResponseEntity.status(400).body(new BaseResponse("이미 등록된 계좌번호입니다."));



		return null;
	}
}
