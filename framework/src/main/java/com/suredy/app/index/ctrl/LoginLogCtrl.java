package com.suredy.app.index.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.security.entity.LoginLogEntity;
import com.suredy.security.service.LoginLogSrv;

@Controller
@RequestMapping(value="/config")
public class LoginLogCtrl extends BaseFlowCtrl {
	
	@Autowired
	private LoginLogSrv llSrv;
	
	@RequestMapping(value = "/loginlog/list")
	public ModelAndView restoreDBList(String page, String size,String startTime,String endTime,String loginName) {
		
		ModelAndView view = new ModelAndView("/config/loginLog-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<LoginLogEntity> data = llSrv.getAll(pageIndex, pageSize, startTime, endTime,loginName);
		int count = llSrv.count( startTime, endTime,loginName);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		
		return view;
	}

}
