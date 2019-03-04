/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * 
 * @since 2017年3月29日
 * @version 1.0
 */
package com.suredy.app.task.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.task.entity.ScheduleEntity;
import com.suredy.core.service.BaseSrvWithEntity;

/**
 * @author ZhangMaoren
 *
 */
@Service("ScheduleSrv")
public class ScheduleSrv extends BaseSrvWithEntity<ScheduleEntity> {

	@Override
	public DetachedCriteria getDc(ScheduleEntity t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}

		if (!StringUtils.isBlank(t.getName())) {
			dc.add(Restrictions.eq("name", t.getName()));
		}

		if (!StringUtils.isBlank(t.getSubjectId())) {
			dc.add(Restrictions.like("subjectId", t.getSubjectId()));
		}

		if (t.getSubjectType() != null) {
			dc.add(Restrictions.like("subjectType", t.getSubjectType()));
		}


		return dc;
	}

	public List<ScheduleEntity> getSchedules(String startTime, String endTime) {
		Date start = null, end = null;
		Calendar calendar = Calendar.getInstance();
		if (StringUtils.isNotEmpty(startTime)) {
			calendar.setTimeInMillis(Long.parseLong(startTime));
			start = calendar.getTime();
		}
		if (StringUtils.isNotEmpty(endTime)) {
			calendar.setTimeInMillis(Long.parseLong(endTime));
			end = calendar.getTime();
		}
		String sql = "SELECT id, CLASSNAME, CREATETIME, CREATORID, CREATORNAME, DESCRIPTION, ENDTIME, NAME, STARTTIME, SUBJECTID, SUBJECTNAME, SUBJECTTYPE FROM T_APP_SCHEDULE";
		sql += " WHERE (STARTTIME>=? AND ENDTIME<=?)"; //start,end
		sql += " OR (STARTTIME<? AND ENDTIME>?)"; //start, end
		sql += " OR (STARTTIME<? AND ENDTIME>? AND ENDTIME<?)"; //start, start, end
		sql += " OR (STARTTIME>? AND STARTTIME<? AND ENDTIME>?)"; //start, end, end
		sql += " ORDER BY STARTTIME";

		List<ScheduleEntity> ret = this.readWithClassBySQL(sql, ScheduleEntity.class, start, end, start, end, start, start, end, start, end, end);
		return ret;
	}

}
