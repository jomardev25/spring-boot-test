package com.github.blog.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.blog.payload.UserDto;
import com.github.blog.payload.UserResponse;
import com.github.blog.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public List<UserResponse> index(){
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> show(@PathVariable(name = "id") Long id){
		UserResponse userResponse = userService.getUserById(id);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody UserDto userDto){
		return userService.createUser(userDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> update(@PathVariable(name = "id") Long id, @RequestBody UserDto userDto){
		return new ResponseEntity<>(userService.updateUser(id, userDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}

}
