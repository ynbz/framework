package com.suredy.test.ctrl;

import java.text.SimpleDateFormat;
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
import org.springframework.web.servlet.view.RedirectView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.manager.entity.FlowEntity;
import com.suredy.flow.manager.service.FlowManagerSrv;
import com.suredy.test.model.IncDisManage;
import com.suredy.test.service.IncDisManageSrv;
import com.suredy.tools.file.srv.FileModelSrv;

/**
 * @Title IncDisManageCtrl
 * @Package com.suredy.test.ctrl
 * @Description 收文控制类
 * @author zyh
 * @date 2017-03-015
 *
 */
@Controller
@RequestMapping(value = "/incdism")
public class IncDisManageCtrl extends BaseFlowCtrl{
private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private IncDisManageSrv incdismSrv;
	
	@Autowired
	private FormSrv formSrv;
	
	@Autowired
	private FlowManagerSrv flowManagerSrv;
	
	@Autowired
	protected FileModelSrv fileModelSrv;
	
	
	@RequestMapping(value = "/list")
	public ModelAndView list(String page, String size,String startTime,String endTime,String fileTypeCode) {
		
		ModelAndView view = new ModelAndView("/test/incdismanage/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<IncDisManage> data = incdismSrv.getAll(pageIndex, pageSize, startTime, endTime,fileTypeCode);
		int count = incdismSrv.count( startTime, endTime,fileTypeCode);
		
		view.addObject("data", data);
		view.addObject("fileTypeCode", fileTypeCode);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping({"/newIncDis/{fileType}"})
	@ResponseBody
	public ModelAndView newIncDis(@PathVariable String fileType) {
		FormEntity form = null;
		ModelAndView view = null;
		if(!StringUtils.isBlank(fileType)){
			form = formSrv.getByFileType(fileType);
			if(form!=null){
				FlowEntity flm = flowManagerSrv.getById(form.getFlowId());
				IncDisManage incdism = new IncDisManage();
				incdism.setIncDisDate(new Date());
				incdism.setTitle(form.getName());
				this.createFlow(incdism, form.getFileType(),flm.getCode(),form.getDoSthPath(),incdism.getTitle());
				view = new ModelAndView(new RedirectView(this.createTodoUri(form.getDoSthPath(), incdism.getId())));//createTodoUri
			}else{
				view = new ModelAndView("flow-errorMessage");
			}
		}else{
			view = new ModelAndView("flow-errorMessage");
		}
		
		return view;
	}
	
	/**
	 * 打开保存/修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view/{id}")
	public ModelAndView view(@PathVariable String id) {
		ModelAndView view = new ModelAndView("/test/incdismanage/addIncDis");
		
		IncDisManage incdism;
		if(StringUtils.isEmpty(id)){
			incdism=new IncDisManage();
		}else{
			incdism = this.incdismSrv.get(id);
		}
		
		view.addObject("fileTypeName", "收文");
		view.addObject("incdism", incdism);
		return view;
	}
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("saveOreditIncDisM")
	@ResponseBody
	public Object doEditRole(HttpServletRequest request) {
		IncDisManage incdism = null;
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String idUnit = request.getParameter("idUnit");
		String rootInUnit = request.getParameter("rootInUnit");
		String originalNumber = request.getParameter("originalNumber");
		String incDisCode = request.getParameter("incDisCode");
		String originalDate = request.getParameter("originalDate");
		String incDisDate = request.getParameter("incDisDate");
		String limitDate = request.getParameter("limitDate");
		String countCode = request.getParameter("countCode");
		String sDepartment = request.getParameter("sDepartment");
		String coOrganizer = request.getParameter("coOrganizer");
		String readUnit = request.getParameter("readUnit");
		String readDoUnit = request.getParameter("readDoUnit");
		String fileSecCla = request.getParameter("fileSecCla");
		String secrecyDateLimit = request.getParameter("secrecyDateLimit");
		String degreeOfE = request.getParameter("degreeOfE");
		String pageNumber = request.getParameter("pageNumber");
		String comment = request.getParameter("comment");
		
		if(!StringUtils.isBlank(id)){
			incdism = incdismSrv.get(id);
		}else{
			incdism = new IncDisManage();
		}
		
		try {
			incdism.setTitle(title);
			incdism.setIdUnit(idUnit);
			incdism.setRootInUnit(rootInUnit);
			incdism.setOriginalNumber(originalNumber);
			incdism.setIncDisCode(incDisCode);
			incdism.setOriginalDate(formatter.parse(originalDate));
			incdism.setIncDisDate(formatter.parse(incDisDate));
			incdism.setLimitDate(formatter.parse(limitDate));
			incdism.setCountCode(countCode);
			incdism.setsDepartment(sDepartment);
			incdism.setCoOrganizer(coOrganizer);
			incdism.setReadUnit(readUnit);
			incdism.setReadDoUnit(readDoUnit);
			incdism.setFileSecCla(fileSecCla);
			incdism.setSecrecyDateLimit(secrecyDateLimit);
			incdism.setDegreeOfE(degreeOfE);
			incdism.setPageNumber(pageNumber);
			incdism.setComment(comment);
			
		} catch (Exception e) {
			return MessageModel.createErrorMessage("时间错误！", null);
		}
		
		this.incdismSrv.saveOrUpdate(incdism);
		
		this.updateTodo(incdism, incdism.getTitle(),createTodoUri("/incdism/view/{id}", incdism.getId()));
		
		return MessageModel.createSuccessMessage(null, null);
	}
}
