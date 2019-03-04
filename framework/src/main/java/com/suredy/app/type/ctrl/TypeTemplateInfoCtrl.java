package com.suredy.app.type.ctrl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suredy.app.type.model.Type;
import com.suredy.app.type.model.TypeTemplateInfo;
import com.suredy.app.type.service.TypeTemplateInfoSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;

@Controller
@RequestMapping({"/TypeTemplateInfoCtrl"})
public class TypeTemplateInfoCtrl extends BaseCtrl{
	
	@Autowired
	private TypeTemplateInfoSrv typeTemplateInfoSrv;
	
	
	/**
	 * 
	 */
	@RequestMapping({"/gettemplateinfo"})
	@ResponseBody
	public Object gettemplateinfo(String typeId){
		Map<String, String> map=new HashMap<String, String>();
		if(!StringUtils.isEmpty(typeId)){
		TypeTemplateInfo templateinfo=new TypeTemplateInfo();
		templateinfo=typeTemplateInfoSrv.gettemplateinfo(typeId);		
		map.put("templateinfo", templateinfo==null?"":templateinfo.getTemplateinfo());
		map.put("id", templateinfo==null?"":templateinfo.getId());
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
		TypeTemplateInfo tti=new TypeTemplateInfo();
		Type t=new Type();
		if(!StringUtils.isEmpty(id)){
			tti.setId(id);
		}
		if(!StringUtils.isEmpty(typeId)){
			t.setId(typeId);
			tti.setType(t);
			tti.setTemplateinfo(templateinfo);
			this.typeTemplateInfoSrv.saveOrUpdate(tti);
			return MessageModel.createSuccessMessage("msg", true);	
		}
		
		return MessageModel.createSuccessMessage("msg", false);	
	}
}
