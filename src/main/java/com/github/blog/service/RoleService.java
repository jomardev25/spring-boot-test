package com.github.blog.service;

import java.util.List;

import com.github.blog.annotation.interfaces.FieldValueExists;
import com.github.blog.payload.RoleDto;

public interface RoleService extends FieldValueExists {

	List<RoleDto> getAllRoles();

	RoleDto getRoleById(Long id);

	RoleDto createRole(RoleDto roleDto);

	RoleDto updateRole(Long id, RoleDto roleDto);

	void deleteRole(Long id);

}
