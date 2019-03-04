package com.suredy.flow.form.ctrl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.entity.FormTypeEntity;
import com.suredy.flow.form.model.Form;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.form.service.FormTypeSrv;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.resource.model.ResourceType;

@Controller
@RequestMapping(value = "/config/form")
public class FormCtrl extends BaseCtrl {

	@Autowired
	private FormSrv formSrv;

	@Autowired
	private FormTypeSrv typeSrv;

	
	@RequestMapping({"/type-tree"})
	@ResponseBody
	public Object getTypeTree() {
		//此处不带表单信息，仅仅显示表单分类
		List<Tree> typeTree = this.formSrv.getFormTypeTree(null, false);
		return typeTree == null ? null : typeTree;
	}

	@RequestMapping({"/type-info"})
	public ModelAndView tyepInfo(String typeId, String parentId) {
		ModelAndView view = new ModelAndView("/flow/formmage/type-info");

		FormTypeEntity data; 
		if (StringUtils.isEmpty(typeId)) {
			data = new FormTypeEntity();
			if (StringUtils.isNotEmpty(parentId)) {
				FormTypeEntity parent = this.typeSrv.get(parentId);
				data.setParent(parent);
			}
		} else {
			data = this.typeSrv.get(typeId);
		}
		view.addObject("type", data);
		return view;
	}

