package com.daagng.test.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import com.daagng.test.BaseTest;
import com.daagng.test.api.request.bank.RegisterAccountRequest;

class BankControllerTest extends BaseTest {
	@Test
	@DisplayName("registerAccount API validation test - success")
	void registerAccountValidationSuccessTest() throws Exception {
		//given
		RegisterAccountRequest request = RegisterAccountRequest.builder()
			.code("D001")
			.accountNumber("1234567890")
			.build();

		//When
		ResultActions authorization = this.mockMvc.perform(
			post("/bank/register").header("Authorization", 1).contentType(MediaType.APPLICATION_JSON).content(
				this.objectMapper.writeValueAsString(request)));

		//Then
		authorization.andExpect(status().is2xxSuccessful());
	}

	@Test
	@DisplayName("registerAccount API validation test - accountNumber fail")
	void registerAccountValidationFailTest() throws Exception {
		//given
		RegisterAccountRequest request = RegisterAccountRequest.builder()
			.code("D001")
			.accountNumber("123456789A")
			.build();

		//When
		ResultActions authorization = this.mockMvc.perform(
			post("/bank/register").header("Authorization", 1).contentType(MediaType.APPLICATION_JSON).content(
				this.objectMapper.writeValueAsString(request)));

		//Then
		authorization.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("registerAccount API validation test - bank code fail")
	void registerAccountValidationFailTest2() throws Exception {
		//given
		RegisterAccountRequest request = RegisterAccountRequest.builder()
			.code("A001")
			.accountNumber("1234567890")
			.build();

		//When
		ResultActions authorization = this.mockMvc.perform(
			post("/bank/register").header("Authorization", 1).contentType(MediaType.APPLICATION_JSON).content(
				this.objectMapper.writeValueAsString(request)));

		//Then
		authorization.andExpect(status().is4xxClientError());
	}

}