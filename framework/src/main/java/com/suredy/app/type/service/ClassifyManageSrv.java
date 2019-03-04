package com.suredy.app.type.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.type.model.ClassifyManage;
import com.suredy.app.type.model.Type;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;

@Service
public class ClassifyManageSrv extends BaseSrvWithEntity<ClassifyManage>{
	
	@Override
	public DetachedCriteria getDc(ClassifyManage t) {
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
		if(t.getIsSearch() != null) {
			dc.add(Restrictions.ge("isSearch", t.getIsSearch()));
		}
		if(t.getIsPrimeAttribute() != null) {
			dc.add(Restrictions.ge("isPrimeAttribute", t.getIsPrimeAttribute()));
		}
		if(t.getIsNeed() != null) {
			dc.add(Restrictions.ge("isNeed", t.getIsNeed()));
		}
		return dc;
		}
		/**
		 * 
		 * @param nodeid
		 * @return 判断物品类型是否是子节点 确定是否有属性
		 */
		public String getIsChildNode(String nodeid){
			String sql="select ischildnode from t_app_type where id='"+nodeid+"'";
			Object object=this.readSingleBySQL(sql);
			String flg=object.toString();
			return flg;	
		}
		
		public List<ClassifyManage> getClassifyData(String tpyeid,int page,int pageSize){
			ClassifyManage search=new ClassifyManage();
			Type t = new Type();
			OrderEntity order=new OrderEntity();
			order.asc("sort");
			if (!StringUtils.isBlank(tpyeid)) {				
				t.setId(tpyeid);
				search.setType(t);
			}			
			List<ClassifyManage> cf=(List<ClassifyManage>) this.readPageByEntity(search, page, pageSize, order);
			return cf;			 
			
		}
		
		public int getCountData(String nodeid){
			ClassifyManage search=new ClassifyManage();
			Type t = new Type();
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
		public List<ClassifyManage> getSortAndTypeList(ClassifyManage cs){
			List<ClassifyManage> datelist=new ArrayList<ClassifyManage>();
			ClassifyManage search=new ClassifyManage();
			ClassifyManage cl = new ClassifyManage();
			
			DetachedCriteria dc = this.getDc(search);
			dc.add(Restrictions.eq("type",cs.getType()));
			
			if(!StringUtils.isEmpty(cs.getId())){
				cl = this.get(cs.getId());
				cs.setField(cl.getField());
				if(cl.getSort()!=cs.getSort()&&cl.getSort()>cs.getSort()){
					dc.add(Restrictions.ge("sort",cs.getSort()));
					dc.add(Restrictions.lt("sort",cl.getSort()));
				}else if(cl.getSort()!=cs.getSort()&&cl.getSort()<cs.getSort()){
					dc.add(Restrictions.le("sort",cs.getSort()));
					dc.add(Restrictions.gt("sort",cl.getSort()));
				}
			}else{
				dc.add(Restrictions.ge("sort",cs.getSort()));
			}
			
			@SuppressWarnings("unchecked")
			List<ClassifyManage> list =(List<ClassifyManage>) this.readByCriteria(dc);
			for (ClassifyManage classifyManage : list) {
				if(!StringUtils.isEmpty(cs.getId())){
					cl = this.get(cs.getId());
					if(cl.getSort()!=cs.getSort()&&cl.getSort()>cs.getSort()){
						classifyManage.setSort(classifyManage.getSort()+1);
					}else if(cl.getSort()!=cs.getSort()&&cl.getSort()<cs.getSort()){
						classifyManage.setSort(classifyManage.getSort()-1);
					}
				}else{
					classifyManage.setSort(classifyManage.getSort()+1);
				}
				datelist.add(classifyManage);				
			}
			
			datelist.add(cs);	
			return datelist;			
		}
		
		
		public ClassifyManage getByIdClassify(String dataId) {
			ClassifyManage data = this.get(dataId);
			data.setIsNeed(data.getIsNeed()==null?0:data.getIsNeed());
			data.setIsSearch(data.getIsSearch()==null?0:data.getIsSearch());
			data.setIsShow(data.getIsShow()==null?0:data.getIsShow());
			data.setIsPrimeAttribute(data.getIsPrimeAttribute()==null?0:data.getIsPrimeAttribute());
			return data;
		}


		public boolean deletedata(String ds) {
			String[] dss=ds.split(",");
			String ql="delete from ClassifyManage where id=?";
			int y=0;
			for(int i=1;i<dss.length;i++){
				ClassifyManage data=this.getByIdClassify(dss[i]);
				y=y+this.deleteByQL(ql, dss[i]);
				String sql="update t_app_equipasset set "+data.getField()+"='' where assettype='"+data.getType().getId()+"'";
				if(!StringUtils.isEmpty(data.getField()))
				this.updateBySQL(sql);
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
		public List<ClassifyManage> getClassifyDataIsShow(String typeId) {
			ClassifyManage search=new ClassifyManage();
			search.setIsShow(1);
			Type t = new Type();
			OrderEntity order=new OrderEntity();
			order.asc("sort");
			if (!StringUtils.isBlank(typeId)) {				
				t.setId(typeId);
				search.setType(t);
			}	
			List<ClassifyManage> cf=(List<ClassifyManage>) this.readByEntity(search,order);
			return cf;	
		}
		/**
		 * 
		 * @param typeId
		 * @return
		 * 	 通过设备类型id获取所有查询条件的数据
		 */
		public List<ClassifyManage> getClassifyDataIssearch(String typeId) {
			ClassifyManage search=new ClassifyManage();
			search.setIsSearch(1);
			Type t = new Type();
			OrderEntity order=new OrderEntity();
			order.asc("sort");
			if (!StringUtils.isBlank(typeId)) {				
				t.setId(typeId);
				search.setType(t);
			}	
			List<ClassifyManage> cf=(List<ClassifyManage>) this.readByEntity(search,order);
			return cf;	
		}

		/**
		 * 
		 * @param typeId
		 * @return
		 * 	 通过设备类型id获取所有主要配置属性
		 */
		public List<ClassifyManage> getPrimeAttribute(String typeId) {
			ClassifyManage search=new ClassifyManage();
			search.setIsPrimeAttribute(1);
			Type t = new Type();
			OrderEntity order=new OrderEntity();
			order.asc("sort");
			if (!StringUtils.isBlank(typeId)) {				
				t.setId(typeId);
				search.setType(t);
			}	
			List<ClassifyManage> cf=(List<ClassifyManage>) this.readByEntity(search,order);
			return cf;	
		}
		/**
		 * 
		 * @param typeId
		 * @return
		 * 	 通过设备类型id获取所需求项
		 */
		public List<ClassifyManage> getEquipIsNeed(String typeId) {
			ClassifyManage search=new ClassifyManage();
			search.setIsNeed(1);
			Type t = new Type();
			OrderEntity order=new OrderEntity();
			order.asc("sort");
			if (!StringUtils.isBlank(typeId)) {				
				t.setId(typeId);
				search.setType(t);
			}	
			List<ClassifyManage> cf=(List<ClassifyManage>) this.readByEntity(search,order);
			return cf;	
		}
}