	@RequestMapping({"/type-save"})
	@ResponseBody
	public Object saveType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String name = request.getParameter("name");
		String sort = request.getParameter("sort");
		if ( StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 分类名称必须填写", null);
		}
		if (StringUtils.isEmpty(id)) {
			Date date = new Date();
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(name);
			resource.setType(ResourceType.FormType.getType());
			resource.setUri(UUID.randomUUID().toString());
			FormTypeEntity type = new FormTypeEntity();
			
			type.setName(name);
			type.setResource(resource);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			if (!StringUtils.isEmpty(parent)) {
				type.setParent(new FormTypeEntity());
				type.getParent().setId(parent);
			}
			this.typeSrv.save(type);
		} else {
			FormTypeEntity type = this.typeSrv.get(id);
			if (type == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的报表分类信息", null);
			}
			
			type.setName(name);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
	
			if (!StringUtils.isEmpty(parent)) {
				if (! parent.equals(id)) {
					type.setParent(new FormTypeEntity());
					type.getParent().setId(parent);
				}
			} else {
				type.setParent(null);
			}
			ResourceEntity resource = type.getResource();
			resource.setName(name);

			type.setResource(resource);
			this.typeSrv.update(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}

	@Transactional
	@RequestMapping({"/type-delete"})
	@ResponseBody
	public Object deleteType(String typeId) {
		if (StringUtils.isEmpty(typeId)) {
			return MessageModel.createErrorMessage("表单分类ID为空！", null);
		}

		FormTypeEntity type = this.typeSrv.get(typeId);

		if (type == null) {
			return MessageModel.createErrorMessage("未找到与ID对应的表单分类信息！", null);
		}
		
		if (type.getAssociationForms().size() > 0) {
			return MessageModel.createErrorMessage("当前报表分类下有关联报表,不允许删除分类！", null);
		}
		
		List<FormTypeEntity> types = this.typeSrv.getFormTypes(typeId);
		List<Form> forms = this.formSrv.getAll(types);
		if (forms != null && !forms.isEmpty()) {
			return MessageModel.createErrorMessage("当前分类的子分类有关联表单,不允许删除分类！", null);
		}
		
		if (type.getParent() != null) {
			type.getParent().getChildren().remove(type);
		}

		try {
			this.typeSrv.delete(type);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除失败,表单分类信息与安全资源映射错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 得到树形数据
	 * 
	 * @return
	 */
	@RequestMapping({"/custom-form-tree"})
	@ResponseBody
	public Object customFormTree() {
		return this.formSrv.getCustomFormTree();
	}

	/**
	 * 列表页面
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView getForms(String typeId, String page, String size) {
		ModelAndView view = new ModelAndView("/flow/formmage/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		List<FormTypeEntity> types = this.typeSrv.getFormTypes(typeId);
		Integer count = this.formSrv.Count(types);
		List<Form> data = this.formSrv.getAll(pageIndex, pageSize, types);

		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("typeId", typeId);
		return view;
	}

	/**
	 * 打开保存/修改页面
	 * 
	 * @param frmid
	 * @return
	 */
	@RequestMapping(value = "form-info")
	public ModelAndView formInfo(String formId, String typeId) {
		ModelAndView view = new ModelAndView("/flow/formmage/form-info");
		FormEntity form;
		if (StringUtils.isEmpty(formId)) {
			form = new FormEntity();
		} else {
			form = this.formSrv.get(formId);
		}

		FormTypeEntity type = null;
		if (StringUtils.isNotEmpty(typeId)) {
			type = this.typeSrv.get(typeId);
		}

		view.addObject("form", form);
		view.addObject("type", type);
		return view;
	}
	
	/**
	 * 验证是否有重复的fileType
	 * 
	 * @param fileType
	 * @return
	 */
	@RequestMapping("form-onlyFileType")
	@ResponseBody
	public Object formOnlyFileType(String fileType) {
		FormEntity form = null;
		if (!StringUtils.isBlank(fileType)) {
			form = formSrv.getByFileType(fileType);
			if(form!=null){
				return MessageModel.createErrorMessage(null, form.getId());
			}
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 保存/修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("form-save")
	@ResponseBody
	public Object formSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String typeId = request.getParameter("formTypeId");
		String name = request.getParameter("name");
		String defineId = request.getParameter("defineId");
		String doSthPath = request.getParameter("doSthPath");
		String draftPath = request.getParameter("draftPath");
		String fileType = request.getParameter("fileType");
		String flowId = request.getParameter("flowId");
		Integer formSel = StringUtils.isEmpty(request.getParameter("formSel")) ? 1 : Integer.valueOf(request.getParameter("formSel"));
		String listPath = request.getParameter("listPath");
		String menuId = request.getParameter("menuId");
		String isTemplate = request.getParameter("isTemplate");
		String temTypeId = request.getParameter("temTypeId");

		if ( StringUtils.isEmpty(typeId)) {
			return MessageModel.createErrorMessage("参数不足, 表单分类必须选择一类", null);
		}
		if ( StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 表单名称必须填写", null);
		}
		
		if (StringUtils.isEmpty(id)) {
			FormEntity form = new FormEntity();
			Date date = new Date();
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(name);
			resource.setType(ResourceType.FormSource.getType());
			if (formSel == 1) {
				resource.setUri(listPath);
			} else {
				resource.setUri("live-form/list?fileTypeCode=" + fileType);
			}
			form.setResource(resource);
			form.setCreateTime(date);
			form.setDefineId(StringUtils.isEmpty(defineId) ? null : defineId);
			form.setDoSthPath(doSthPath);
			form.setDraftPath(draftPath);
			form.setListPath(listPath);
			form.setFileType(fileType);
			form.setFlowId(flowId);
			form.setIsTemplate(isTemplate);
			form.setTemTypeId(temTypeId);
			form.setFormSel(formSel);
			form.setMenuId(menuId);
			form.setName(name);
			FormTypeEntity type = new FormTypeEntity();
			type.setId(typeId);
			form.setType(type);
			

			this.typeSrv.save(form);
		} else {
			FormEntity form = this.formSrv.get(id);
			if (form == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的表单信息", null);
			}
			ResourceEntity resource = form.getResource();
			resource.setName(name);
			resource.setType(ResourceType.FormSource.getType());
			if (formSel == 1) {
				resource.setUri(listPath);
			} else {
				resource.setUri("live-form/list?fileTypeCode=" + fileType);
			}
			form.setResource(resource);			
			form.setDefineId(StringUtils.isEmpty(defineId ) ? null : defineId);
			form.setDoSthPath(doSthPath);
			form.setDraftPath(draftPath);
			form.setListPath(listPath);
			form.setFileType(fileType);
			form.setFlowId(flowId);
			form.setIsTemplate(isTemplate);
			form.setTemTypeId(temTypeId);
			form.setFormSel(formSel == null ? 1 : Integer.valueOf(formSel));
			form.setMenuId(menuId);
			form.setName(name);
			FormTypeEntity type = new FormTypeEntity();
			type.setId(typeId);
			form.setType(type);
			this.typeSrv.update(form);
		}
		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 删除
	 * 
	 * @param formId
	 * @return
	 */
	@RequestMapping("form-delete")
	@ResponseBody
	public Object doEditDelete(String formId) {
		if (StringUtils.isBlank(formId)) {
			return MessageModel.createErrorMessage("表单ID必须提供", null);
		}
		FormEntity frm = this.formSrv.get(formId);
		if (frm == null) {
			return MessageModel.createErrorMessage("未找到与ID['" + formId + "']对应的表单信息", null);
		}

		this.formSrv.delete(frm);
		return MessageModel.createSuccessMessage(null, null);
	}


}
