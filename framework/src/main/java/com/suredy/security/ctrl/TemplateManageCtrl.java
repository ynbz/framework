package com.suredy.security.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.security.model.TemplateManage;
import com.suredy.security.model.TemplateType;
import com.suredy.security.service.TemplateManageSrv;
import com.suredy.security.service.TemplateTypeSrv;

@Controller
@RequestMapping({"/config"})
public class TemplateManageCtrl extends BaseCtrl{
	
	@Autowired
	private TemplateManageSrv templateSrv;
	
	@Autowired
	private TemplateTypeSrv typeSrv;
	
	@RequestMapping({"/templatetype/tree"})
	@ResponseBody
	public Object typeTree(){
		return typeSrv.getTemplateTypeTree();
	}
	
	@RequestMapping({"/templatetype/info"})
	public ModelAndView editft(String id, String parentId) {
		ModelAndView view = new ModelAndView("/config/template/templatemanage/type-info");
		TemplateType data; 
		if (StringUtils.isEmpty(id)) {
			data = new TemplateType();
			data.setParent(new TemplateType());
			data.getParent().setId(parentId);
		} else {
			data = this.typeSrv.get(id);
		}
		view.addObject("data", data);
		return view;
	}
	
	@RequestMapping({"/templatetype/save"})
	@ResponseBody
	public Object save(HttpServletRequest request){
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String typeName = request.getParameter("typeName");
		String sort = request.getParameter("sort");
		String code = request.getParameter("code");
		if ( StringUtils.isEmpty(typeName)) {
			return MessageModel.createErrorMessage("参数不足, 菜单名称必须填写", null);
		}
		if (StringUtils.isEmpty(id)) {
			TemplateType ft = new TemplateType();
			ft.setTypeName(typeName);
			ft.setCode(code);
			ft.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			
			if (!StringUtils.isEmpty(parent)) {
				ft.setParent(new TemplateType());
				ft.getParent().setId(parent);
			}
			this.typeSrv.save(ft);
		} else {
			TemplateType ft = this.typeSrv.get(id);
			if (ft == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的菜单信息", null);
			}
			
			ft.setTypeName(typeName);
			ft.setCode(code);
			ft.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			
			if (!StringUtils.isEmpty(parent)) {
				if (! parent.equals(id)) {
					ft.setParent(new TemplateType());
					ft.getParent().setId(parent);
				}
			} else {
				ft.setParent(null);
			}
			
			this.typeSrv.update(ft);
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	

	@RequestMapping({"/templatetype/delete"})
	@ResponseBody
	public Object delete(String templateId){
		List<TemplateType> types = this.typeSrv.getFileTypes(templateId);
		if (types != null && !types.isEmpty()) {
			int size = this.templateSrv.count(types);
			if (size > 0) {
				return MessageModel.createErrorMessage("当前分类或者子分类下有关联的文件，请先删除文件再次尝试删除分类！", null);
			}
			
			TemplateType type = this.typeSrv.get(templateId);
			this.typeSrv.delete(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping({"/templatemanage/tree/{typeId}"})
	@ResponseBody
	public Object typeTree(@PathVariable String typeId){
		return templateSrv.getTemplateTree(typeId);
	}
	
	@RequestMapping(value = "/templatemanage/templateList")
	public ModelAndView templateList(String page, String size, String typeId) {
		
		ModelAndView view = new ModelAndView("/config/template/templatemanage/template-list");
		TemplateType type = null;
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		
		if(!StringUtils.isBlank(typeId)){
			type = typeSrv.get(typeId);
		}
		List<TemplateManage> data = templateSrv.getFmAll(pageIndex, pageSize, type);
		int count = templateSrv.count(type);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("childid", typeId);
		
		return view;
	}
	
	@RequestMapping(value = "/templatemanage/uploadView")
	public ModelAndView uploadView(String temid,String  temTypeId) {
		ModelAndView view = new ModelAndView("/config/template/templatemanage/templateupload");
		TemplateManage tem = null;
		TemplateType type = null;
		
		if(!StringUtils.isBlank(temid)){
			tem = templateSrv.get(temid);
		}else{
			tem = new TemplateManage();
		}
		
		if(!StringUtils.isBlank(temTypeId)){
			type = typeSrv.get(temTypeId);
		}
		
		view.addObject("template", tem);
		view.addObject("type", type);
		return view;
	}
	
	@RequestMapping({"/templatemanage/saveTemplate"})
	@ResponseBody
	public Object delete(HttpServletRequest request){
		String templateTitel = request.getParameter("templateTitel");
		String templateTypeId = request.getParameter("templateTypeId");
		String templateId = request.getParameter("templateId");
		TemplateManage tem = new TemplateManage();
		tem.setTemplateTitel(templateTitel);
		if(!StringUtils.isBlank(templateId)){
			tem.setTemplateUrlId(templateId);
		}
		tem.setUploadTime(new Date());
		tem.setUploadName(this.getUser().getFullName());
		tem.setType(typeSrv.get(templateTypeId));
		templateSrv.save(tem);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "/templatemanage/templateView")
	public ModelAndView templateView(String templateId) {
		ModelAndView view = new ModelAndView("/config/template/templatemanage/templateView");
		TemplateManage tem =null;
		if(!StringUtils.isBlank(templateId)){
			tem = templateSrv.get(templateId);
		}
		view.addObject("template", tem);
		return view;
	}
	
	/**
	 * 删除
	 * @param temid
	 * @return
	 */
	@RequestMapping("templatemanage/delete")
	@ResponseBody
	public Object doEditDelete(String temid) {	
		if (StringUtils.isBlank(temid)) {
			return MessageModel.createErrorMessage("模板ID必须提供", null);
		}
		TemplateManage tem = this.templateSrv.get(temid);
		if (tem == null) {
			return MessageModel.createErrorMessage("未找到与ID['"+ temid +"']对应的模板信息", null);
		}
		
		tem = this.templateSrv.delete(tem);
		return MessageModel.createSuccessMessage(tem.getTemplateUrlId(), null);
	}

}
