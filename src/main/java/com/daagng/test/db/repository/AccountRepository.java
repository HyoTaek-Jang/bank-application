package com.daagng.test.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.User;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Optional<Account> findByAccountNumber(Long accountNumber);

	Optional<Account> findByAccountId(Long accountId);

	List<Account> findByUser(User user);
}
