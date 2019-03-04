package com.suredy.test.ctrl;

import java.text.ParseException;
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
import com.suredy.security.model.User;
import com.suredy.security.service.UserSrv;
import com.suredy.test.model.WorkFlow;
import com.suredy.test.service.WorkFlowSrv;

/**
 * @author sdkj
 * 
 */
@Controller
@RequestMapping(value = "/workflow")
public class WorkFlowCtrl extends BaseFlowCtrl{
	

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmm");
	private SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private WorkFlowSrv wfsrv;
	
	@Autowired
	private UserSrv userSrv;
	
	
	@RequestMapping({"/new"})
	@ResponseBody
	public ModelAndView newFile() {
		WorkFlow wf = new WorkFlow();
		wf.setTitle(this.getUser().getUnitName() + "工作单" + formatter.format(new Date()));
		wf.setCreateName(this.getUser().getFullName());
		wf.setCreateNameId(this.getUser().getId());
		this.createFlow(wf, "gzlc", "F=gzlcsq/APP=SuredyOA/O=suredy", "/workflow/save/{id}",wf.getTitle());
		ModelAndView view = new ModelAndView( new RedirectView("save/"+wf.getId()));//createTodoUri
		return view;
	}
	
	/**
	 * 打开保存/修改页面
	 * @param wfid
	 * @return
	 */
	@RequestMapping(value = "save/{id}")
	public ModelAndView editRole(@PathVariable String id) {
		ModelAndView view = new ModelAndView("/test/workflow/save-edit");
		
		WorkFlow wf;
		if(StringUtils.isEmpty(id)){
			wf=new WorkFlow();
		}else{
			wf = this.wfsrv.get(id);
		}
		
		view.addObject("fileTypeName", "工作单");
		view.addObject("wf", wf);
		return view;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	@RequestMapping(value = "/getSelectUser")
	@ResponseBody
	public ModelAndView getSelectUser(String page, String size,String name ,String unit) {
		ModelAndView view = new ModelAndView("/test/workflow/selectUser");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}

		List<User> users = this.userSrv.getByFilter(pageIndex, pageSize, unit, name);
		Integer count = this.userSrv.CountByFilter(unit, name);
		
		
		view.addObject("data", users);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("name", name);
		view.addObject("unit", unit);
		return view;
	}
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("save-edit")
	@ResponseBody
	public Object doEditRole(HttpServletRequest request) {
		WorkFlow wf = this.wfsrv.get(request.getParameter("id"));
		String executeNameId=request.getParameter("executeNameId");
		String executeName=request.getParameter("executeName");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String content=request.getParameter("content");
		
		if(wf!=null){
			wf.setExecuteNameId(executeNameId);
			wf.setExecuteName(executeName);
			wf.setContent(content);
			try {
				if(!StringUtils.isEmpty(startTime)){
					wf.setStartTime(fm.parse(startTime));
				}
				if(!StringUtils.isEmpty(endTime)){
					wf.setEndTime(fm.parse(endTime));
				}
			} catch (ParseException e) {
				return MessageModel.createErrorMessage("时间错误！", null);
			}
			
			
			this.wfsrv.saveOrUpdate(wf);
			this.updateTodo(wf, wf.getTitle(),createTodoUri("/wf/save/{id}", wf.getId()));
		}else{
			return MessageModel.createErrorMessage(null, null);
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}
	

	/**
	 * 删除
	 * @param wfid
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Object doEditDelete(String wfid) {	
		if (StringUtils.isBlank(wfid)) {
			return MessageModel.createErrorMessage("工作单ID必须提供", null);
		}
		WorkFlow wf = this.wfsrv.get(wfid);
		if (wf == null) {
			return MessageModel.createErrorMessage("未找到与ID['"+ wfid +"']对应的工作单", null);
		}
		
		this.wfsrv.delete(wf);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	

}
