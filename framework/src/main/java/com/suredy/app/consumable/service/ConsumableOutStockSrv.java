package com.suredy.app.consumable.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumOutStock;
import com.suredy.core.service.BaseSrvWithEntity;

@Service
public class ConsumableOutStockSrv extends BaseSrvWithEntity<ConsumOutStock>{
	
	
	
	public List<ConsumOutStock> lookConsumableAnddEquip(String consumId,int page,int pageSize){
		List<ConsumOutStock> consumlist=new ArrayList<ConsumOutStock>();
		String sql="select co.departName,co.stockNum,co.outStocktime,"
					+" co.userName,et.assetId,et.equipModel,"
					+" t.typeName,co.id from  t_app_consumableoutstock co "
					+" join t_app_equipasset et on co.equipId=et.id"
					+" join t_app_type t on et.assetType=t.id"
					+" where co.consumableId='"+consumId+"' order by co.outStocktime desc ";
		List list=this.readPageBySQL(sql, page, pageSize);	
		if(list.size()>0){
			for (Object object : list) {
				ConsumOutStock con=new ConsumOutStock();	
				con.setDepartName(((Object[])object)[0]==null?"":((Object[])object)[0].toString());
				con.setStockNum(((Object[])object)[1]==null?"":((Object[])object)[1].toString());
				String	outtime=((Object[])object)[2]==null?"":((Object[])object)[2].toString();
				if(!StringUtils.isEmpty(outtime)){
					SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
					try {
						con.setOutStocktime(df.parse(outtime));
					} catch (ParseException e) {
						System.out.println("出库时间转换出错了");
					}
					}
						
				con.setUserName(((Object[])object)[3]==null?"":((Object[])object)[3].toString());
				con.setAssetId(((Object[])object)[4]==null?"":((Object[])object)[4].toString());
				con.setEquipModel(((Object[])object)[5]==null?"":((Object[])object)[5].toString());
				con.setTypeName(((Object[])object)[6]==null?"":((Object[])object)[6].toString());
				con.setId(((Object[])object)[7]==null?"":((Object[])object)[7].toString());
				consumlist.add(con);
			}
		}
		
		return consumlist;	
	}

	public int Count(String consumableid) {
		String sql="select count(co.id) from  t_app_consumableoutstock co "
				+" join t_app_equipasset et on co.equipId=et.id"
				+" join t_app_type t on et.assetType=t.id"
				+" where co.consumableId='"+consumableid+"' order by co.outStocktime desc ";
		int count=this.getCountBySQL(sql);
		return count;
	}

}
