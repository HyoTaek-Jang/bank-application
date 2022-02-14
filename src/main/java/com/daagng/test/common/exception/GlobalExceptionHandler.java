package com.daagng.test.common.exception;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daagng.test.api.response.bankingSystem.BankingSystemErrorResponse;
import com.daagng.test.api.response.BaseResponse;
import com.daagng.test.common.constants.CommonConstant;
import com.daagng.test.common.constants.bank.BankingSystemConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String defaultMessage = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());;
		return ResponseEntity.status(400).body(new BaseResponse(defaultMessage));
	}

	@ExceptionHandler(value = BankingSystemException.class)
	protected ResponseEntity<BaseResponse> handleBankingException(BankingSystemException e) {
		BankingSystemErrorResponse bankingSystemErrorResponse = e.getBankingSystemErrorResponse();
		HttpStatus httpStatus = e.getHttpStatus();
		return ResponseEntity.status(httpStatus.value()).body(new BaseResponse(bankingSystemErrorResponse.getMessage()));
	}

	@ExceptionHandler(value = BankingSystemTimeoutException.class)
	protected ResponseEntity<BaseResponse> handleTimeoutException(BankingSystemTimeoutException e) {
		return ResponseEntity.status(500).body(new BaseResponse(BankingSystemConstant.LATE_RESPONSE_MSG));
	}

	@ExceptionHandler(value = UnauthorizedException.class)
	protected ResponseEntity<BaseResponse> unauthorizedException(UnauthorizedException e) {
		return ResponseEntity.status(401).body(new BaseResponse(CommonConstant.UNAUTHORIZATION_MSG));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<BaseResponse> handleException(Exception e) {
		log.error("Internal Error Trace : {} ", Arrays.toString(e.getStackTrace()));
		log.error("Error Message : {}",e.getMessage());
		return ResponseEntity.status(500).body(new BaseResponse(e.getMessage()));
	}

}
