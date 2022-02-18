package com.daagng.test.db.entity;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@Size(max = CODE_SIZE, min = CODE_SIZE, message = CODE_SIZE_MSG)
	private String code;

	// 은행명
	@NotNull
	private String name;

	@OneToMany(mappedBy = "bank")
	private List<Account> accountList;

	public void addAccount(Account account) {
		if (this.accountList == null)
			accountList = new LinkedList<>();
		accountList.add(account);
		account.setBank(this);
	}

	@OneToMany(mappedBy = "toBank")
	private List<Transfer> transferList;

	public void addTransfer(Transfer transfer) {
		if (this.transferList == null)
			transferList = new LinkedList<>();
		transferList.add(transfer);
		transfer.setToBank(this);
	}
}
