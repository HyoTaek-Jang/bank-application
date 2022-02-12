package com.daagng.test.db.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Transfer extends BaseEntity{
	@NotNull
	private Long to_account_number;

	// common - constants - TransferConstant 참조
	@NotNull
	private Integer state;

	@ManyToOne
	@JoinColumn
	private Bank to_bank;

	@ManyToOne
	@JoinColumn
	private Account from_account;

}
