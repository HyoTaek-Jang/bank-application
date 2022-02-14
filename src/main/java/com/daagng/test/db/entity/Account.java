package com.daagng.test.db.entity;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {
	// 은행 계좌 번호 10자리 숫자
	@NotNull
	@Column(unique = true)
	private Long accountNumber;

	@ManyToOne
	@JoinColumn
	private User user;

	@ManyToOne
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
}
