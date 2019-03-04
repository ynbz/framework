package com.suredy.app.liveform.model.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.liveform.model.LiveForm;
import com.suredy.app.liveform.model.src.LiveFormSrv;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.manager.entity.FlowEntity;
import com.suredy.flow.manager.service.FlowManagerSrv;
import com.suredy.formbuilder.design.model.FormDefine;
import com.suredy.formbuilder.eav.helper.FormDataProcesser;
import com.suredy.formbuilder.eav.model.FormEntry;
/**
 * 动态表单web控制器
 * 
 * @author VIVID.G
 * @since 2017-3-13
 * @version v0.1
 */
@Controller
@RequestMapping("/live-form")
public class LiveFormCtrl extends BaseFlowCtrl {
	private final Logger log = LoggerFactory.getLogger(LiveFormCtrl.class);

	@Autowired
	private FormSrv formSrv;

	@Autowired
	private LiveFormSrv liveFormSrv;
	
	@Autowired
	private FlowManagerSrv flowManagerSrv;

	@Autowired
	private FormDataProcesser formDataProcesser;
	
	/**
	 * 得到列表数据
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @param fileTypeCode
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(String page, String size,String startTime,String endTime,String fileTypeCode) {
		
		ModelAndView view = new ModelAndView("/app/live-form/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<LiveForm> data = liveFormSrv.getAll(pageIndex, pageSize, startTime, endTime,fileTypeCode);
		int count = liveFormSrv.count( startTime, endTime,fileTypeCode);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}

	/**
	 * 打开表单
	 * 
	 * @param id 先以数据ID进行查询，如果未查询到，则以form manager id进行新数据的创建
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/open/{id:.{32}}")
	public String open(@PathVariable String id, Model model) {
		String view = "app/live-form/edit-form";

		boolean isNew = false;
		LiveForm form = null;
		String formEntryId = null;

		if (!StringUtils.isBlank(id)) {
			String ql = "SELECT T, T.formEntry.id FROM LiveForm T WHERE T.id = ?";

			Object[] data = (Object[]) this.liveFormSrv.readSingleByQL(ql, id);

			if (data != null && data.length == 2) {
				form = (LiveForm) data[0];
				formEntryId = (String) data[1];
			}
		}

		if (form == null) {
			isNew = true;
			form = this.newLiveFormBy(id);
			formEntryId = form == null ? null : form.getFormEntry().getId();
		}

		if (form == null || StringUtils.isBlank(formEntryId)) {
			log.error("Null form data or empty form entry id.");
			model.addAttribute("invalid", true);
			return view;
		}

		if (isNew)
			return "redirect:" + this.createOpenURIForSpringMVC(form.getId());

		model.addAttribute("data", form);
		model.addAttribute("formEntryId", formEntryId);

		return view;
	}

	/**
	 * 保存表单
	 * 
	 * @param form
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object update(String id, String title, String formEntryId, String processId, HttpServletRequest request) {
		if (StringUtils.isBlank(id) || StringUtils.isBlank(title) || StringUtils.isBlank(formEntryId)) {
			log.error("Empty form data id or form title or form entry id.");
			return MessageModel.createErrorMessage("错误，表单信息无效！", null);
		}

		FormEntry formEntry = this.formDataProcesser.updateData(formEntryId, request);

		if (formEntry == null) {
			log.error("Update form entry data error.");
			return MessageModel.createErrorMessage("错误，无法更新表单数据！", null);
		}

		// update form title
		String ql = "UPDATE LiveForm T SET T.title = ? WHERE T.id = ?";

		int count = this.liveFormSrv.updateByQL(ql, title, id);

		if (count != 1) {
			log.error("Update form title error.");
			return MessageModel.createErrorMessage("警告，无法更新表单标题！", null);
		}

		LiveForm form = new LiveForm();
		form.setId(id);
		form.setProcessId(processId);

		this.updateTodo(form, title, this.createTodoURI(id));

		return MessageModel.createSuccessMessage("success", null);
	}

	/**
	 * 加载动态表单部分数据
	 * 
	 * @param liveFormId
	 * @return
	 */
	@RequestMapping(value = "/load-data/{liveFormId}", method = RequestMethod.POST)
	@ResponseBody
	public Object loadFormData(@PathVariable String liveFormId) {
		if (StringUtils.isBlank(liveFormId))
			return null;

		String ql = "SELECT T.formEntry FROM LiveForm T WHERE T.id = ?";

		Object formEntry = this.liveFormSrv.readSingleByQL(ql, liveFormId);

		if (formEntry == null || !(FormEntry.class.isInstance(formEntry))) {
			log.warn("Can't get form entry data.");
			return null;
		}

		Object data = this.formDataProcesser.loadData((FormEntry) formEntry);

		return data;
	}

	/**
	 * 通过表单管理创建一个新的表单数据，并启动流程
	 * 
	 * @param formMageId
	 * @return
	 */
	@Transactional
	private LiveForm newLiveFormBy(String formMageId) {
		if (StringUtils.isBlank(formMageId)) {
			log.error("Need a form manager id.");
			return null;
		}

		String ql = "SELECT T, T.defineId, T.flowId FROM FormEntity T WHERE T.id = ?";

		Object[] data = (Object[]) this.formSrv.readSingleByQL(ql, formMageId);

		if (data == null || data.length != 3) {
			log.error("Can't get form manager data by id: " + formMageId);
			return null;
		}

		FormEntity formManager = (FormEntity) data[0];
		String formDefineId = (String) data[1];
		FlowEntity flowManager=null;
		if(!StringUtils.isBlank(formDefineId))
			flowManager = flowManagerSrv.getById(data[2].toString());

		if (StringUtils.isBlank(formDefineId)) {
			log.error("There is no form define id.");
			return null;
		}

		if (flowManager == null) {
			log.error("There is no flow defined.");
			return null;
		}

		LiveForm form = new LiveForm();
		form.setFormEntry(new FormEntry());

		// set data
		form.setTitle("「新建的" + formManager.getName() + "」");
		form.setFileTypeName(formManager.getName());
		form.setFileTypeId(formManager.getId());
		form.getFormEntry().setFormDefine(new FormDefine());
		form.getFormEntry().getFormDefine().setId((String) data[1]);
		return this.createFlow(form, formManager.getFileType(), flowManager.getCode(), this.openURITemplate(), form.getTitle());
	}

	private String createTodoURI(String id) {
		return this.createTodoUri(this.openURITemplate(), id);
	}

	private String createOpenURIForSpringMVC(String id) {
		return this.openURITemplate().replace("{id}", id);
	}

	private String openURITemplate() {
		return "/live-form/open/{id}";
	}

}
