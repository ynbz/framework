package com.suredy.app.consumable.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.consumable.model.ConsumProperty;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.app.consumable.service.ConsumPropertySrv;
import com.suredy.app.consumable.service.ConsumerTypeService;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;

/**
 * 
 * @author lhl
 *
 */

@Controller
@RequestMapping(value="/ConsumPropertyCtrl")
public class ConsumPropertyCtrl extends BaseCtrl{
	
	@Autowired
	private ConsumPropertySrv consumPropertySrv;
	@Autowired
	private ConsumerTypeService typesrv;
	
	/**
	 * 
	 * @param nodeid
	 * @return 判断设备类型是否是跟节点
	 */
	@RequestMapping(value="/getListData")
	@ResponseBody
	public Object getListData(String nodeid){
		String flg=consumPropertySrv.getIsChildNode(nodeid);
		return MessageModel.createSuccessMessage(null, flg);
	}
	/**
	 * 
	 * @param nodeid
	 * @param page
	 * @param size
	 * @return 获取设备类型属性信息
	 */
	@RequestMapping(value="/getConsumProperData")
	public ModelAndView getConsumProperData(String nodeid,String page,String size){	
		int pageIndex=1,pageSize=Constants.DEFAULT_PAGE_SIZE;
		if(!StringUtils.isBlank(page)){
			pageIndex=Integer.parseInt(page);
		}
		if(!StringUtils.isBlank(size)){
			pageSize=Integer.parseInt(size);
		}
		int count=consumPropertySrv.getCountData(nodeid);
		List<ConsumProperty> data=consumPropertySrv.getConsumProperData(nodeid,pageIndex,pageSize);
		ModelAndView view=new ModelAndView("/app/consumableType/consumableType-list");
		view.addObject("data",data);
		view.addObject("pageIndex",pageIndex);
		view.addObject("pageSize",pageSize);
		view.addObject("count",count);
		view.addObject("childid",nodeid);
		return view;
	}
	/**
	 * 
	 * @param nodeid
	 * @param dataId
	 * @return 打开修改或新增页面
	 */
	@RequestMapping(value="/addDatapage")
	public ModelAndView addDatapage(String nodeid,String dataId){
		ModelAndView view=new ModelAndView("/app/consumableType/consumProperty-info");
		ConsumerType type=typesrv.getByIdData(nodeid);
		ConsumProperty data=new ConsumProperty();
		data.setSort(0);
	
		if(!StringUtils.isEmpty(dataId)){
			data=consumPropertySrv.getByIdClassify(dataId);
		}
		view.addObject("type",type);
		view.addObject("data",data);
		return view;	
	}
	/**
	 * 
	 * @param csdata
	 * @return 保存和修改物品类型属性
	 */
	@RequestMapping(value="/saveData")
	@ResponseBody
	public Object saveData(ConsumProperty csdata){
		boolean flg=false;
		if(StringUtils.isEmpty(csdata.getId())){
			List<ConsumProperty> cf= consumPropertySrv.getConsumProperData(csdata.getType().getId(), 1, 999);
			String colmun="col0";	
			if(cf.size()>0){
				int i=0;
			while(true){
				int y=0;
				for (ConsumProperty cp : cf) {
					String col=cp.getField();
						if(!StringUtils.isEmpty(col)&&col.equals("col"+i)){
							y++;
						}
					}
				if(y==0){
					colmun="col"+i;
					break;
				}
				i++;
			}
			}
			csdata.setField(colmun);	
		}
		int i=0;
		for (ConsumProperty cp :consumPropertySrv.getSortAndTypeList(csdata)) {
			ConsumProperty data=consumPropertySrv.saveOrUpdate(cp);
			if(!StringUtils.isEmpty(data.getId())) i++;
		}
		if(i>0){
			flg=true;
		}
		return MessageModel.createSuccessMessage(null, flg);	
	}
	/**
	 * 
	 * @param chechedids
	 * @return 删除属性信息
	 */
	@RequestMapping(value="/deletedata")
	@ResponseBody
	public Object  deletedata(String chechedids){
		boolean flg=consumPropertySrv.deletedata(chechedids);
		return MessageModel.createErrorMessage("flg", flg);
	}
}
