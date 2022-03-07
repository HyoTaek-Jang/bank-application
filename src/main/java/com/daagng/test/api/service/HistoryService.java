package com.daagng.test.api.service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.daagng.test.api.response.bank.TransferHistoryDto;
import com.daagng.test.api.db.entity.Account;
import com.daagng.test.api.db.repository.TransferRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoryService {

	private final TransferRepository transferRepository;

	public List<TransferHistoryDto> findAllHistory(List<Account> accountList) {
		List<TransferHistoryDto> responses = new LinkedList<>();
		for (Account account:
			accountList) {
			responses.addAll(transferRepository.findByFromAccountId(account)
				.stream()
				.map(transfer -> new TransferHistoryDto(transfer, account))
				.collect(Collectors.toList()));
		}
		return responses;
	}
}
