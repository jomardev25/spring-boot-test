package com.github.blog.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.github.blog.payload.UserDto;
import com.github.blog.payload.UserResponse;

public interface UserService {

	List<UserResponse> getAllUsers();

	UserResponse getUserById(Long id);

	ResponseEntity<Object> createUser(UserDto userDto);

	UserResponse updateUser(Long id, UserDto userDto);

	void deleteUser(Long id);

}
