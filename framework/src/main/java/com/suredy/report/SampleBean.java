package com.suredy.report;

import java.util.ArrayList;
import java.util.List;

public class SampleBean {
	public static List<SampleEntity> getEntities(){
		List<SampleEntity> list = new ArrayList<SampleEntity>();
		list.add(new SampleEntity("01", "张三", "北京", "三里屯"));
		list.add(new SampleEntity("02", "李四", "上海", "浦东"));
		list.add(new SampleEntity("03", "王二", "昆明", "五华"));
		return list;
	}
}
