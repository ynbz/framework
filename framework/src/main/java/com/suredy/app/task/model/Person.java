package com.suredy.app.task.model;

import com.suredy.security.entity.UserEntity;


public class Person extends UserEntity implements TaskSubject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6083446067223058858L;

	@Override
	public Integer getType() {
		return SubjectType.Person.getType();
	}

	@Override
	public void setType(Integer type) {
		return;
	}

}
