package com.github.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.blog.payload.CommentResponseDto;
import com.github.blog.payload.CommentDto;
import com.github.blog.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping
	public List<CommentResponseDto> index(){
		return commentService.getAllComments();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CommentResponseDto> show(@PathVariable(name = "id") Long id){
		CommentResponseDto commentResponseDto = commentService.getCommentById(id);
		return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
	}

	@GetMapping("/{postId}/{id}")
	public List<CommentResponseDto> getByCommentsPostIdAndCommentId(
			@PathVariable(name = "postId") Long postId,
			@PathVariable(name = "id") Long id
	){
		return commentService.getCommentsByPostIdAndId(postId, id);
	}

	@PostMapping
	public ResponseEntity<CommentResponseDto> create(@RequestBody CommentDto commentDto){
		CommentResponseDto commentResponseDto = commentService.createComment(commentDto);
		return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentResponseDto> update(@PathVariable(name = "id") Long id, @RequestBody CommentDto commentDto){
		CommentResponseDto updatedCommentResponseDto = commentService.updateComment(id, commentDto);
		return new ResponseEntity<>(updatedCommentResponseDto, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
		commentService.deleteComment(id);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}
}
