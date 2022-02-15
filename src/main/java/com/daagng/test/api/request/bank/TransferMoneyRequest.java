package com.daagng.test.api.request.bank;

import static com.daagng.test.common.constants.bank.RegisterConstant.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {
	@NotNull
	@Size(max = CODE_SIZE, min = CODE_SIZE, message = CODE_SIZE_MSG)
	private String toCode;

	@NotNull
	@Size(max = ACCOUNT_NUMBER_SIZE, min = ACCOUNT_NUMBER_SIZE, message = ACCOUNT_NUMBER_SIZE_MSG)
	private String toAccountNumber;

	@NotNull
	private Long amount;

	@NotNull
	@Size(max = ACCOUNT_ID_SIZE, min = ACCOUNT_ID_SIZE, message = ACCOUNT_ID_SIZE_MSG)
	private String fromAccountId;
}
