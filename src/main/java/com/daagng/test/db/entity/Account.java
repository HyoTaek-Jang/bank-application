package com.daagng.test.db.entity;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "i_account_id", columnList = "accountId"), @Index(name = "i_account_number", columnList = "accountNumber")})
public class Account extends BaseEntity {
	// 뱅킹시스템 account id
	@NotNull
	@Column(unique = true)
	@DecimalMax(ACCOUNT_ID_MAX_NUMBER)
	@Min(MIN_NUMBER)
	private Long accountId;

	// 은행 계좌 번호
	@NotNull
	@Column(unique = true)
	@DecimalMax(ACCOUNT_NUMBER_MAX_NUMBER)
	@Min(MIN_NUMBER)
	private Long accountNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Bank bank;

	@OneToMany(mappedBy = "fromAccount")
	private List<Transfer> transferList;

	public void addTransfer(Transfer transfer) {
		if (this.transferList == null)
			transferList = new LinkedList<>();
		transferList.add(transfer);
		transfer.setFromAccount(this);
	}

	public Account(Long accountId, Long accountNumber, User user, Bank bank) {
		this.accountId = accountId;
		this.accountNumber = accountNumber;
		this.user = user;
		this.bank = bank;
	}
}
