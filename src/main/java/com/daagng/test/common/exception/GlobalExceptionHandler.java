package com.daagng.test.common.exception;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daagng.test.api.response.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String defaultMessage = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());;
		log.error("handle Method Argument Not Valid Exception : {}", defaultMessage);
		return ResponseEntity.status(400).body(new BaseResponse(defaultMessage));
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<BaseResponse> handleException(Exception e) {
		log.error("Internal Error Trace : {} ", Arrays.toString(e.getStackTrace()));
		log.error("Error Message : {}",e.getMessage());
		return ResponseEntity.status(500).body(new BaseResponse(e.getMessage()));
	}

}
