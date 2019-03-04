package com.suredy.app.task.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.task.entity.WorkGroupEntity;
import com.suredy.app.task.service.WorkGroupSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;

@Controller
@RequestMapping("/subject")
public class WorkGroupCtrl extends BaseCtrl{

	
	@Autowired
	private WorkGroupSrv groupSrv;
	
	@RequestMapping({"/group-tree"})
	@ResponseBody
	public Object getGroupTree() {
		//此处不带表单信息，仅仅显示表单分类
		List<Tree> typeTree = this.groupSrv.getGroupTree();
		return typeTree == null ? null : typeTree;
	}
	
	/**
	 * 列表页面
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/group-list")
	public ModelAndView getGroupList(String page, String size) {
		ModelAndView view = new ModelAndView("/app/subject/group-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		Integer count = this.groupSrv.Count();
		List<WorkGroupEntity> data = this.groupSrv.getAll(pageIndex, pageSize);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping(value = "/group-info")
	public ModelAndView groupForm(String id) {
		ModelAndView view = new ModelAndView("/app/subject/group-info");
		WorkGroupEntity group = null;
		if(!StringUtils.isBlank(id)){
			group = this.groupSrv.get(id);
		}else{
			group = new WorkGroupEntity();
		}
		view.addObject("group", group);
		return view;
	}
	
	/**
	 * 保存/修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/group-save")
	@ResponseBody
	public Object saveGroup(HttpServletRequest request) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String memberId = request.getParameter("memberId");
		String memberName = request.getParameter("memberName");
		String gruopLeaderId = request.getParameter("gruopLeaderId");
		String gruopLeaderName = request.getParameter("gruopLeaderName");
		WorkGroupEntity group = null;
		if(!StringUtils.isBlank(id)){
			group = this.groupSrv.get(id);
		}else{
			group = new WorkGroupEntity();
			group.setCreateTime(new Date());
		}
		group.setName(name);
		group.setMemberId(memberId);
		group.setMemberName(memberName);
		group.setLeaderId(gruopLeaderId);
		group.setLeaderName(gruopLeaderName);
		this.groupSrv.saveOrUpdate(group);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/group-delete")
	@ResponseBody
	public Object deleteGroup(String ids) {	
		if (StringUtils.isBlank(ids)) {
			return MessageModel.createErrorMessage("班组ID必须提供", null);
		}
		String[] idarray = ids.split(",");
		for(String id:idarray){
			WorkGroupEntity group = this.groupSrv.get(id);
			if(group!=null)
				this.groupSrv.delete(group);
			else
				continue;
		}
		return MessageModel.createSuccessMessage(null, null);
	}

}
