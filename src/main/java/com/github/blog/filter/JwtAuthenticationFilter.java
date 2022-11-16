package com.github.blog.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.blog.exception.BlogAPIException;
import com.github.blog.payload.ErrorDetails;
import com.github.blog.security.CustomUserDetailsService;
import com.github.blog.security.JwtTokenProvider;
import com.github.blog.util.JwtUtil;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {
		String token = JwtUtil.getTokenFromRequest(request);
		try {
			if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
				String username = jwtTokenProvider.getUsernameFromToken(token);
				UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities()
				);

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (BlogAPIException ex) {
			ErrorDetails errorDetails = new ErrorDetails(new Date(), "Bad Request", ex.getMessage());
			filterResponse(response, ex.getStatus().value(), errorDetails);
		}

		filterChain.doFilter(request, response);
	}

	private void filterResponse(HttpServletResponse response, int statusCode, ErrorDetails errorDetails) throws StreamWriteException, DatabindException, IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	response.setStatus(statusCode);
		OutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(outputStream, errorDetails);
        outputStream.flush();
	}

}
