package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.Bank;
import com.daagng.test.db.repository.BankRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {

	private final BankRepository bankRepository;

	public Bank findBank(String code) {
		return bankRepository.findByCode(code).orElse(null);
	}
}
