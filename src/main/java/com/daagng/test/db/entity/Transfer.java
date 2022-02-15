package com.daagng.test.db.entity;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import javax.persistence.Entity;
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
public class Transfer extends BaseEntity{
	@NotNull
	@DecimalMax(ACCOUNT_NUMBER_MAX_NUMBER)
	@Min(MIN_NUMBER)
	private Long toAccountNumber;

	// common - constants - TransferConstant 참조
	@NotNull
	private Integer state;

	@ManyToOne
	@JoinColumn
	private Bank toBank;

	@ManyToOne
	@JoinColumn
	private Account fromAccount;

}
