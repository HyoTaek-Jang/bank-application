package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.Account;
import com.daagng.test.db.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Account findAccountByAccountNumber(Long accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber).orElse(null);
	}

	public Account save(Account account) {
		return accountRepository.save(account);
	}

	public Account findAccountByAccountId(Long accountId) {
		return accountRepository.findByAccountId(accountId).orElse(null);
	}
}
