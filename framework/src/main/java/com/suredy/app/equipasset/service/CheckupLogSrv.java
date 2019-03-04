package com.suredy.app.equipasset.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.equipasset.model.CheckupLog;
import com.suredy.core.service.BaseSrvWithEntity;

/**
 * 巡检数据服务
 * 
 * @author VIVID.G
 * @since 2016-8-2
 * @version v0.1
 */
@Service
public class CheckupLogSrv extends BaseSrvWithEntity<CheckupLog> {

	public CheckupLogSrv() {
		this.addAnDefOrder("time", false);
	}

	@Override
	public DetachedCriteria getDc(CheckupLog t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getUser())) {
			dc.add(Restrictions.like("user", t.getUser(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(t.getPlace())) {
			dc.add(Restrictions.eq("place", t.getPlace()));
		}
		if (!StringUtils.isBlank(t.getAssetId())) {
			dc.add(Restrictions.eq("assetId", t.getAssetId()));
		}

		return dc;
	}

}
