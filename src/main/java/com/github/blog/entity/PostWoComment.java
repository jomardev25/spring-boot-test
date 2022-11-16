package com.github.blog.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostWoComment {

	private long id;
	private String title;
	private String description;
	private String content;

}
