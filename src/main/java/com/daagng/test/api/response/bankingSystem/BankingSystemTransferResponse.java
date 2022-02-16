package com.daagng.test.api.response.bankingSystem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankingSystemTransferResponse {
	private Long tx_id;
	private Long bank_tx_id;
	private String result;
}
