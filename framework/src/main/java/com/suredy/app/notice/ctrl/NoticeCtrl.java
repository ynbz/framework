package com.suredy.app.notice.ctrl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.suredy.app.notice.entity.NoticeEntity;
import com.suredy.app.notice.service.NoticeSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.security.model.User;

/**
 * 公告控制类
 * @author zyh
 *
 */
@Controller
@RequestMapping({"/notice"})
public class NoticeCtrl extends BaseCtrl{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 日期格式化
	
	@Autowired
	private NoticeSrv notSrv;
	
	/**
	 * 列表页面
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/notice-list")
	public ModelAndView getNoticeList(String page, String size,String type) {
		ModelAndView view = new ModelAndView("/app/notice/notice-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		Integer count = this.notSrv.Count();
		List<NoticeEntity> data = this.notSrv.getAll(pageIndex, pageSize);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping(value = "/notice-info")
	public ModelAndView noticeForm(String id) {
		ModelAndView view = new ModelAndView("/app/notice/notice-info");
		NoticeEntity notice = null;
		if(!StringUtils.isBlank(id)){
			notice = this.notSrv.get(id);
		}else{
			notice = new NoticeEntity();
			User user = this.getUser();
			notice.setIssuerId(user.getId());
			notice.setIssuer(user.getName());
			notice.setIssueUnitId(user.getUnitId());
			notice.setIssueUnit(user.getUnitName());
		}
		view.addObject("notice", notice);
		return view;
	}
	
	@RequestMapping(value = "/notice-view")
	public ModelAndView noticeView(String id) {
		ModelAndView view = new ModelAndView("/app/notice/notice-view");
		NoticeEntity notice = new NoticeEntity();
		if(!StringUtils.isBlank(id)){
			notice = this.notSrv.get(id);
		}
		view.addObject("notice", notice);
		return view;
	}
	
	@RequestMapping({"/notice-save"})
	@ResponseBody
	public Object saveNotice(HttpServletRequest request) {
		NoticeEntity notice = null;
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String validDate = request.getParameter("validDate");
		String type = request.getParameter("type");
		String fileId = request.getParameter("fileId");
		String content = request.getParameter("content");
		if (StringUtils.isEmpty(id)) {
			notice = new NoticeEntity();
			Date date = new Date();
			User user = this.getUser();
			notice.setIssuerId(user.getId());
			notice.setIssuer(user.getName());
			notice.setIssueUnitId(user.getUnitId());
			notice.setIssueUnit(user.getUnitName());
			notice.setCreateDate(date);
		} else {
			notice = this.notSrv.get(id);
			
		}
		
		notice.setTitle(title);
		try {
			notice.setValidDate(sdf.parse(validDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		notice.setType(type);
		notice.setFileId(fileId);
		notice.setContent(content);
		
		this.notSrv.saveOrUpdate(notice);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/notice-delete")
	@ResponseBody
	public Object deleteNotice(String ids) {	
		if (StringUtils.isBlank(ids)) {
			return MessageModel.createErrorMessage("公告ID必须提供", null);
		}
		String[] idarray = ids.split(",");
		String fileids="";
		for(String id:idarray){
			NoticeEntity notice = this.notSrv.get(id);
			if(notice!=null)
				notice=this.notSrv.delete(notice);
			else
				continue;
			if("".equals(fileids)){
				fileids = notice.getFileId();
			}else{
				fileids = fileids+","+notice.getFileId();
			}
		}
		return MessageModel.createSuccessMessage(fileids, null);
	}

}
