package com.github.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.blog.entity.Comment;
import com.github.blog.entity.Post;
import com.github.blog.exception.ResourceNotFoundException;
import com.github.blog.payload.CommentDto;
import com.github.blog.payload.CommentResponseDto;
import com.github.blog.repository.CommentRepository;
import com.github.blog.repository.PostRepository;
import com.github.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<CommentResponseDto> getAllComments() {
		List<Comment> comments = commentRepository.findAll();
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentResponseDto getCommentById(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		return mapToDto(comment);

	}

	@Override
	public List<CommentResponseDto> getCommentsByPostId(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		return post.getComments().stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public List<CommentResponseDto> getCommentsByPostIdAndId(Long postId, Long id) {
		List<Comment> comments = commentRepository.findByPostIdAndId(postId, id);
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentResponseDto createComment(CommentDto commentDto) {
		Post post = postRepository.getById(commentDto.getPostId());
		Comment comment = mapToEntity(commentDto);
		comment.setPost(post);
		commentRepository.save(comment);
		return mapToDto(comment);
	}

	@Override
	public CommentResponseDto updateComment(Long id, CommentDto commentDto) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		commentRepository.save(comment);
		return mapToDto(comment);
	}

	@Override
	public void deleteComment(Long id) {
		Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
		commentRepository.delete(comment);
	}

	private CommentResponseDto mapToDto(Comment comment) {
		CommentResponseDto commentDto = new CommentResponseDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		commentDto.setPost(comment.getPost());
		return commentDto;

	}

	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}

}
