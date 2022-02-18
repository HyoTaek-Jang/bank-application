package com.daagng.test.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.User;
import com.daagng.test.db.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final AccountRepository accountRepository;

	public Account findByAccountNumber(Long accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber).orElse(null);
	}

	public Account save(Account account) {
		return accountRepository.save(account);
	}

	public Account findByAccountId(Long accountId) {
		return accountRepository.findByAccountId(accountId).orElse(null);
	}

	public List<Account> findByUser(User user) {
		return accountRepository.findByUser(user);
	}
}
