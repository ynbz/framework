package com.suredy.app.equipoutstorage.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.equipasset.model.EquipAsset;
import com.suredy.app.equipoutstorage.model.EquipOutStorage;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service
public class EquipOutStorageSrv extends BaseSrvWithEntity<EquipOutStorage>{
	
	@Override
	public DetachedCriteria getDc(EquipOutStorage t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (t.getAssetId() != null) {
			dc.add(Restrictions.eqOrIsNull("assetId", t.getAssetId()));
		}
		if(t.getEquipStatus()!=null){
			dc.add(Restrictions.eqOrIsNull("equipStatus", t.getEquipStatus()));
		}
		if(t.getBackStatus()!=null){
			
			dc.add(Restrictions.eqOrIsNull("backStatus", t.getBackStatus()));
		}
		return dc;
	}
	public List<EquipOutStorage> getEquipOutStorage(String assetId){
		EquipOutStorage es=new EquipOutStorage();
		es.setAssetId(assetId);
		List<EquipOutStorage> esList= this.readByEntity(es);
		return esList;		
	}
	/**
	 * 
	 * @param equipOutStorage
	 * @return 出库数据状态跟新
	 */
	public Boolean updateOutStorangeed(EquipOutStorage equipOutStorage) {
		EquipOutStorage es=new EquipOutStorage();
		es.setAssetId(equipOutStorage.getAssetId());
		es.setEquipStatus("3");
		OrderEntity order=new OrderEntity();
		order.desc("dataCreateTime");
		EquipOutStorage eos=this.readSingleByEntity(es,order);
		
		if (eos == null) return false;
		
		try {
			EquipOutStorage dest = new EquipOutStorage();
			BeanUtils.copyProperties(dest, eos);
			
			dest.setId(null);		
			dest.setEquipStatus("4");
			dest.setReceiveDate(equipOutStorage.getReceiveDate());
			dest.setBackStatus(0);
			dest.setUpdatetime(new Date());
			EquipOutStorage eos1=this.save(dest);
			if(eos1.getEquipStatus().equals("4")){
				return true;
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	

	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param assetId
	 * @return 获取应归还未归还的设备出库信息
	 */
	public List<EquipOutStorage> getEquipOutStorageList(int page,int pageSize,String assetId) {
		String sql = "SELECT tos.assetId,tt.typeName,tea.equipModel,tos.receiveDate,tos.beakTime,tos.recipients,tos.userName,"
				+" tos.userDepartName,tos.deadline,tos.promiseTime,tos.comm from t_app_outstorage tos"
				+" join t_app_equipasset tea on tos.assetId=tea.assetId"
				+" join t_app_type tt on tt.id=tea.assetType"
				+" where  tos.promiseTime is not null and tos.promiseTime!='' and tos.promiseTime<now() "
				+" and backStatus=0 and tos.equipStatus='4'";
		if(!StringUtils.isEmpty(assetId)){
			sql+=" and tos.assetId='"+assetId+"'";
		}
		List<EquipOutStorage> eoslist=new ArrayList<EquipOutStorage>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		List list=this.readPageBySQL(sql, page, pageSize);
		for (Object object : list) {
			EquipOutStorage es=new EquipOutStorage();
			
				es.setAssetId(((Object[])object)[0]==null?"":((Object[])object)[0].toString());
			
				es.setTypeName(((Object[])object)[1]==null?"":((Object[])object)[1].toString());
			
				es.setEquipModel(((Object[])object)[2]==null?"":((Object[])object)[2].toString());
			try {		
				String  rt=((Object[])object)[3]==null?"":((Object[])object)[3].toString();				
			if(!StringUtils.isEmpty(rt)){
					es.setReceiveDate(sf.parse(rt));
			}									
			String pt=((Object[])object)[9]==null?"":((Object[])object)[9].toString();
			if(!StringUtils.isEmpty(pt)){
				es.setPromiseTime(sf.parse(pt));
			}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
				es.setRecipients(((Object[])object)[5]==null?"":((Object[])object)[5].toString());
				es.setUserName(((Object[])object)[6]==null?"":((Object[])object)[6].toString());
				es.setUserDepartName(((Object[])object)[7]==null?"":((Object[])object)[7].toString());
			
				es.setDeadline(((Object[])object)[8]==null?"":((Object[])object)[8].toString());

				
			eoslist.add(es);
		}
		return eoslist;
	}
	
	public int Count(String assetId) {
		String sql = "SELECT count(*) from t_app_outstorage tos"
				+" join t_app_equipasset tea on tos.assetId=tea.assetId"
				+" join t_app_type tt on tt.id=tea.assetType"
				+" where  tos.promiseTime is not null and tos.promiseTime!='' and tos.promiseTime<now()"
				+" and backStatus=0 and tos.equipStatus='4'" ;
		if(!StringUtils.isEmpty(assetId)){
			sql+=" and tos.assetId='"+assetId+"'";
		}
		int count= this.getCountBySQL(sql);
		return count;
	}

	@Transactional
	public Boolean updateOutBacktime(EquipAsset equipAsset,String backtime){
		
		int i=0;
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		EquipOutStorage es=new EquipOutStorage();
		es.setAssetId(equipAsset.getAssetId());
		es.setBackStatus(0);
		EquipOutStorage es1=this.readSingleByEntity(es);
		
		
		if(es1==null){
			try {
			es.setBeakTime(sf.parse(backtime));
			} catch (ParseException e) {
				
			}
			es.setBackStatus(1);
			es.setEquipModel(equipAsset.getEquipModel());
			es.setUserId(equipAsset.getUserID());
			es.setUserName(equipAsset.getUserName());
			es.setRecipients(equipAsset.getResponsible());
			es.setRecipientsId(equipAsset.getResponsibleId());
			es.setReceiveDate(equipAsset.getReceiveDate());
			es.setEquipStatus("4");
			es.setUpdatetime(new Date());
			es.setIsApplyfor("2");
			EquipOutStorage es2=this.saveOrUpdate(es);
			if(es2!=null){
				i++;
			}
		}else{
			String sql="update t_app_outstorage set beakTime='"+backtime+"',backStatus=1,updatetime='"+sf.format(new Date())+"',isApplyfor='2' where assetId='"+equipAsset.getAssetId()+"' and backStatus=0 and equipStatus='4'";
			
			i=this.updateBySQL(sql);
		}
		
		return i>0?true:false;		
	}
}
