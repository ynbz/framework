package com.suredy.app.consumable.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumableAndEquipType;
import com.suredy.core.service.BaseSrvWithEntity;

@Service 
public class ConAndEquipSrv extends BaseSrvWithEntity<ConsumableAndEquipType>{
	@Override
	public DetachedCriteria getDc(ConsumableAndEquipType t) {
		DetachedCriteria dc = super.getDc(t);
		if (t == null) {
			return dc;
		}
		if (t.getConsumableID() != null) {
			dc.add(Restrictions.eqOrIsNull("consumableID", t.getConsumableID()));
		}
		if (t.getEquipTypeID() != null) {
			dc.add(Restrictions.eqOrIsNull("equipTypeID", t.getEquipTypeID()));
		}
		return dc;		
		}
	
	
	/**
	 * 
	 * @param consumID
	 * @return 获取关联设备类型
	 */
	public String getEquipmentTypeName(String consumID){
		String equipTypeName="";
		String sql="SELECT t.typeName FROM t_app_consumerAndEquipType ct JOIN t_app_type t ON ct.equipTypeID = t.id where  ct.consumableID='"+consumID+"'";
		List typenames=this.readBySQL(sql);
		if(typenames.size()>0){
			for (Object object : typenames) {
				equipTypeName+=object.toString()+",";
			}
		}
		return equipTypeName;	
	}
	
	
	/**
	 * 
	 * @param consumID
	 * @return 获取关联设备的id
	 */
	public String getEquipmentTypeID(String consumID){
		String equipID="";
		ConsumableAndEquipType cet=new ConsumableAndEquipType();
		cet.setConsumableID(consumID);
		List<ConsumableAndEquipType> cets=this.readByEntity(cet);
		if(cets!=null){
			for (ConsumableAndEquipType c : cets) {
				equipID+=c.getEquipTypeID()+",";
			}
		}
		
		return equipID;	
	}


	public void deleteData(String id) {
		String ql="delete from ConsumableAndEquipType t where t.consumableID='"+id+"'";
		int s=this.deleteByQL(ql);
		System.out.println(s);
	}
}
