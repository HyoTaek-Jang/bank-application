package com.daagng.test.api.request.bankingSystem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BankingSystemTransferRequest {
	private Long tx_id;
	private Long from_bank_account_id;
	private String to_bank_code;
	private String to_bank_account_number;
	private Long transfer_amount;
}
