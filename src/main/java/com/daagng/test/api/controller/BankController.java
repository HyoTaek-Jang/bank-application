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
import com.daagng.test.db.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
public class BankController {

	@PostMapping("/register")
	public ResponseEntity<? extends BaseResponse> registerAccount(HttpServletRequest request,@Valid @RequestBody RegisterAccountRequest registerAccountRequest) {
		User user = (User)request.getAttribute("user");

		System.out.println(registerAccountRequest.getAccountNumber());

		return null;
	}
}
