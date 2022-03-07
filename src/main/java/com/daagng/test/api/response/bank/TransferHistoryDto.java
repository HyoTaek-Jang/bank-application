package com.daagng.test.api.response.bank;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import com.daagng.test.api.db.entity.Account;
import com.daagng.test.api.db.entity.Transfer;

import lombok.Getter;

@Getter
public class TransferHistoryDto {
	private final String fromAccountId;
	private final String toAccountNumber;
	private final String toBank;
	private final Integer state;

	public TransferHistoryDto(Transfer transfer, Account account) {
		this.fromAccountId = String.format("%0" + ACCOUNT_ID_SIZE + "d", account.getAccountId());
		this.toAccountNumber = String.format("%0" + ACCOUNT_NUMBER_SIZE + "d", transfer.getToAccountNumber());
		this.toBank = transfer.getToBank().getCode();
		this.state = transfer.getState();
	}
}
