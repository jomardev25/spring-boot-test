package com.github.blog.util;

import com.github.blog.entity.Role;
import com.github.blog.payload.RoleDto;

public class RoleMapper {

	public RoleDto mapToDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setId(role.getId());
		roleDto.setName(role.getName());
		return roleDto;
	}

	public Role mapToEntity(RoleDto roleDto) {
		Role role = Role.builder().id(roleDto.getId()).name(roleDto.getName()).build();
		return role;
	}
}
