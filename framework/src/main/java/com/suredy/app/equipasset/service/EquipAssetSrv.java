package com.suredy.app.equipasset.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.suredy.app.equipasset.model.EquipAsset;
import com.suredy.app.equipasset.model.EquipAssetUpdateLog;
import com.suredy.app.equipasset.model.Myannotation;
import com.suredy.app.type.model.ClassifyManage;
import com.suredy.app.type.model.Type;
import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.core.service.OrderEntity;


/**
 * 
 * @author lhl
 *	
 *
 */
@Service
public class EquipAssetSrv extends BaseSrvWithEntity<EquipAsset>{

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public DetachedCriteria getDc(EquipAsset t) {
		DetachedCriteria dc = super.getDc(t);

		if (t == null) {
			return dc;
		}
		if (t.getType() != null) {
			dc.add(Restrictions.eqOrIsNull("type", t.getType()));
		}
		if(t.getSerialNum()!=null){
			dc.add(Restrictions.like("serialNum", "%"+t.getSerialNum()+"%"));
		}
		if(t.getAssetId()!=null){
			dc.add(Restrictions.eqOrIsNull("assetId", t.getAssetId()));
		}
		if(t.getRfid()!=null){
			dc.add(Restrictions.like("rfid","%"+ t.getRfid()+"%"));
		}
		if(t.getResponsible()!=null){
			dc.add(Restrictions.eqOrIsNull("responsible", t.getResponsible()));
		}
		if(t.getResponsibleId()!=null){
			dc.add(Restrictions.eqOrIsNull("responsibleId", t.getResponsibleId()));
		}
		if(t.getStatus()!=null){
			dc.add(Restrictions.eqOrIsNull("status", t.getStatus()));
		}
		if(t.getIdentifying()!=null){
			dc.add(Restrictions.eqOrIsNull("identifying", t.getIdentifying()));
		}	
		if(t.getEquipModel()!=null){
			dc.add(Restrictions.eqOrIsNull("equipModel", t.getEquipModel()));
		}
		if(t.getUpUserId()!=null){
			dc.add(Restrictions.eqOrIsNull("upUserId", t.getUpUserId()));
		}
		
		if(t.getProjectName()!=null){
			dc.add(Restrictions.like("projectName","%"+ t.getProjectName()+"%"));
		}
		if(t.getContractNumber()!=null){
			dc.add(Restrictions.like("contractNumber","%"+ t.getContractNumber()+"%"));
		}
		if(t.getUserUnit()!=null){
			dc.add(Restrictions.like("userUnit","%"+ t.getUserUnit()+"%"));
		}
		
		if(t.getIsFull()!=null){
			dc.add(Restrictions.eq("isFull",t.getIsFull()));
		}
		
		for(int i=0;i<40;i++){			 
			 if(!t.getColvalue("Col"+i).equals("")){
				 dc.add(Restrictions.like("col"+i, "%"+t.getColvalue("Col"+i)+"%"));
			 }
		}
		return dc;
	}
	
	public List<EquipAsset> getEquipData(int pageIndex, int pageSize, String typeId,EquipAsset equipasset, String sortField, String sort) {
		EquipAsset search = equipasset;
		if (!StringUtils.isBlank(typeId)) {
			Type t = new Type();
			t.setId(typeId);
			search.setType(t);
		}
		OrderEntity order=new OrderEntity();
		if (StringUtils.isNotEmpty(sortField)) {
			//buyDate
			//type
			//contractNumber
			//projectName
			//responsible
			//userName
			if ("asc".equalsIgnoreCase(sort)) {
				order.asc(sortField);
			} else {
				order.desc(sortField);
			}			
		} 
		
		List<EquipAsset> eList = this.readPageByEntity(search, pageIndex, pageSize, order);
		return eList;
	}
	
	

