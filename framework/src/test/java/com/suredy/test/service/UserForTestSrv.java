package com.suredy.test.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.test.model.UserForTest;

@Service
public class UserForTestSrv extends BaseSrvWithEntity<UserForTest> {

	public UserForTestSrv() {
		this.addAnDefOrder("name", true);
	}

	@Override
	public DetachedCriteria getDc(UserForTest t) {
		DetachedCriteria dc = super.getDc(t);

		if (StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.eq("name", t.getName()));
		}
		if (StringUtils.isBlank(t.getPswd())) {
			dc.add(Restrictions.eq("pswd", t.getPswd()));
		}

		return super.getDc(t);
	}

	public UserForTest getUser(String name, String pswd) {
		if (StringUtils.isBlank(name) || StringUtils.isBlank(pswd))
			throw new IllegalArgumentException("Invalid parameters.");

		UserForTest search = new UserForTest();
		search.setName(name);
		search.setPswd(pswd);

		return this.readSingleByEntity(search);
	}

}
