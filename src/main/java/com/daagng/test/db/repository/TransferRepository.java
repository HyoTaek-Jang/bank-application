package com.daagng.test.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
	Optional<Transfer> findByFromAccountAndState(Account fromAccount, Integer state);

	@Query(value = "SELECT MAX(id) FROM transfer", nativeQuery = true)
	Optional<Long> findLastPK();
}
