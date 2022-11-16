package com.github.blog.service;

import com.github.blog.payload.PostDto;
import com.github.blog.payload.PostResponse;

public interface PostService {

	PostResponse getAllPosts(int page, int size, String sortBy, String sortDir);

	PostDto getPostById(Long id);

	PostDto savePost(PostDto postDto);

	PostDto updatePost(Long id, PostDto postDto);

	void deletePost(Long id);

}