	@Transactional
	public List<List<String>> getDowEquipData(String typeId,EquipAsset equipasset){
		List<List<String>> listData = new  ArrayList<List<String>>();
		EquipAsset search = equipasset;
		if (!StringUtils.isBlank(typeId)) {
			Type t = new Type();
			t.setId(typeId);
			search.setType(t);
		}
		List<EquipAsset> eList = this.readByEntity(search);
		
		for(EquipAsset eq:eList){
			List<String> eqlistString = new ArrayList<String>();
	
			eqlistString.add(eq.getAssetId()!=null?eq.getAssetId():"");//资产ID
			eqlistString.add(eq.getBuyDate()!=null?formatter.format(eq.getBuyDate()):"");//购买日期
			eqlistString.add(eq.getType()!=null&&eq.getType().getTypeName()!=null?eq.getType().getTypeName():"");//设备类型
			eqlistString.add(eq.getEquipModel()!=null?eq.getEquipModel():"");//设备型号
			eqlistString.add(eq.getContractNumber()!=null?eq.getContractNumber():"");//合同号
			eqlistString.add(eq.getProjectName()!=null?eq.getProjectName():"");//项目名称
			eqlistString.add(eq.getSerialNum()!=null?eq.getSerialNum():"");//序列号
			eqlistString.add(eq.getRfid()!=null?eq.getSerialNum():"");//RFID
			eqlistString.add(eq.getCostSource()!=null?eq.getCostSource():"");//费用来源
			eqlistString.add(eq.getFinanceAssetNumber()!=null?eq.getFinanceAssetNumber():"");//财务资产编号
			eqlistString.add(eq.getSupplier()!=null?eq.getSupplier():"");//供应商
			eqlistString.add(eq.getVendor()!=null?eq.getVendor():"");//生产厂商
			eqlistString.add(eq.getIsNewEquip()!=null?(eq.getIsNewEquip()==1?"新设备":"旧设备"):"");//新旧设备
			eqlistString.add(eq.getReceiveDate()!=null?formatter.format(eq.getReceiveDate()):"");//领用日期
			eqlistString.add(eq.getUserPlace()!=null?eq.getUserPlace():"");//使用地点
			eqlistString.add(eq.getIsPublic()!=null?(eq.getIsNewEquip()==0?"合同制员工":(eq.getIsNewEquip()==1?"部门公用":"其他用工人员")):"");//使用范围
			eqlistString.add(eq.getUserUnit()!=null?eq.getUserUnit():"");//责任部门
			eqlistString.add(eq.getResponsible()!=null?eq.getResponsible():"");//责任人
			eqlistString.add(eq.getResponsiblePhone()!=null?eq.getResponsiblePhone():"");//责任人电话
			eqlistString.add(eq.getUserName()!=null?eq.getUserName():"");//使用人
			eqlistString.add(eq.getUserPhone()!=null?eq.getUserPhone():"");//使用人电话
			eqlistString.add(eq.getComm()!=null?eq.getComm():"");//备注
			
			//配置信息
			String csdataStr ="";
			List<ClassifyManage> csdata = eq.getType().getClassifyList();
			for(ClassifyManage cm:csdata){
				if(eq.getColvalue(cm.getField())!=null&&!eq.getColvalue(cm.getField()).isEmpty())
					csdataStr+=cm.getPropertyName()+":"+eq.getColvalue(cm.getField())+";";
			}
			eqlistString.add(!csdataStr.equals("")?csdataStr.substring(0, csdataStr.length()-1):"");
			
			listData.add(eqlistString);
		}
		
		return listData;
	}
	
	
	/**
	 * 
	 * @param status
	 * @return 根据设备状态返回所有备用设备
	 */
	public List<EquipAsset> getEquipData( EquipAsset equipasset) {
		List<EquipAsset> EquipAsset=this.readByEntity(equipasset);		
		return EquipAsset;
	}

	public Long Count(String typeId,EquipAsset equipasset) {
		EquipAsset search = equipasset;
		if (!StringUtils.isBlank(typeId)) {
			Type t = new Type();
			t.setId(typeId);
			search.setType(t);
		}
		long ret=this.getCountByEntity(search);
		
		return  ret;
	}

	public boolean deletedata(String chechedids) {
		String[] dss=chechedids.split(",");
		String ql="delete from EquipAsset where id in (?)";
		int y=0;
		for(int i=1;i<dss.length;i++){
			y=y+this.deleteByQL(ql, dss[i]);
		}	
		return y>0?true:false;	
	}
	
	public boolean scrapData(String chechedids) {
		String[] dss=chechedids.split(",");
		String ql="update EquipAsset set status='0' where id in (?)";
		int y=0;
		for(int i=1;i<dss.length;i++){
			y=y+this.deleteByQL(ql, dss[i]);
		}	
		return y>0?true:false;	
	}

	/**
	 * 
	 * @param status
	 * @return 根据设备状态返回所有备用设备
	 */
	public List<EquipAsset> getEquipInfo( String status) {
		EquipAsset search = new EquipAsset();
		search.setStatus(status);	
		search.setIdentifying(1);
		OrderEntity order=new OrderEntity();
		order.asc("buyDate");
		List<EquipAsset> EquipAsset=this.readByEntity(search,order);		
		return EquipAsset;
	}

