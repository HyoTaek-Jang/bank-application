package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.common.exception.CustomValidException;
import com.daagng.test.common.util.NumberUtil;
import com.daagng.test.api.db.entity.Bank;

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
		Bank bank = bankService.findByCode(code);
		if (bank == null)
			throw new CustomValidException(400, message);
		return bank;
	}

}
