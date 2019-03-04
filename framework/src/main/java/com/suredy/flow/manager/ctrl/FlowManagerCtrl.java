package com.suredy.flow.manager.ctrl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.manager.entity.FlowEntity;
import com.suredy.flow.manager.service.FlowManagerSrv;

@Controller
@RequestMapping(value = "/config/flow")
public class FlowManagerCtrl extends BaseCtrl {

	@Autowired
	private FlowManagerSrv flowManagerSrv;

	@Autowired
	private FormSrv formSrv;

	@RequestMapping({"/tree"})
	@ResponseBody
	public Object flTree() {
		return flowManagerSrv.getFlTree();
	}

	/**
	 * 列表页面
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "list")
	public ModelAndView getRoles(String page, String size) {
		ModelAndView view = new ModelAndView("/flow/flow/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;

		Long count = this.flowManagerSrv.Count();
		List<FlowEntity> data = this.flowManagerSrv.getAll(pageIndex, pageSize);

		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}

	/**
	 * 打开保存/修改页面
	 * 
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value = "save")
	public ModelAndView editRole(String flowId) {
		ModelAndView view = new ModelAndView("/flow/flow/save-edit");
		FlowEntity flm;
		if (StringUtils.isEmpty(flowId)) {
			flm = new FlowEntity();
		} else {
			flm = this.flowManagerSrv.getById(flowId);
		}
		view.addObject("flm", flm);
		return view;
	}

	/**
	 * 保存/修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("save-edit")
	@ResponseBody
	public Object doEditRole(FlowEntity flowEntity) {
		FlowEntity t1 = flowManagerSrv.getOnlyCode(flowEntity.getCode().trim());
		if (t1 != null) {
			if (flowEntity.getId() == null) {
				return MessageModel.createSuccessMessage("code", "流程代码重复！");
			} else if (!t1.getId().equals(flowEntity.getId())) {
				return MessageModel.createSuccessMessage("code", "流程代码重复！");
			}
		}
		flowEntity.setCreatetime(new Date());
		this.flowManagerSrv.saveOrUpdate(flowEntity);
		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 删除
	 * 
	 * @param flowId
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object doEditDelete(String flowId) {
		if (StringUtils.isBlank(flowId)) {
			return MessageModel.createErrorMessage("流程ID必须提供", null);
		}
		FlowEntity flm = this.flowManagerSrv.get(flowId);
		if (flm == null) {
			return MessageModel.createErrorMessage("未找到与ID['" + flowId + "']对应的流程信息", null);
		}

		List<FormEntity> fms = this.formSrv.getAll(flowId);
		if (fms != null && fms.size() > 0) {
			return MessageModel.createErrorMessage("存在关联表单数据，请先删关联表单数据！", null);
		}

		this.flowManagerSrv.delete(flm);
		return MessageModel.createSuccessMessage(null, null);
	}
}
