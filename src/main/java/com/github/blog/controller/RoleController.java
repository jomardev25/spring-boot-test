package com.github.blog.controller;

import java.util.List;

import javax.validation.Valid;

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
import com.github.blog.payload.RoleDto;
import com.github.blog.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

	private RoleService roleService;

	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@GetMapping
	public List<RoleDto> index(){
		return roleService.getAllRoles();
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleDto> show(@PathVariable(name = "id") Long id){
		RoleDto roleDto = roleService.getRoleById(id);
		return new ResponseEntity<>(roleDto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<RoleDto> create(@RequestBody @Valid RoleDto roleDto){
		return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<RoleDto> update(@PathVariable(name = "id") Long id, @RequestBody @Valid RoleDto roleDto){
		return new ResponseEntity<>(roleService.updateRole(id, roleDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		roleService.deleteRole(id);
		return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
	}
}
