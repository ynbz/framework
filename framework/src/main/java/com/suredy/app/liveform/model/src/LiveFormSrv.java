package com.suredy.app.liveform.model.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import com.suredy.app.liveform.model.LiveForm;
import com.suredy.core.helper.ApplicationContextHelper;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.formbuilder.eav.srv.FormEntrySrv;

/**
 * 动态表单服务类
 * 
 * @author VIVID.G
 * @since 2017-3-13
 * @version v0.1
 */
@Service
public class LiveFormSrv extends BaseSrvWithEntity<LiveForm> {

	@Override
	public DetachedCriteria getDc(LiveForm t, String alias) {
		return this.putClause(null, t, alias);
	}

	@Override
	public DetachedCriteria putClause(DetachedCriteria dc, LiveForm t, String alias) {
		if (dc == null)
			dc = super.getDc(t, alias);

		if (t == null)
			return dc;

		if (!StringUtils.isBlank(t.getId())) {
			dc.add(Restrictions.eq("id", t.getId()));
		}
		if (!StringUtils.isBlank(t.getTitle())) {
			dc.add(Restrictions.like("title", t.getTitle(), MatchMode.ANYWHERE));
		}
		if (!StringUtils.isBlank(t.getFileTypeName())) {
			dc.add(Restrictions.eq("fileTypeName", t.getFileTypeName()));
		}
		if (!StringUtils.isBlank(t.getFileTypeId())) {
			dc.add(Restrictions.eq("fileTypeId", t.getFileTypeId()));
		}
		if (t.getFormEntry() != null) {
			FormEntrySrv srv = ApplicationContextHelper.getBeanByType(FormEntrySrv.class);
			srv.putClause(dc.createCriteria("formEntry", JoinType.LEFT_OUTER_JOIN), t.getFormEntry());

		}

		return dc;
	}
	
	
	/**
	 * 得到数据列表
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LiveForm> getAll(int page, int size,String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		LiveForm search = new LiveForm();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("createTime"));
		if(!StringUtils.isBlank(fileTypeCode)){
			dc.add(Restrictions.eq("fileTypeCode",fileTypeCode));
		}
		
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("createTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("createTime",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// TODO:
		@SuppressWarnings("unchecked")
		List<LiveForm> pos =(List<LiveForm>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		LiveForm search = new LiveForm();
		DetachedCriteria dc = this.getDc(search);
		if(!StringUtils.isBlank(fileTypeCode)){
			dc.add(Restrictions.eq("fileTypeCode",fileTypeCode));
		}
		
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("createTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("createTime",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// TODO:
		@SuppressWarnings("unchecked")
		List<LiveForm> pos =(List<LiveForm>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}

}
