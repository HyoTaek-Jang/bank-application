package com.daagng.test.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.daagng.test.BaseTest;
import com.daagng.test.api.db.entity.Bank;
import com.daagng.test.api.db.entity.User;
import com.daagng.test.api.db.repository.BankRepository;
import com.daagng.test.api.db.repository.UserRepository;

public class SqlInitTest extends BaseTest {
	private final UserRepository userRepository;
	private final BankRepository bankRepository;

	@Autowired
	public SqlInitTest(UserRepository userRepository, BankRepository bankRepository) {
		this.userRepository = userRepository;
		this.bankRepository = bankRepository;
	}

	@Test
	@DisplayName("SQL init test")
	void sqlInitTest() {
		//given
		User user = userRepository.findById(1L).orElse(null);
		Bank bank = bankRepository.findByCode("D001").orElse(null);

		//Then
		Assertions.assertThat(user).isNotNull();
		Assertions.assertThat(bank).isNotNull();
	}
}
