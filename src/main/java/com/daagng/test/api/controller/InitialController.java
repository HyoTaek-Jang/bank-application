package com.daagng.test.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class InitialController {

	@GetMapping("/auth")
	public String testAuthInterceptor() {
		return "pass auth interceptor";
	}
}
