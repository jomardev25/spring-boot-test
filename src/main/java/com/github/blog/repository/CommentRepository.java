package com.github.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.blog.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPostIdAndId(Long postId, Long id);
}
