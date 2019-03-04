package com.suredy.app.equipasset.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.equipasset.model.EquipAssetUpdateLog;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class EquipAssetUpdateLogSrv extends BaseSrvWithEntity<EquipAssetUpdateLog>{
	
	@Override
	public DetachedCriteria getDc(EquipAssetUpdateLog t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (t.getEqId() != null) {
			dc.add(Restrictions.eqOrIsNull("eqId", t.getEqId()));
		}
		
		return dc;
	}

	@SuppressWarnings("unchecked")
	public List<EquipAssetUpdateLog> getList(String startTime,String endTime,Integer page, Integer size) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		EquipAssetUpdateLog search = new EquipAssetUpdateLog();
		DetachedCriteria dc = this.getDc(search);
	
		try {
			if(startTime!=null&&!startTime.isEmpty())
				dc.add(Restrictions.ge("updateEq",formatter.parse(startTime)));
			else if(endTime!=null&&!endTime.isEmpty())
				dc.add(Restrictions.le("updateEq",formatter.parse(endTime)));
			else if(startTime!=null&&endTime!=null&&!startTime.isEmpty()&&!endTime.isEmpty())
				dc.add(Restrictions.between("updateEq",formatter.parse(startTime),formatter.parse(endTime)));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		// TODO:
		List<EquipAssetUpdateLog> pos =(List<EquipAssetUpdateLog>)this.readPageByCriteria(dc,page, size);
		return pos;
	}
	
	public Long count(String startTime,String endTime) {
		String ql = "";
		Object ret = null;
		ql = "SELECT COUNT(*) FROM EquipAssetUpdateLog T WHERE 1=1";
		if(startTime!=null&&!startTime.isEmpty()&&(endTime==null||endTime.isEmpty()))
			ql+=" AND UNIX_TIMESTAMP(T.updateEq) >= UNIX_TIMESTAMP('"+startTime+"')";
		if(endTime!=null&&!endTime.isEmpty()&&(startTime==null||startTime.isEmpty()))
			ql+=" AND UNIX_TIMESTAMP(T.updateEq) <= UNIX_TIMESTAMP('"+endTime+"')";
		if(startTime!=null&&endTime!=null&&!startTime.isEmpty()&&!endTime.isEmpty())
			ql+=" AND UNIX_TIMESTAMP(T.updateEq) >= UNIX_TIMESTAMP('"+startTime+"') AND UNIX_TIMESTAMP(T.createTime) <= UNIX_TIMESTAMP('"+endTime+"')";
		
		ret = this.readSingleByQL(ql);
		return (Long) ret;
	}

}
