package com.suredy.app.consumable.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.consumable.model.ConsumProperty;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service
public class ConsumPropertySrv extends BaseSrvWithEntity<ConsumProperty>{
	
	@Override
	public DetachedCriteria getDc(ConsumProperty t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if(t.getType() != null) {
			dc.add(Restrictions.eqOrIsNull("type", t.getType()));
		}
		if(t.getSort() != null) {
			dc.add(Restrictions.ge("sort", t.getSort()));
		}
		if(t.getIsShow() != null) {
			dc.add(Restrictions.eq("isShow", t.getIsShow()));
		}
		return dc;
		}
		/**
		 * 
		 * @param nodeid
		 * @return 判断物品类型是否是子节点 确定是否有属性
		 */
		public String getIsChildNode(String nodeid){
			String sql="select ischildnode from t_app_consumtype where id='"+nodeid+"'";
			Object object=this.readSingleBySQL(sql);
			String flg=object.toString();
			return flg;	
		}
		
		public List<ConsumProperty> getConsumProperData(String tpyeid,int page,int pageSize){
			ConsumProperty search=new ConsumProperty();
			ConsumerType t = new ConsumerType();
			OrderEntity oe = new OrderEntity();
			oe.asc("sort");
			if (!StringUtils.isBlank(tpyeid)) {				
				t.setId(tpyeid);
				search.setType(t);
			}			
			List<ConsumProperty> cf=(List<ConsumProperty>) this.readPageByEntity(search, page, pageSize, oe);
			return cf;			 
			
		}
		
		public int getCountData(String nodeid){
			ConsumProperty search=new ConsumProperty();
			ConsumerType t = new ConsumerType();
			if (!StringUtils.isBlank(nodeid)) {				
				t.setId(nodeid);
				search.setType(t);
			}	
			int count=this.getCountByEntity(search);
			return count;
		}
		
		/**
		 * 
		 * @param cs
		 * @return
		 * 查询出是否存在当前排序的数据 
		 * 	如果存在查询大于等于当前排序的数据 并让所有排序加1 返回所有数据
		 * 
		 */
		public List<ConsumProperty> getSortAndTypeList(ConsumProperty cs){
			ConsumProperty search=new ConsumProperty();
			search.setType(cs.getType());
			search.setSort(cs.getSort());
			List<ConsumProperty> list=this.readByEntity(search);
			List<ConsumProperty> datelist=new ArrayList<ConsumProperty>();								
			for (ConsumProperty cp : list) {
				cp.setSort(cp.getSort()+1);
				if(StringUtils.isEmpty(cs.getId())){	
					datelist.add(cp);
				}else if(!cs.getId().equals(cp.getId())){
					datelist.add(cp);
				}else{
					cs.setField(cp.getField());
				}							
			}	
			datelist.add(cs);	
			return datelist;			
		}
		
		public ConsumProperty getByIdClassify(String dataId) {
			ConsumProperty data = this.get(dataId);
			return data;
		}


		public boolean deletedata(String ds) {
			String[] dss=ds.split(",");
			String ql="delete from ConsumProperty where id=?";
			int y=0;
			for(int i=1;i<dss.length;i++){
				ConsumProperty data=this.getByIdClassify(dss[i]);
				y=y+this.deleteByQL(ql, dss[i]);
			//	String sql="update t_app_equipasset set "+data.getField()+"='' where assettype='"+data.getType().getId()+"'";
			//	if(!StringUtils.isEmpty(data.getField()))
				//this.updateBySQL(sql);
			}	
			return y>0?true:false;	
		}
		
		/**
		 * 
		 * @param typeId
		 * @return
		 *  通过设备类型id获取所有表头显示的数据
		 * 
		 */
		public List<ConsumProperty> getConsumPropertyIsShow(String typeId) {
			ConsumProperty search=new ConsumProperty();
			search.setIsShow(1);
			ConsumerType t = new ConsumerType();
			OrderEntity oe = new OrderEntity();
			oe.asc("sort");
			if (!StringUtils.isBlank(typeId)) {				
				t.setId(typeId);
				search.setType(t);
			}	
			List<ConsumProperty> cf=(List<ConsumProperty>) this.readByEntity(search,oe);
			return cf;	
		}
		
}