	/**
	 * 
	 * @param typeId
	 * @return 根据设备类型返回该类型的所有备用设备
	 */
	public List<EquipAsset> getEquipStandbyList(String typeId,String contractNumber,String rfid) {
		// TODO Auto-generated method stub
		EquipAsset search = new EquipAsset();
		search.setStatus("1");
		search.setIdentifying(1);
		
		if(contractNumber!=null&&!contractNumber.isEmpty())
			search.setContractNumber(contractNumber);
		if(rfid!=null&&!rfid.isEmpty())
			search.setRfid(rfid);
		
		OrderEntity order=new OrderEntity();
		order.asc("buyDate");
		order.asc("isNewEquip");
		if (!StringUtils.isBlank(typeId)) {
			Type t = new Type();
			t.setId(typeId);
			search.setType(t);
		}
			List<EquipAsset> EquipAsset=this.readByEntity(search,order);
		
		return EquipAsset;
	}

	/**
	 * 
	 * @param assetId
	 * @return 判断资产id是否存在
	 */
	@Transactional
	public Boolean judgeAssetId(String assetId,String equipId) {
		String sql="select count(id) from t_app_equipasset where  assetId=?";
		int ret=0;
		if(!StringUtils.isEmpty(equipId)){
			sql="select count(id) from t_app_equipasset where id!=? and assetId=?";
			ret=this.getCountBySQL(sql,equipId,assetId);
		}else{
			ret=this.getCountBySQL(sql,assetId);
		}			
		return ret>0?false:true;
	}

	/**
	 * 
	 * @param userId
	 * @param typeId
	 * @return 通过人员、设备分类返回该人员占用和拥有的设备信息
	 */
	public List<EquipAsset> getOccupyEquipList(String userId, String typeId) {		
		String ql="select T from EquipAsset T where T.type='"+typeId+"' and T.responsibleId='"+userId+"' and T.status!=1 and T.status!=0 and T.identifying=1";
		@SuppressWarnings("unchecked")
		List<EquipAsset> EquipAsset =(List<EquipAsset>) this.readByQL(ql);		
		return EquipAsset;
	}
	
	/**
	 * 
	 * @param assetId
	 * @return 通过资产id获取该设备的所有信息
	 */
	public EquipAsset getEquipAsset(String assetId) {
		EquipAsset search = new EquipAsset();
		search.setAssetId(assetId);	
		search.setIdentifying(1);
		return this.readSingleByEntity(search);
	}
	
	/**
	 * 
	 * @param rfid
	 * @return 通过资产rfid获取该设备的所有信息
	 */
	public EquipAsset getEqByRfid(String rfid) {
		EquipAsset search = new EquipAsset();
		search.setRfid(rfid);
		search.setIdentifying(1);
		return this.readSingleByEntity(search);
	}

	
	/**
	 * 
	 * @param equipModel
	 * @return 根据设备型号返回该型号的所有备用设备
	 */
	public List<EquipAsset> getEquipModelList(String equipModel) {
		// TODO Auto-generated method stub
		EquipAsset search = new EquipAsset();
		search.setStatus("1");
		search.setIdentifying(1);
		search.setEquipModel(equipModel);
		List<EquipAsset> EquipAsset=this.readByEntity(search);
		
		return EquipAsset;
	}

	public Boolean deleteAllbtn(String userid) {
		String ql="delete from EquipAsset where identifying=0 and upUserId='"+userid+"'";
		int y=this.deleteByQL(ql);
		
		return y>0?true:false;
		
	}

	public String getStandbyCount(String typeId) {
		String sql="select count(id) from t_app_equipasset where assetType=? and identifying=1";
		int ret=this.getCountBySQL(sql,typeId);
		return ret+"";
	}
	
	/**
	 * 设备待出库列表
	 * @param person 责任人
	 * @param page
	 * @param size
	 * @return
	 */
	public List<EquipAsset> getEqByStatus(String person,int page, int size){
		EquipAsset search = new EquipAsset();
		DetachedCriteria dc = this.getDc(search);
		dc.add(Restrictions.eq("status","3"));
		dc.add(Restrictions.eq("identifying", 1));
		if(person!=null&&!person.isEmpty()){
			dc.add(Restrictions.like("responsible","%"+person+"%"));
		}
		// TODO:
		@SuppressWarnings("unchecked")
		List<EquipAsset> pos =(List<EquipAsset>) this.readPageByCriteria(dc, page, size);
	
		return pos;
	}
	
