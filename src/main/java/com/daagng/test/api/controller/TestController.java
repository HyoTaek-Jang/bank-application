package com.daagng.test.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping("/auth")
	public String testAuthInterceptor() {
		return "pass auth interceptor";
	}
}
