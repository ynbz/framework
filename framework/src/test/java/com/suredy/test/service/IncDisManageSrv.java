package com.suredy.test.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.test.model.IncDisManage;

/**
 * @Title IncDisManageSrv
 * @Package com.suredy.test.service
 * @Description 收文业务类
 * @author zyh
 * @date 2017-03-15
 *
 */
@Service
public class IncDisManageSrv extends BaseSrvWithEntity<IncDisManage> {

	
	@Override
	public DetachedCriteria getDc(IncDisManage t) {
		DetachedCriteria dc = super.getDc(t);
		
		if (t == null) return dc;
		
		if(t.getProcessId() != null) {
			dc.add(Restrictions.eq("processId", t.getProcessId()));
		}
	
		return dc;
	}
	
	public IncDisManage getByProcessId(String processId) {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		IncDisManage po = new IncDisManage();
		po.setProcessId(processId);
		po = this.readSingleByEntity(po);
		return po;
	}
	
	/**
	 * 得到数据列表
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<IncDisManage> getAll(int page, int size,String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		IncDisManage search = new IncDisManage();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("createTime"));
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("createTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("createTime",formatter.parse(endTime)));
			}
			if(!StringUtils.isBlank(fileTypeCode)){
				dc.add(Restrictions.eq("fileTypeCode",fileTypeCode));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// TODO:
		@SuppressWarnings("unchecked")
		List<IncDisManage> pos =(List<IncDisManage>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		IncDisManage search = new IncDisManage();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("createTime"));
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("createTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("createTime",formatter.parse(endTime)));
			}
			if(!StringUtils.isBlank(fileTypeCode)){
				dc.add(Restrictions.eq("fileTypeCode",fileTypeCode));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// TODO:
		@SuppressWarnings("unchecked")
		List<IncDisManage> pos =(List<IncDisManage>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}
	
	
}
