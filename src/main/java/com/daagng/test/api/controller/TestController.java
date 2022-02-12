package com.daagng.test.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daagng.test.db.entity.User;

@RestController
@RequestMapping("test")
public class TestController {

	@GetMapping("/auth")
	public String testAuthInterceptor(HttpServletRequest request) {
		return "pass auth interceptor";
	}
}
