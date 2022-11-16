package com.github.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.github.blog.annotation.Unique;
import com.github.blog.service.RoleService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

	private long id;

	@NotNull(message = "Name field is required.")
	@NotEmpty(message = "Name field must not be empty.")
	@Size(min = 2, message = "Name field must be greater than 2 characters.")

	//@Unique(service = RoleService.class, fieldName = "name", message = "Role name already exists.")
	private String name;

}
