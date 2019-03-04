package com.suredy.app.equipasset.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Myannotation {
	String description();
	
}
