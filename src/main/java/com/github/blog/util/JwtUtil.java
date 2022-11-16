package com.github.blog.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class JwtUtil {

	public static String getTokenFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7, bearer.length());
		}

		return null;
	}
}
