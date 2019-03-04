package com.suredy.app.task.ctrl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.task.entity.ScheduleEntity;
import com.suredy.app.task.service.ScheduleSrv;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.security.model.User;

@Controller
@RequestMapping({"/schedule"})
public class ScheduleCtrl {

	@Autowired
	private ScheduleSrv scheduleSrv;

	@RequestMapping(value = "/index")
	public ModelAndView getSchedules() {
		ModelAndView view = new ModelAndView("/app/schedule/index");
		return view;
	}

	@RequestMapping({"/tasks"})
	@ResponseBody
	public Object getTasks(String startTime, String endTime) {
		List<ScheduleEntity> data =this.scheduleSrv.getSchedules(startTime, endTime);
		return MessageModel.createSuccessMessage(null, data);
	}	
	
	@RequestMapping(value = "/task-info")
	public ModelAndView taskInfo(String id) {
		ModelAndView view = new ModelAndView("/app/schedule/task-info");
		ScheduleEntity task = null;
		if(StringUtils.isNotEmpty(id)){
			task = this.scheduleSrv.get(id);
		}else{
			task = new ScheduleEntity();
		}
		view.addObject("task", task);
		return view;
	}

		
	private static final String[] classes = {"event-important", "event-info", "event-warning", "event-inverse", "event-success", "event-special"};	
	/**
	 * 保存/修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/task-save")
	@ResponseBody
	public Object saveTask(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		String creatorId = user.getId();
		String creatorName = user.getFullName();
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String subjectId = request.getParameter("subjectId");
		String subjectName = request.getParameter("subjectName");
		Integer subjectType = StringUtils.isEmpty(request.getParameter("subjectType")) ? 0 : Integer.valueOf(request.getParameter("subjectType"));
		Random random = new Random();
		String className = classes[random.nextInt(6)];
		Date start = null, end = null;
		try {
			start = StringUtils.isEmpty(startTime) ? new Date() : Constants.dateTimeformater1.parse(startTime);
			end = StringUtils.isEmpty(endTime) ? new Date() : Constants.dateTimeformater1.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		ScheduleEntity task = null;
		if(!StringUtils.isEmpty(id)){
			task = this.scheduleSrv.get(id);
		} else {
			task = new ScheduleEntity();
			task.setCreateTime(new Date());
			task.setCreatorId(creatorId);
			task.setCreatorName(creatorName);
			task.setClassName(className);
		}
		task.setDescription(description);
		task.setEnd(end);
		task.setName(name);
		task.setStart(start);
		task.setSubjectId(subjectId);
		task.setSubjectId(subjectId);
		task.setSubjectName(subjectName);
		task.setSubjectType(subjectType);
		
		if (StringUtils.isEmpty(id)) {
			this.scheduleSrv.save(task);
		} else {
			this.scheduleSrv.update(task);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

}
