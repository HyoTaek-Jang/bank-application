package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.User;
import com.daagng.test.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
}
