package com.github.blog.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.github.blog.entity.Comment;

import lombok.Data;

@Data
public class PostDto {

	private long id;

	@NotEmpty(message = "Title field must not be empty.")
	@Size(min = 2, message = "Title field must be greater than 2 characters.")
	private String title;

	@NotEmpty(message = "Description field must not be empty.")
	private String description;


	@NotEmpty(message = "Content field must not be empty.")
	private String content;

	private Set<Comment> comments = new HashSet<Comment>();

}
