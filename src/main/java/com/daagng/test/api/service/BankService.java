package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.api.db.entity.Bank;
import com.daagng.test.api.db.repository.BankRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BankService {

	private final BankRepository bankRepository;

	public Bank findByCode(String code) {
		return bankRepository.findByCode(code).orElse(null);
	}
}
