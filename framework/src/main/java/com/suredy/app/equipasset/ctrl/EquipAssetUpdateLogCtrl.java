package com.suredy.app.equipasset.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.equipasset.model.EquipAssetUpdateLog;
import com.suredy.app.equipasset.service.EquipAssetUpdateLogSrv;
import com.suredy.core.ctrl.BaseCtrl;

@Controller
@RequestMapping(value = "/eqUpdateLog")
public class EquipAssetUpdateLogCtrl extends BaseCtrl {
	
	@Autowired
	private EquipAssetUpdateLogSrv eaulSrv;
	
	/**
	 * 未完结耗材申请单
	 * 
	 * @return
	 */
	@RequestMapping({"/eqLog-list"})
	@ResponseBody
	public ModelAndView getList(String startTime,String endTime,String page, String size) {
		ModelAndView view = new ModelAndView("/app/equipasset/eqLog-list");

		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		Long count = eaulSrv.count(startTime,endTime);
		List<EquipAssetUpdateLog> data = eaulSrv.getList(startTime,endTime,pageIndex,pageSize);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("startTime", startTime);
		view.addObject("endTime", endTime);		
				
		return view;
	}

}
