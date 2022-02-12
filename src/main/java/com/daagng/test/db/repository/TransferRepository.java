package com.daagng.test.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daagng.test.db.entity.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