	/**
	 *  设备待出库数量
	 * @param person 责任人
	 * @return
	 */
	public Long getEqByStatusCount(String person) {
		String ql = "";
		Object ret = null;
		ql = "SELECT COUNT(*) FROM EquipAsset AS T WHERE  T.status='3' AND T.identifying=1";
		if(person!=null&&!person.isEmpty())
			ql = ql +" AND T.responsible LIKE '%"+person+"%'";
		ret = this.readSingleByQL(ql);

		return (Long) ret;
	}
	

	/**
	 * 比较两个设备
	 * 
	 * @param model1 原来设备
	 * @param model2 修改设备
	 * @return
	 * @throws Exception
	 */
	public EquipAssetUpdateLog updateReflect(Object model1,Object model2,List<ClassifyManage> classifyList) throws Exception{
		EquipAssetUpdateLog updatelog = new EquipAssetUpdateLog();
		EquipAsset eq1 = (EquipAsset)model1;
		EquipAsset eq2 = (EquipAsset)model2;
		String laterContent = "";//修改后的内容
		Field[] field = model1.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组 
		Field[] libraryfield = model2.getClass().getDeclaredFields();//获取实体类的所有属性，返回Field数组 
        for(int j=0;j<field.length;j++){
            String name = field[j].getName();    //获取属性的名字             
            String type = field[j].getGenericType().toString();//获取属性的类型
			Annotation annotation = field[j].getAnnotation(Myannotation.class); //获取属性上的指定类型的注释
            String libraryname = libraryfield[j].getName();    //获取属性的名字             
            Method m = model1.getClass().getMethod("get"+name.substring(0, 1).toUpperCase()+name.substring(1, name.length()));
            Method librarym = model2.getClass().getMethod("get"+libraryname.substring(0, 1).toUpperCase()+libraryname.substring(1, libraryname.length()));
            if(type.equals("class java.lang.String")){   //如果type是类类型，则前面包含"class "，后面跟类名
                String value = (String) m.invoke(model1);//调用getter方法获取属性值
                String libraryvalue = (String) librarym.invoke(model2); 
                
                //不需要的值
                if("userID".equals(name)||"responsibleId".equals(name)||"userUnitId".equals(name)||"userNameeed".equals(name)||"noFullCause".equals(name)||"upUserId".equals(name))
                	continue;
                
                if("status".equals(name)&&!value.equals(libraryvalue)){
                	
                	laterContent+="状态:"+(value.equals("0")?"已报废":(value.equals("1")?"待分配":(value.equals("2")?"占用":(value.equals("3")?"待出库":(value.equals("4")?"已投运":(value.equals("5")?"其他":"待报废"))))))
                						+"→"+(libraryvalue.equals("0")?"已报废":(libraryvalue.equals("1")?"待分配":(libraryvalue.equals("2")?"占用":(libraryvalue.equals("3")?"待出库":(libraryvalue.equals("4")?"已投运":(libraryvalue.equals("5")?"其他":"待报废"))))))+"<br/>";
                }else{
                	if((value != null&&libraryvalue!=null&&!value.isEmpty()&&!libraryvalue.isEmpty()&&!value.trim().equals(libraryvalue.trim()))||(value == null&&libraryvalue != null)||(value != null&&libraryvalue == null)){
                  	   //有该类型的注释存在
         			   if (annotation!=null) {
                  		   Myannotation Myannotation = (Myannotation)annotation;//强制转化为相应的注释
                  		   if((value==null||"".equals(value))&&libraryvalue!=null&&!"".equals(libraryvalue)){
                  			   laterContent+=Myannotation.description()+":【 】→"+libraryvalue+"<br/>";
                  		   }else if((libraryvalue==null||"".equals(libraryvalue))&&value!=null&&!"".equals(value)){
                  			   laterContent+=Myannotation.description()+":"+value+"→【 】<br/>"; 
                  		   }else if(libraryvalue!=null&&!"".equals(libraryvalue)&&value!=null&&!"".equals(value)){
                  			   laterContent+=Myannotation.description()+":"+value+"→"+libraryvalue+"<br/>";   
                  		   }
                  			   
                  	   }
                    }
                }
            }
            
            if(type.equals("class java.lang.Integer")){   
                Integer value = (Integer) m.invoke(model1);
                Integer libraryvalue = (Integer) librarym.invoke(model2);
                
                //不需要的值
                if("identifying".equals(name)||"isBorrow".equals(name)||"isFull".equals(name))
                	continue;
                
                if("isPublic".equals(name)){
                	if(value != null&&value != libraryvalue){
                		laterContent+="使用范围:"+(value==0?"合同制员工":(value==1?"部门公用":(value==2?"其他用工人员":"")))+"→"+(libraryvalue==0?"合同制员工":(libraryvalue==1?"部门公用":(libraryvalue==2?"其他用工人员":"")))+"<br/>";
                	}else if(value == null&&libraryvalue != null&&("4".equals(eq2.getStatus())||"3".equals(eq2.getStatus())||"2".equals(eq2.getStatus()))){
                		
                		laterContent+="使用范围:【 】 → "+(libraryvalue==0?"合同制员工":(libraryvalue==1?"部门公用":(libraryvalue==2?"其他用工人员":"")))+"<br/>";
                	}else if(value != null&&libraryvalue == null){
                		laterContent+="使用范围:"+(value==0?"合同制员工":(value==1?"部门公用":(value==2?"其他用工人员":"")))+"→【 】<br/>";
                	}
                }else if("isNewEquip".equals(name)){
                	if(value != null&&value != libraryvalue){
                		laterContent+="新旧设备:"+(value==0?"旧设备":"新设备")+"→"+(libraryvalue==0?"旧设备":"新设备")+"<br/>";
                	}else if(value == null&&libraryvalue != null){
                		laterContent+="新旧设备:【 】 → "+(libraryvalue==0?"旧设备":"新设备")+"<br/>";
                	}else if(value != null&&libraryvalue == null){
                		laterContent+="新旧设备:"+(value==0?"旧设备":"新设备")+"→【 】 <br/>";
                	}
                }else{
            		if((value != null&&value != libraryvalue)||(value == null&&libraryvalue != null)){
	                	//有该类型的注释存在
            			if (annotation!=null) {
                   		   Myannotation Myannotation = (Myannotation)annotation;//强制转化为相应的注释
                   		   if(value==null&&libraryvalue!=null){
                 			 laterContent+=Myannotation.description()+":【 】→"+libraryvalue+"<br/>";
                 		   }else if(libraryvalue==null&&value!=null){
                 			 laterContent+=Myannotation.description()+":"+value+"→【 】<br/>"; 
                 		   }else if(libraryvalue!=value){
                 			 laterContent+=Myannotation.description()+":"+value+"→"+libraryvalue+"<br/>";   
                 		   }
                   	   }
            		}
                }
            }
            
            if(type.equals("class java.util.Date")){                
                Date value = (Date) m.invoke(model1);
                Date libraryvalue = (Date) librarym.invoke(model2);
                
                if((value != null&&libraryvalue!=null&&value.compareTo(libraryvalue) != 0)||(value == null&&libraryvalue != null)){
                	//有该类型的注释存在
        			if (annotation!=null) {
               		   Myannotation Myannotation = (Myannotation)annotation;//强制转化为相应的注释
               		   
               		   if(value==null&&libraryvalue!=null){
             			 laterContent+=Myannotation.description()+":【 】→"+libraryvalue+"<br/>";
             		   }else if(libraryvalue==null&&value!=null){
             			 laterContent+=Myannotation.description()+":"+value+"→【 】<br/>"; 
             		   }else if(libraryvalue!=value){
             			 laterContent+=Myannotation.description()+":"+value+"→"+libraryvalue+"<br/>";   
             		   }
               	   }
                }
            } 
            
            if(type.equals("class com.suredy.app.type.model.Type")){
            	Type value = (Type) m.invoke(model1);
            	Type libraryvalue = (Type) librarym.invoke(model2);
            	if(value != null&&libraryvalue != null&&value.getId().equals(libraryvalue.getId())){
            		for(ClassifyManage cm:classifyList){
            			String colValue = eq1.getColvalue(cm.getField());
            			String colLibrary = eq2.getColvalue(cm.getField());
            			
            			if((colValue != null&&!colValue.equals(colLibrary))||(colValue == null&&colLibrary != null)||(colValue != null&&colLibrary == null)){
            				if(colValue==null||"".equals(colValue)){
	                  			laterContent+=cm.getPropertyName()+":【 】→"+colLibrary+"<br/>";
	                  		 }else if(colLibrary==null||"".equals(colLibrary)){
	                  			laterContent+=cm.getPropertyName()+":"+colValue+"→【 】<br/>";
	                  		 }else{
	                  			 laterContent+=cm.getPropertyName()+":"+colValue+"→"+colLibrary+"<br/>";
	                  		 }
            			}
            			
            		}
            	}
            }
        }
        if(laterContent.length()>0){
        	updatelog.setLaterContent(laterContent);
        	updatelog.setEqId(eq1.getId());
        }
        
        return (laterContent.length()>0)?updatelog:null;
    }
}
