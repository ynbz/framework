package com.suredy.app.consumable.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumableManage;
import com.suredy.app.consumable.model.ConsumableManegeE;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class ConsumableManageSrv extends BaseSrvWithEntity<ConsumableManage>{
		
	@Override
	public DetachedCriteria getDc(ConsumableManage t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (t.getType() != null) {
			dc.add(Restrictions.eqOrIsNull("type", t.getType()));
		}
		if(t.getConsumableName()!=null){
			 dc.add(Restrictions.like("consumableName","%"+ t.getConsumableName()+"%"));
		}
		if(t.getCunsumModel()!=null){
			 dc.add(Restrictions.like("cunsumModel", "%"+t.getCunsumModel()+"%"));
		}
		return dc;
	}
	
	public List<ConsumableManage> getListData(int pageIndex, int pageSize, String typeId,ConsumableManage consumableManage) {
		ConsumableManage search = consumableManage;
		if (!StringUtils.isBlank(typeId)) {
			ConsumerType t = new ConsumerType();
			t.setId(typeId);
			search.setType(t);
		}
		List<ConsumableManage> cList = this.readPageByEntity(search, pageIndex, pageSize, null);
		return cList;
	}

	public Long Count(String typeId, ConsumableManage consumabledata) {
		ConsumableManage search = consumabledata;
		if (!StringUtils.isBlank(typeId)) {
			ConsumerType t = new ConsumerType();
			t.setId(typeId);
			search.setType(t);
		}
		long ret=this.getCountByEntity(search);
		
		return  ret;
	}

	public boolean deletedata(String chechedid) {
		String[] dss=chechedid.split(",");
		String ql="delete from ConsumableManage where id in (?)";
		int y=0;
		for(int i=1;i<dss.length;i++){
			y+=this.deleteByQL(ql, dss[i]);
			}
		return y>0?true:false;	
	}

	public List<ConsumableManegeE> getconsumableList(String consumTypeid) {
		ConsumableManage search = new ConsumableManage();
		if (!StringUtils.isBlank(consumTypeid)) {
			ConsumerType t = new ConsumerType();
			t.setId(consumTypeid);
			search.setType(t);
		}
		List<ConsumableManage> listconsumable=this.readByEntity(search);
		List<ConsumableManegeE> listconsumableE=new ArrayList<ConsumableManegeE>();
		if(listconsumable!=null){
			for (ConsumableManage data : listconsumable) {
				int stock=(data.getStock()==null||data.getStock().length()<=0)?0:Integer.parseInt(data.getStock());
				int footprint=(data.getFootprint()==null||data.getFootprint().length()<=0?0:Integer.parseInt(data.getFootprint()));
				int usablestock=stock-footprint;
				if(usablestock>0){
					ConsumableManegeE dataE=new ConsumableManegeE();				
					dataE.setId(data.getId());
					dataE.setConsumableName(data.getConsumableName());
					dataE.setCunsumModel(data.getCunsumModel());
					dataE.setUsablestock(usablestock>0?usablestock+"":""+0);
					listconsumableE.add(dataE);
				}
				
			}
		}
		
		return listconsumableE;		
	}

	
}
