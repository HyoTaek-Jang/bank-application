package com.daagng.test.api.service;

import static com.daagng.test.common.constants.bank.TransferConstant.*;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.Account;
import com.daagng.test.db.entity.Transfer;
import com.daagng.test.db.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransferService {

	private final TransferRepository transferRepository;

	public Transfer findByAccountAndState(Account fromAccount, Integer state) {
		return transferRepository.findByFromAccountAndState(fromAccount, state).orElse(null);
	}

	public Long findTxId() {
		Long lastId = transferRepository.findLastPK().orElse(-1L);
		if (lastId >= MAX_TX_ID_NUMBER)
			return null;
		return lastId + 1;
	}

	public Transfer save(Transfer transfer) {
		return transferRepository.save(transfer);
	}
}
