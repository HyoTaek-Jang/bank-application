package com.daagng.test.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.daagng.test.api.service.UserService;
import com.daagng.test.common.exception.UnauthorizedException;
import com.daagng.test.db.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements AsyncHandlerInterceptor {

	private final UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		try {
			Long user_id = Long.parseLong(request.getHeader("Authorization"));
			User user = userService.findUser(user_id);
			if (user == null)
				throw new UnauthorizedException();
			request.setAttribute("user", user);
			return true;
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
	}
}
