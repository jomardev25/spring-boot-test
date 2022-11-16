package com.github.blog.annotation.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.github.blog.annotation.Unique;
import com.github.blog.annotation.interfaces.FieldValueExists;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

	@Autowired
    private ApplicationContext applicationContext;

    private FieldValueExists service;
    private String fieldName;

    @Override
    public void initialize(Unique unique) {
        Class<? extends FieldValueExists> clazz = unique.service();
        this.fieldName = unique.fieldName();
        String serviceQualifier = unique.serviceQualifier();

        if (!serviceQualifier.equals("")) {
            this.service = this.applicationContext.getBean(serviceQualifier, clazz);
        } else {
            this.service = this.applicationContext.getBean(clazz);
        }
    }

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		return !this.service.fieldValueExists(value, this.fieldName);
	}

}
