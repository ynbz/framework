package com.suredy.app.project.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.project.entity.ProjectEntity;
import com.suredy.core.service.BaseSrvWithEntity;

/**
 * @Title ProjectSrv
 * @Package com.suredy.app.project.service
 * @Description 生产计划管理业务类
 * @author zyh
 * @date 2017-04-07
 *
 */
@Service
public class ProjectSrv extends BaseSrvWithEntity<ProjectEntity> {
	

	@Override
	public DetachedCriteria getDc(ProjectEntity t) {
		DetachedCriteria dc = super.getDc(t);
		
		if (t == null) return dc;
		
		if(t.getProcessId() != null) {
			dc.add(Restrictions.eq("processId", t.getProcessId()));
		}
	
		return dc;
	}
	
	public ProjectEntity getByProcessId(String processId) {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		ProjectEntity po = new ProjectEntity();
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
	public List<ProjectEntity> getAll(int page, int size,String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		ProjectEntity search = new ProjectEntity();
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
		List<ProjectEntity> pos =(List<ProjectEntity>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime,String fileTypeCode) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		ProjectEntity search = new ProjectEntity();
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
		List<ProjectEntity> pos =(List<ProjectEntity>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
	}

}
