package com.daagng.test.api.service;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.common.exception.CustomValidException;
import com.daagng.test.common.util.NumberUtil;
import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.Bank;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidationService {

	private final BankService bankService;

	public Long numbericTest(String target, String message) {
		if (!NumberUtil.isNumberic(target))
			throw new CustomValidException(400, message);
		return Long.parseLong(target);
	}

	public Bank bankCodeTest(String code, String message) {
		Bank bank = bankService.findBank(code);
		if (bank == null)
			throw new CustomValidException(400, message);
		return bank;
	}

}
