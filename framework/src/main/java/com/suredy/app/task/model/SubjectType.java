package com.suredy.app.task.model;

public enum SubjectType {

	Person(1), WorkGroup(2);

	private final Integer type;

	public static SubjectType parse(Integer value) {
		SubjectType ret = null;
		switch (value) {
			case 1:
				ret = Person;
				break;
			case 2:
				ret = WorkGroup;
				break;			
			default:
				break;
		}
		return ret;
	}
	
	public Integer getType(){
		return type;
	}

	SubjectType(Integer value) {
		this.type = value;
	}

	public String getDescription() {
		String ret = null;
		switch (this.type) {

			case 1:
				ret = "个人";
				break;
			case 2:
				ret = "班组";
				break;
			default:
				break;
		}
		return ret;
	}

}
