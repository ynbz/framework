package com.suredy.app.consumable.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suredy.app.consumable.model.ConsumableTemplateInfo;
import com.suredy.app.consumable.model.ConsumerType;
import com.suredy.app.consumable.service.ConsumTemplateInfoSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;

@Controller
@RequestMapping({"/ConsumTemplateInfoCtrl"})
public class ConsumTemplateInfoCtrl extends BaseCtrl{
	
	@Autowired
	private ConsumTemplateInfoSrv consumTemplateInfoSrv;
	
	
	/**
	 * 
	 */
	@RequestMapping({"/gettemplateinfo"})
	@ResponseBody
	public Object gettemplateinfo(String typeId){
		Map<String, String> map=new HashMap<String, String>();
		if(!StringUtils.isEmpty(typeId)){
		ConsumableTemplateInfo templateinfo=new ConsumableTemplateInfo();
		templateinfo=consumTemplateInfoSrv.gettemplateinfo(typeId);		
		map.put("contemplateinfo", templateinfo==null?"":templateinfo.getContemplateinfo());
		map.put("contemplateId", templateinfo==null?"":templateinfo.getId());
		}
		return map;		
	}
	
	
	
	/**
	 * 
	 * @param id
	 * @return 模板数据保存
	 */
	@RequestMapping({"/savetemplateinfo"})
	@ResponseBody
	public Object savetemplateinfo(String typeId,String templateinfo,String id){
		ConsumableTemplateInfo tti=new ConsumableTemplateInfo();
		ConsumerType t=new ConsumerType();
		if(!StringUtils.isEmpty(id)){
			tti.setId(id);
		}
		if(!StringUtils.isEmpty(typeId)){
			t.setId(typeId);
			tti.setType(t);
			tti.setContemplateinfo(templateinfo);
			this.consumTemplateInfoSrv.saveOrUpdate(tti);
			return MessageModel.createSuccessMessage("msg", true);	
		}
		
		return MessageModel.createSuccessMessage("msg", false);	
	}
}
