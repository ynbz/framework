package com.suredy.app.consumable.ctrl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.consumable.model.ConsumInStock;
import com.suredy.app.consumable.model.ConsumOutStock;
import com.suredy.app.consumable.model.ConsumPro;
import com.suredy.app.consumable.model.ConsumProperty;
import com.suredy.app.consumable.model.ConsumableManage;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.app.consumable.service.ConsumInStockSrv;
import com.suredy.app.consumable.service.ConsumPropertySrv;
import com.suredy.app.consumable.service.ConsumableManageSrv;
import com.suredy.app.consumable.service.ConsumableOutStockSrv;
import com.suredy.app.consumable.service.ConsumerTypeService;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;


@Controller
@RequestMapping(value="/ConsumableCtrl")
public class ConsumableCtrl extends BaseCtrl{

	@Autowired
	private ConsumableManageSrv consumableManageSrv;
	@Autowired
	private ConsumPropertySrv consumPropertySrv;
	@Autowired
	private ConsumerTypeService consumerTypeService;
	@Autowired
	private  ConsumInStockSrv consumInStockSrv;
	@Autowired
	private  ConsumableOutStockSrv consumableOutStockSrv;
	
	
	@RequestMapping(value="/getConsumbleCtrl")
	@ResponseBody
	public ModelAndView getConsumbleCtrl(String page, String size, String typeId, ConsumableManage consumabledata){
		ModelAndView view=new ModelAndView("/app/consumable/consumable-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		Long count = (long) 0;	
		List<ConsumableManage> data = new ArrayList<ConsumableManage>();
		List<ConsumProperty> cpdata =new ArrayList<ConsumProperty>();
		count = this.consumableManageSrv.Count(typeId, consumabledata);
		data = this.consumableManageSrv.getListData(pageIndex, pageSize, typeId, consumabledata);
		if(!StringUtils.isBlank(typeId)){			
			cpdata = consumPropertySrv.getConsumPropertyIsShow(typeId);
		}
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("cpdata", cpdata);
		view.addObject("childid", typeId);
		return view;	
	}
	
	@RequestMapping(value="/addOrUpdatePage")
	@ResponseBody
	public ModelAndView addOrUpdatePage(String id,String typeID){
		ModelAndView view=new ModelAndView("/app/consumable/consumable-info");
		ConsumableManage cmdata=new ConsumableManage();
		List<ConsumProperty>  datalist = new ArrayList<ConsumProperty>();
		if(!StringUtils.isEmpty(id)){
			cmdata=consumableManageSrv.get(id);
			datalist= consumPropertySrv.getConsumProperData(cmdata.getType().getId(), 1, 999);
		}
		if(!StringUtils.isEmpty(typeID)){
			ConsumerType type =consumerTypeService.getByIdData(typeID);
			datalist= consumPropertySrv.getConsumProperData(typeID, 1, 999);
			cmdata.setType(type);
		}
		view.addObject("cmdata",cmdata);
		view.addObject("datalist",datalist);
		return view;	
	}
	
	
	/**
	 * 
	 * @param typeid
	 * @return 根据类型id获取配置属性
	 * 
	 * 
	 */
	@RequestMapping(value="getconsumablepro")
	@ResponseBody
	public List<ConsumPro> getconsumablepro(String typeid) {
		List<ConsumProperty>  datalist = new ArrayList<ConsumProperty>();
		List<ConsumPro> datalistpro=new ArrayList<ConsumPro>();
		if (!StringUtils.isEmpty(typeid)) {
			datalist = consumPropertySrv.getConsumProperData(typeid, 1, 999);
			if(datalist!=null){
				for (ConsumProperty conm : datalist) {
					ConsumPro datapro=new ConsumPro();
					datapro.setConsumTypeId(conm.getType().getId());
					datapro.setField(conm.getField());
					datapro.setPropertyName(conm.getPropertyName());
					datapro.setIsShow(conm.getIsShow());
					datapro.setSort(conm.getSort());
					datalistpro.add(datapro);
				}
			}
		}	
		return datalistpro;
	}
	
	/**
	 * 
	 * @param equipasset
	 * @return 保存或修改数据
	 */
	@RequestMapping(value = "/saveAndUpdatedata")
	@ResponseBody
	public Object saveAndUpdatedata(ConsumableManage consumableManage) {
		boolean flg = false;
		String msg="";
		String id=consumableManage.getId();
		if(id!=null&&id.length()>0){
			ConsumableManage hiseas=consumableManageSrv.get(id);
			int ystock=(hiseas.getStock()==null||hiseas.getStock().length()<=0)?0:Integer.parseInt(hiseas.getStock());
			int nstock=(consumableManage.getStock()==null||consumableManage.getStock().length()<=0)?0:Integer.parseInt(consumableManage.getStock());
			int footprint=(hiseas.getFootprint()==null||hiseas.getFootprint().length()<=0)?0:Integer.parseInt(hiseas.getFootprint());
			if(nstock>=ystock){
				ConsumableManage eas = consumableManageSrv.saveOrUpdate(consumableManage);	
				if(eas==null) {
					msg="库存修改失败！";
					flg=false;
					return MessageModel.createSuccessMessage(msg, flg);
				}	
					ConsumInStock cminstock=new ConsumInStock();
					cminstock.setConsumableId(eas.getId());
					cminstock.setInStocktime(new Date());
					cminstock.setOperationId(this.getUser().getId());
					cminstock.setOperationName(this.getUser().getName());
					cminstock.setStockNum((nstock-ystock)+"");
					ConsumInStock cminstock1=consumInStockSrv.saveOrUpdate(cminstock);
					if(cminstock1!=null){
						flg = true;
						msg="库存入库成功！并且已记录";
					}
			}else{
				if(nstock<footprint){
					flg = false;
					msg="库存修改失败，占用量超过库存，不能修改！";
				}else{
					ConsumableManage eas = consumableManageSrv.saveOrUpdate(consumableManage);	
					if(eas==null) {
						msg="库存修改失败！";
						flg=false;
						return MessageModel.createSuccessMessage(msg, flg);
					}
					ConsumInStock cminstock=new ConsumInStock();
					cminstock.setConsumableId(eas.getId());
					cminstock.setInStocktime(new Date());
					cminstock.setOperationId(this.getUser().getId());
					cminstock.setOperationName(this.getUser().getName());
					cminstock.setStockNum(nstock+"");
					cminstock.setComm("库存量从"+ystock+"减少到"+nstock+",减少了"+(ystock-nstock));
					ConsumInStock cminstock1=consumInStockSrv.saveOrUpdate(cminstock);
					if(cminstock1!=null){
						flg = true;
						msg="库存修改成功";
					}
				}
			}	
		}else{
			ConsumableManage eas = consumableManageSrv.saveOrUpdate(consumableManage);
			if(eas==null) {
				msg="库存修改失败！";
				flg=false;
				return MessageModel.createSuccessMessage(msg, flg);
			}
				ConsumInStock cminstock=new ConsumInStock();
				cminstock.setConsumableId(eas.getId());
				cminstock.setInStocktime(new Date());
				cminstock.setOperationId(this.getUser().getId());
				cminstock.setOperationName(this.getUser().getName());
				cminstock.setStockNum(eas.getStock());
				ConsumInStock cminstock1=consumInStockSrv.saveOrUpdate(cminstock);
				if(cminstock1!=null){
				flg = true;
				msg="库存修改成功";
				}
		}
		
		return MessageModel.createSuccessMessage(msg, flg);
	}
	
	
	@RequestMapping(value = "/deletedata")
	@ResponseBody
	public Object deletedata(String chechedid) {
		boolean flg = consumableManageSrv.deletedata(chechedid);
		return MessageModel.createErrorMessage("flg", flg);
	}
	
	@RequestMapping(value = "/lookConsumableAnddEquip")
	@ResponseBody
	public ModelAndView lookConsumableAnddEquip(String consumableid,String page, String size,String consumname) {
		ModelAndView view=new ModelAndView("/app/consumable/consumableAndEquip");
		int pageIndex = 1, pageSize = 8;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		int count=0;	
		List<ConsumOutStock> data= consumableOutStockSrv.lookConsumableAnddEquip(consumableid,pageIndex,pageSize);
		count = this.consumableOutStockSrv.Count(consumableid);
		view.addObject("data",data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count",  count);
		view.addObject("consumableid",  consumableid);
		view.addObject("consumname",  consumname);
		return view;
	}
	
}
