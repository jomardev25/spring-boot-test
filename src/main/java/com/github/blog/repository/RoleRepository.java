package com.github.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	boolean existsByName(String name);

}
