package com.github.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String resource;
	private final String field;
	private final Object value;

	public ResourceAlreadyExistsException(String resource, String field, Object value) {
		super(String.format("%s %s \"%s\" already exists.", resource, field, value));
		this.resource = resource;
		this.field = field;
		this.value = value;
	}
}
