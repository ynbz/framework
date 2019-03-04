package com.suredy.app.project.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.app.project.entity.ProjectEntity;
import com.suredy.app.project.entity.ProScheduleEntity;
import com.suredy.app.project.service.ProjectSrv;
import com.suredy.app.project.service.ProScheduleSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;

/**
 * @Title ProScheduleCtrl
 * @Package com.suredy.app.project.ctrl
 * @Description 生产计划表管理控制类
 * @author zyh
 * @date 2017-04-07
 *
 */
@Controller
@RequestMapping(value = "/pro-schedule")
public class ProScheduleCtrl extends BaseCtrl{
	
	@Autowired
	private ProjectSrv proSrv;
	
	@Autowired
	private ProScheduleSrv schSrv;
	
	/**
	 * 获取修改日志
	 * 
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sch-list/{id}", method = RequestMethod.GET)
	public String eqLogList(@PathVariable String id, Integer page, Integer pageSize, Model model) {
		String view = "app/project/sch-list";
		ProScheduleEntity search = new ProScheduleEntity();
		ProjectEntity pro = null;
		
		if (StringUtils.isBlank(id)) {
			model.addAttribute("invalid", true);
			return view;
		}else{
			pro=proSrv.get(id);
			search.setProject(pro);
		}

		if (page == null || page < 1)
			page = 1;

		if (pageSize == null || pageSize < 1)
			pageSize = 25;

		List<ProScheduleEntity> data = this.schSrv.readPageByEntity(search, page, pageSize);
		int count = 0;

		if (data != null && !data.isEmpty()) {
			count = this.schSrv.getCountByEntity(search);
		}

		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("count", count);

		return view;
	}
	
	/**
	 * 打开保存/修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "sch-info")
	public ModelAndView view(String proid,String schid) {
		ModelAndView view = new ModelAndView("/app/project/sch-info");
		ProScheduleEntity sch = null;
		if(!StringUtils.isBlank(schid)){
			sch = schSrv.get(schid);
		}else{
			sch = new ProScheduleEntity();
		}
		view.addObject("proid", proid);
		view.addObject("sch", sch);
		return view;
	}
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("sch-save")
	@ResponseBody
	public Object doEditRole(HttpServletRequest request) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");	
		ProjectEntity pro = null;
		ProScheduleEntity sch = null;
		
		String schid = request.getParameter("schid");
		String proid = request.getParameter("proid");
		String periodStart = request.getParameter("periodStart");
		String periodEnd = request.getParameter("periodEnd");
		String number = request.getParameter("number");
		String unit = request.getParameter("unit");
		
		if(!StringUtils.isBlank(proid)){
			pro = proSrv.get(proid);
		}else{
			return MessageModel.createErrorMessage("缺省生产计划id！", null);
		}
		
		if(!StringUtils.isBlank(schid)){
			sch = schSrv.get(schid);
		}else{
			sch = new ProScheduleEntity();
			sch.setCreateDate(new Date());
		}
		 try {
			sch.setPeriodStart(formatter.parse(periodStart));
			sch.setPeriodEnd(formatter.parse(periodEnd));
			sch.setNumber(Integer.parseInt(number));
			sch.setUnit(unit);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageModel.createErrorMessage("周期时间错误！", null);
		}
		 sch.setProject(pro);
		
		this.schSrv.saveOrUpdate(sch);
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/sch-delete")
	@ResponseBody
	public Object deleteNotice(String ids) {	
		if (StringUtils.isBlank(ids)) {
			return MessageModel.createErrorMessage("计划表ID必须提供", null);
		}
		String[] idarray = ids.split(",");
		for(String id:idarray){
			ProScheduleEntity sch = schSrv.get(id);
			if(sch!=null)
				this.schSrv.delete(sch);
			else
				continue;
		}
		return MessageModel.createSuccessMessage(null, null);
	}


}
