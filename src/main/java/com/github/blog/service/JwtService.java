package com.github.blog.service;

import java.util.List;

import com.github.blog.payload.JwtBlockListTokenDto;

public interface JwtService {

	List<JwtBlockListTokenDto> getAllBlockListedToken();

	JwtBlockListTokenDto getBlockListedToken(String token);

	void removeBlockListedToken(String token);

}
