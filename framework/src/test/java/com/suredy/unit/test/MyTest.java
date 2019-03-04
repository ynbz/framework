package com.suredy.unit.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.suredy.security.entity.OrgEntity;
import com.suredy.security.service.OrgSrv;

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/springMVC-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class MyTest {

	@Autowired
	private OrgSrv orgSrv;

	@Test
	public void test() {
		OrgEntity e = orgSrv.readSingleByEntity(null);

		this.orgSrv.update(e);
	}

}
