package com.suredy.test.dictionary;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.suredy.core.model.Dictionary;
import com.suredy.core.service.DictionarySrv;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/springMVC-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDictionary {

	@Autowired
	private DictionarySrv srv;

	@Test
	public void testAdd() {
		System.out.println(srv.add("ReportController", "1个值", false));
		System.out.println(srv.add("ReportController", "2个值", false));

		List<Dictionary> dics = this.srv.getDics("ReportController", false);

		System.out.println(dics == null ? null : dics.size());

		List<String> vals = this.srv.getVals("ReportController", false);

		System.out.println(vals);

		if (dics != null)
			for (Dictionary d : dics) {
				this.srv.delete(d.getId());
			}
	}

}
