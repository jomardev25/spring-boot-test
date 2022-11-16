package com.github.blog.payload;
import com.github.blog.entity.Post;
import com.github.blog.entity.PostWoComment;


public class CommentResponseDto {

	private long id;
	private String name;
	private String email;
	private String body;
	private PostWoComment postWoComment;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public PostWoComment getPost() {
		return postWoComment;
	}

	public void setPost(Post post) {
		this.postWoComment = new PostWoComment();
		this.postWoComment.setId(post.getId());
		this.postWoComment.setTitle(post.getTitle());
		this.postWoComment.setDescription(post.getDescription());
		this.postWoComment.setContent(post.getContent());
	}
}
