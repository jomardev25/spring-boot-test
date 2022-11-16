package com.github.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.github.blog.entity.Role;
import com.github.blog.exception.ResourceAlreadyExistsException;
import com.github.blog.exception.ResourceNotFoundException;
import com.github.blog.payload.RoleDto;
import com.github.blog.repository.RoleRepository;
import com.github.blog.service.RoleService;
import com.github.blog.util.RoleMapper;

@Service
public class RoleServiceImpl implements RoleService{

	private RoleRepository roleRepository;

	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDto> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		RoleMapper mapper = new RoleMapper();
		return roles.stream().map(role -> mapper.mapToDto(role)).collect(Collectors.toList());
	}

	@Override
	public RoleDto getRoleById(Long id) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		RoleMapper mapper = new RoleMapper();
		return mapper.mapToDto(role);
	}

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		//if(roleRepository.findByName(roleDto.getName()) != null)
		//	throw new ResourceAlreadyExistsException("Role", "name", roleDto.getName());
		RoleMapper mapper = new RoleMapper();
		Role role = roleRepository.save(mapper.mapToEntity(roleDto));
		return mapper.mapToDto(role);
	}

	@Override
	public RoleDto updateRole(Long id, RoleDto roleDto) {
		Role role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		role.setName(roleDto.getName());
		roleRepository.save(role);
		RoleMapper mapper = new RoleMapper();
		return mapper.mapToDto(role);
	}

	@Override
	public void deleteRole(Long id) {
		roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
		roleRepository.deleteById(id);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
		Assert.notNull(fieldName);

		if (!fieldName.equals("name")) {
            throw new UnsupportedOperationException("Field name not supported");
        }

		if (value == null) {
            return false;
        }

		return this.roleRepository.existsByName(value.toString());
	}
}
