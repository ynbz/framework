package com.suredy.test.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.test.model.WorkFlow;

/**   
* @Title WorkFlowSrv 
* @Package com.suredy.app.workflow.service
* @Description 工作单业务类
* @author sdkj
* @date 2017-02-06
*/
@Service("WorkFlowSrv")
public class WorkFlowSrv extends BaseSrvWithEntity<WorkFlow> {
	
	@Override
	public DetachedCriteria getDc(WorkFlow t) {
		DetachedCriteria dc = super.getDc(t);
		
		if (t == null) return dc;
		
		if(t.getProcessId() != null) {
			dc.add(Restrictions.eq("processId", t.getProcessId()));
		}
	
		return dc;
	}
	
	public WorkFlow getByProcessId(String processId) {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		WorkFlow po = new WorkFlow();
		po.setProcessId(processId);
		po = this.readSingleByEntity(po);
		return po;
	}

}
