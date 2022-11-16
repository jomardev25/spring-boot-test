package com.github.blog.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.blog.entity.Role;
import com.github.blog.entity.User;
import com.github.blog.exception.ResourceNotFoundException;
import com.github.blog.payload.UserDto;
import com.github.blog.payload.UserResponse;
import com.github.blog.repository.RoleRepository;
import com.github.blog.repository.UserRepository;
import com.github.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(user -> mapToDto(user)).collect(Collectors.toList());
	}

	@Override
	public UserResponse getUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return mapToDto(user);
	}

	@Override
	public ResponseEntity<Object> createUser(UserDto userDto) {
		if (userRepository.findByEmail(userDto.getEmail()).isPresent())
			return ResponseEntity.badRequest().body("The email is already exists, Failed to create new user");

		User user = userRepository.save(mapToEntity(userDto));
		return new ResponseEntity<>(mapToDto(user), HttpStatus.CREATED);
	}

	@Override
	public UserResponse updateUser(Long id, UserDto userDto) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user = userRepository.save(mapToEntity(userDto));
		return mapToDto(user);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		userRepository.delete(user);
	}

	private UserResponse mapToDto(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setEmail(user.getEmail());
		userResponse.setUsername(user.getUsername());
		userResponse.setRoles(user.getRoles());
		return userResponse;
	}

	private User mapToEntity(UserDto userDto) {
		User user = new User();
		user.setUsername(userDto.getUsername());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName()); user.setEmail(userDto.getEmail());

		Set<Role> roles =  userDto.getRoles().stream().map(roleName -> {
			return roleRepository.findByName(roleName);
		}).collect(Collectors.toSet());

		user.setRoles(roles);

		return user;
	}

}
