package com.github.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5447505662615795310L;

	private String resource;
	private String field;
	private Object value;


	public ResourceNotFoundException(String resource, String field, Object value) {
		super(String.format("%s not found with %s: %s ", resource, field, value));
		this.resource = resource;
		this.field = field;
		this.value = value;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public Object getValue() {
		return value;
	}
}
