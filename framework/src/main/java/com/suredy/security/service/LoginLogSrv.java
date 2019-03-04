package com.suredy.security.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.security.entity.LoginLogEntity;

/**
 * @author SDKJ
 *
 */
@Service("LoginLogSrv")
public class LoginLogSrv extends BaseSrvWithEntity<LoginLogEntity>{
	
	/**
	 * 得到记录列表
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LoginLogEntity> getAll(int page, int size,String startTime,String endTime,String loginName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		LoginLogEntity search = new LoginLogEntity();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("logindate"));
		
		if(!StringUtils.isBlank(loginName)){
			dc.add(Restrictions.like("loginName", "%"+loginName+"%"));
		}
		
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("logindate",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("logindate",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// TODO:
		List<LoginLogEntity> pos =(List<LoginLogEntity>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime,String loginName) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		LoginLogEntity search = new LoginLogEntity();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("logindate"));
		
		if(!StringUtils.isBlank(loginName)){
			dc.add(Restrictions.like("loginName", "%"+loginName+"%"));
		}
		
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("logindate",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("logindate",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// TODO:
		List<LoginLogEntity> pos =(List<LoginLogEntity>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}

}
