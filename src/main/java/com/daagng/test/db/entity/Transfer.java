package com.daagng.test.db.entity;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;
import static com.daagng.test.common.constants.bank.TransferConstant.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer extends BaseEntity{
	@NotNull
	@DecimalMax(ACCOUNT_NUMBER_MAX_NUMBER)
	@Min(MIN_NUMBER)
	private Long toAccountNumber;

	// common - constants - TransferConstant 참조
	@NotNull
	private Integer state;

	@NotNull
	private Long amount;

	private Long bankTxId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Bank toBank;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Account fromAccount;

	public void finishedRequest(String result, Long bankTxId) {
		if (result.equals(SUCCESS_SIGNATURE))
			this.state = TRANSFER_SUCCESS;
		if (result.equals(FAIL_SIGNATURE))
			this.state = TRANSFER_FAIL;
		if (bankTxId != null)
			this.bankTxId = bankTxId;
	}
}
