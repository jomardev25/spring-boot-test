package com.github.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.blog.payload.PostDto;
import com.github.blog.payload.PostResponse;
import com.github.blog.service.PostService;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin
public class PostController {

	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}

	//@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping
	public PostResponse index(
			@RequestParam(name = "page", defaultValue = "1", required = false) int page,
			@RequestParam(name = "size", defaultValue = "10", required = false) int size,
			@RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
	) {

		return postService.getAllPosts(page - 1, size, sortBy, sortDir);

	}

	@PreAuthorize("hasAuthority('USER')")
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> show(@PathVariable(name = "id") long id) {
		PostDto postDto = postService.getPostById(id);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PostDto> create(@Valid @RequestBody PostDto formPostDto){
		PostDto postDto = postService.savePost(formPostDto);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PostDto> update(@PathVariable(name = "id") long id, @RequestBody PostDto formPostDto) {
		PostDto updatedPost = postService.updatePost(id, formPostDto);
		return new ResponseEntity<>(updatedPost, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") long id) {
		postService.deletePost(id);
		return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
	}
}
