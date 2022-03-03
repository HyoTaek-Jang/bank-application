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
public class RegisterAccountRequest {
	@NotNull
	@Size(max = CODE_SIZE, min = CODE_SIZE, message = CODE_SIZE_MSG)
	private String code;

	@NotNull
	@Size(max = ACCOUNT_NUMBER_SIZE, min = ACCOUNT_NUMBER_SIZE, message = ACCOUNT_NUMBER_SIZE_MSG)
	private String accountNumber;
}
