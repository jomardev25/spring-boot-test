package com.github.blog.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostResponse {

	private int pageSize;
	private int pageNumber;
	private int totalPages;
	private long totalElements;
	private boolean first;
	private boolean last;
	private List<PostDto> posts;

}
