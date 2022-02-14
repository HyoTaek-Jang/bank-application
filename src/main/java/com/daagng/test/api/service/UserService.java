package com.daagng.test.api.service;

import org.springframework.stereotype.Service;

import com.daagng.test.db.entity.User;
import com.daagng.test.db.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User findUser(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}

	public User findUser(String name) {
		return userRepository.findByName(name).orElse(null);
	}

}
