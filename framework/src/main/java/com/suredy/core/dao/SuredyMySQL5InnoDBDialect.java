package com.suredy.core.dao;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.CharacterType;

public class SuredyMySQL5InnoDBDialect extends MySQL5InnoDBDialect {

	public SuredyMySQL5InnoDBDialect() {
		super();
		registerFunction("CONVERT_GBK", new SQLFunctionTemplate(CharacterType.INSTANCE, "convert(?1 USING GBK)"));
	}

}
