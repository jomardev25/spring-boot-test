package com.github.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.github.blog.entity.Post;
import com.github.blog.exception.ResourceNotFoundException;
import com.github.blog.payload.PostDto;
import com.github.blog.payload.PostResponse;
import com.github.blog.repository.PostRepository;
import com.github.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;


	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public PostResponse getAllPosts(int page, int size, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		PostResponse postResponse = new PostResponse();
		Pageable pageable = PageRequest.of(page, size, sort);
		Page<Post> posts = postRepository.findAll(pageable);
		List<PostDto> content =  posts.getContent().stream().map(post -> mapToDto(post)).collect(Collectors.toList());

		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setFirst(posts.isFirst());
		postResponse.setLast(posts.isLast());
		postResponse.setPosts(content);

		return postResponse;
	}

	@Override
	public PostDto getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDto(post);
	}

	@Override
	public PostDto savePost(PostDto postDto) {
		Post post = postRepository.save(mapToEntity(postDto));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(Long id, PostDto postDto) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return mapToDto(postRepository.save(post));
	}

	@Override
	public void deletePost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
	}

	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		postDto.setComments(post.getComments());
		return postDto;
	}

	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		return post;
	}

}
