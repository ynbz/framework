package com.suredy.app.project.ctrl;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
import com.suredy.app.project.entity.ProjectEntity;
import com.suredy.app.project.entity.ProScheduleEntity;
import com.suredy.app.project.service.ProjectSrv;
import com.suredy.app.project.service.ProScheduleSrv;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.manager.entity.FlowEntity;
import com.suredy.flow.manager.service.FlowManagerSrv;
import com.suredy.security.model.User;

/**
 * @Title ProjectCtrl
 * @Package com.suredy.app.project.ctrl
 * @Description 生产计划管理控制类
 * @author zyh
 * @date 2017-04-07
 *
 */
@Controller
@RequestMapping(value = "/project")
public class ProjectCtrl extends BaseFlowCtrl{
	
	@Autowired
	private ProjectSrv proSrv;
	
	@Autowired
	private ProScheduleSrv schSrv;
	
	@Autowired
	private FormSrv formSrv;
	
	@Autowired
	private FlowManagerSrv flowManagerSrv;
	
	@RequestMapping(value = "/list")
	public ModelAndView list(String page, String size,String startTime,String endTime,String fileTypeCode) {
		
		ModelAndView view = new ModelAndView("/app/project/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<ProjectEntity> data = proSrv.getAll(pageIndex, pageSize, startTime, endTime,fileTypeCode);
		int count = proSrv.count( startTime, endTime,fileTypeCode);
		
		view.addObject("data", data);
		view.addObject("fileTypeCode", fileTypeCode);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping({"/newpro/{fileType}"})
	@ResponseBody
	public ModelAndView newpro(@PathVariable String fileType) {
		FormEntity form = null;
		ModelAndView view = null;
		if(!StringUtils.isBlank(fileType)){
			form = formSrv.getByFileType(fileType);
			if(form!=null){
				FlowEntity flm = flowManagerSrv.getById(form.getFlowId());
				ProjectEntity pro = new ProjectEntity();
				User user = this.getUser();
				pro.setProPerson(user.getName());
				pro.setProUnit(user.getUnitName());
				this.createFlow(pro, form.getFileType(),flm.getCode(),form.getDoSthPath(),pro.getTitle());
				
				view = new ModelAndView(new RedirectView(this.createTodoUri(form.getDoSthPath(), pro.getId())));//createTodoUri
			}
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
		ModelAndView view = new ModelAndView("/app/project/addPro");
		
		ProjectEntity pro;
		if(StringUtils.isEmpty(id)){
			pro=new ProjectEntity();
		}else{
			pro = this.proSrv.get(id);
		}
		
		view.addObject("fileTypeName", "生产计划");
		view.addObject("pro", pro);
		return view;
	}
	
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("pro-save")
	@ResponseBody
	public Object proSave(HttpServletRequest request) {
		ProjectEntity pro = null;
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String fileType = request.getParameter("fileType");
		
		FormEntity form = null;
		if(!StringUtils.isBlank(fileType)){
			form = formSrv.getByFileType(fileType);
		}
		
		if(!StringUtils.isBlank(id)){
			pro = proSrv.get(id);
		}else{
			pro = new ProjectEntity();
		}
		pro.setTitle(title);
		pro.setComment(comment);
		this.proSrv.saveOrUpdate(pro);
		if(form!=null)
			this.updateTodo(pro, pro.getTitle(),createTodoUri(form.getDoSthPath(), pro.getId()));
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	/**
	 * 创建发布时间
	 * @param id
	 * @return
	 */
	@RequestMapping("createIssueDate")
	@ResponseBody
	public Object createIssueDate(String id) {
		ProjectEntity pro = null;
		
		
		if(!StringUtils.isBlank(id)){
			pro = proSrv.get(id);
		}
		if(pro!=null){
			pro.setIssueDate(new Date());
		}else{
			return MessageModel.createErrorMessage(null, null);
		}
		this.proSrv.saveOrUpdate(pro);
	
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/pro-delete")
	@ResponseBody
	public Object deleteNotice(String id) {	
		ProjectEntity pro = null;
		if (StringUtils.isBlank(id)) {
			return MessageModel.createErrorMessage("计划ID必须提供", null);
		}else{
			pro = proSrv.get(id);
		}
		Set<ProScheduleEntity> schs = pro.getSchedules();
		for(ProScheduleEntity sch:schs){
			this.schSrv.delete(sch);
		}
		
		this.delTodo(pro.getProcessId());
		this.proSrv.delete(pro);
		
		return MessageModel.createSuccessMessage(null, null);
	}

}
