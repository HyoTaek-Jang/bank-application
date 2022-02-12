package com.daagng.test.db.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bank extends BaseEntity{
	// 은행 고유 코드
	@NotNull
	@Column(unique = true)
	private String code;

	// 은행명
	private String name;

	@OneToMany(mappedBy = "bank")
	private List<Account> accountList;

	public void addAccount(Account account) {
		if (this.accountList == null)
			accountList = new LinkedList<>();
		accountList.add(account);
		account.setBank(this);
	}

	@OneToMany(mappedBy = "to_bank")
	private List<Transfer> transferList;

	public void addTransfer(Transfer transfer) {
		if (this.transferList == null)
			transferList = new LinkedList<>();
		transferList.add(transfer);
		transfer.setTo_bank(this);
	}
}
