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
// 빌더가 생성자 없을때 AllArgs 만들어주는데 내가 잭슨 역직렬화때문에 NoArg를 만들어서 AllArg를 넣어야함.
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
