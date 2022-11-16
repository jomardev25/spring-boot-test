package com.github.blog.service;

import java.util.List;

import com.github.blog.payload.CommentDto;
import com.github.blog.payload.CommentResponseDto;

public interface CommentService {

	List<CommentResponseDto> getAllComments();

	CommentResponseDto getCommentById(Long id);

	List<CommentResponseDto> getCommentsByPostId(Long postId);

	List<CommentResponseDto> getCommentsByPostIdAndId(Long postId, Long id);

	CommentResponseDto createComment(CommentDto commentDto);

	CommentResponseDto updateComment(Long id, CommentDto commentDto);

	void deleteComment(Long id);

}
