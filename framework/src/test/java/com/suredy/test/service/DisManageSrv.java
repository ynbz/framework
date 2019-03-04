package com.suredy.test.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.dbmanage.service.DbManageSrv;
import com.suredy.test.model.DisManage;

/**
 * @Title DisManageSrv
 * @Package com.suredy.test.service
 * @Description 发文业务类
 * @author zyh
 * @date 2017-03-09
 *
 */
@Service("DisManageSrv")
public class DisManageSrv extends BaseSrvWithEntity<DisManage> {
	
	@Override
	public DetachedCriteria getDc(DisManage t) {
		DetachedCriteria dc = super.getDc(t);
		
		if (t == null) return dc;
		
		if(t.getProcessId() != null) {
			dc.add(Restrictions.eq("processId", t.getProcessId()));
		}
	
		return dc;
	}
	
	public DisManage getByProcessId(String processId) {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		DisManage po = new DisManage();
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
	public List<DisManage> getAll(int page, int size,String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		DisManage search = new DisManage();
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
		List<DisManage> pos =(List<DisManage>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		DisManage search = new DisManage();
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
		List<DisManage> pos =(List<DisManage>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}
	
	
}
