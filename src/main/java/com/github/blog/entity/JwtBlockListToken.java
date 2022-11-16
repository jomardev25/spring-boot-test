package com.github.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "jwt_blocklist_tokens")
@Data
public class JwtBlockListToken {

	@Id
	@Column(name = "token", nullable = false)
	private String token;
}
