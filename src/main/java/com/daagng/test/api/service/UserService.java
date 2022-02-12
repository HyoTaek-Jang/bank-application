package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

}
