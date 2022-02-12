package com.daagng.test.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daagng.test.db.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
	Optional<Bank> findByCode(String code);
}
