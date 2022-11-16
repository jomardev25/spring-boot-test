package com.github.blog.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import org.mockito.quality.Strictness;

import com.github.blog.entity.Role;
import com.github.blog.exception.ResourceNotFoundException;
import com.github.blog.payload.RoleDto;
import com.github.blog.repository.RoleRepository;
import com.github.blog.service.impl.RoleServiceImpl;
import com.github.blog.util.RoleMapper;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoleServiceTest {

	@Mock
	private RoleRepository roleRepository;

	@InjectMocks
	private RoleServiceImpl roleService;

	private RoleDto roleDto;
	private RoleMapper mapper;


	@BeforeEach
	void setUp() {
		mapper = new RoleMapper();
		roleDto = RoleDto.builder().id(1L).name("admin").build();
	}

	@DisplayName("JUnit test for getAllRoles method")
	@Test
	void testGetAllRoles() {
		Role role1 = Role.builder().id(1L).name("admin").build();
		Role role2 = Role.builder().id(2L).name("user").build();
		List<Role> roles = new ArrayList<>();
		roles.add(role1);
		roles.add(role2);
		when(roleRepository.findAll()).thenReturn(roles);

		List<RoleDto> roleDtos = roleService.getAllRoles();
		assertThat(roleDtos).isNotNull();
		assertThat(roleDtos.size()).isEqualTo(2);
	}

	@DisplayName("JUnit test for getAllRoles method")
	@Test
	void testGetAllRolesEmpty() {
		when(roleRepository.findAll()).thenReturn(Collections.emptyList());
		List<RoleDto> roleDtos = roleService.getAllRoles();
		assertThat(roleDtos).isEmpty();
		assertThat(roleDtos.size()).isEqualTo(0);
	}

	@DisplayName("JUnit test for getRoleById method")
	@Test
	void testGetRoleById() {
		Role role = Role.builder().id(1L).name("admin").build();
		when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
		RoleDto findRoleDto = roleService.getRoleById(1L);
		assertThat(findRoleDto).isNotNull();
	}

	@DisplayName("JUnit test for getRoleById method then return ResourceNotFoundException")
	@Test
	void testGetRoleByIdResourceNotFoundException() {
		when(roleRepository.findById(1L)).thenReturn(null);

		assertThrows(RuntimeException.class, () -> {
			roleService.getRoleById(1L);
        });

	}

	@DisplayName("JUnit test for createRole method")
	@Test
	void testCreateRole() {
		when(roleRepository.save(any(Role.class))).thenReturn(mapper.mapToEntity(roleDto));
		RoleDto storedRole = roleService.createRole(roleDto);
		assertThat(storedRole).isNotNull();
	}

	/*@DisplayName("JUnit test for createRole method which throws ResourceAlreadyException")
	@Test
	void testCreateRoleAlreadyExistException() {

		when(roleRepository.findByName("admin")).thenReturn(mapper.mapToEntity(roleDto));

		assertThrows(ResourceAlreadyExistsException.class, () -> {
			roleService.createRole(roleDto);
        });

		verify(roleRepository, never()).save(any(Role.class));
	}*/

	@DisplayName("JUnit test for updateRole method")
	@Test
	void testUpdateRole() {
		RoleDto toUpdateRoleDto = RoleDto.builder().id(1L).name("administrator").build();
		when(roleRepository.findById(1L)).thenReturn(Optional.of(mapper.mapToEntity(roleDto)));
		when(roleRepository.save(any(Role.class))).thenReturn(mapper.mapToEntity(toUpdateRoleDto));
		RoleDto updatedRole = roleService.updateRole(1L, toUpdateRoleDto);
		assertThat(updatedRole).isEqualTo(toUpdateRoleDto);
	}

	@DisplayName("JUnit test for updateRole method which throws ResourceNotFoundException")
	@Test
	void testCreateRoleResourceNotFoundException() {
		when(roleRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			roleService.updateRole(1L, roleDto);
        });

		verify(roleRepository, never()).save(any(Role.class));
	}

	@DisplayName("JUnit test for deleteRole method")
	@Test
	void testDeleteRole() {
		long roleId = 1L;
		when(roleRepository.findById(roleId)).thenReturn(Optional.of(mapper.mapToEntity(roleDto)));
		doNothing().when(roleRepository).deleteById(roleId);
		roleService.deleteRole(roleId);
		verify(roleRepository, times(1)).deleteById(roleId);
	}

	@DisplayName("JUnit test for updateRole method which throws ResourceNotFoundException")
	@Test
	void testDeleteRoleResourceNotFoundException() {
		long roleId = 1L;

		when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			roleService.deleteRole(roleId);
        });

		verify(roleRepository, never()).deleteById(roleId);
	}
}
