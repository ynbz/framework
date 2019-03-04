package com.suredy.app.notice.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.notice.entity.NoticeEntity;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

/**
 * 公告业务
 * @author zyh
 *
 */
@Service
public class NoticeSrv extends BaseSrvWithEntity<NoticeEntity> {
	

	public List<NoticeEntity> getAll(Integer page, Integer size) {
		OrderEntity oe = new OrderEntity();
		oe.desc("createDate");
		NoticeEntity search = new NoticeEntity();
		List<NoticeEntity> pos =(List<NoticeEntity>) this.readPageByEntity(search, page, size, oe);
		return pos;
	}
	
	public Integer Count() {
		NoticeEntity search = new NoticeEntity();
		return this.getCountByEntity(search);
	}
	
	public List<NoticeEntity> getAll(List<String> units) {
		NoticeEntity search = new NoticeEntity();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("createDate"));
		dc.add(Restrictions.or(Restrictions.eq("type","1"),Restrictions.in("issueUnitId", units)));
		@SuppressWarnings("unchecked")
		List<NoticeEntity> pos =(List<NoticeEntity>) this.readByCriteria(dc);
		return pos == null?null:pos;
	}

}
