package com.suredy.app.equipoutstorage.ctrl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.equipoutstorage.model.EquipOutStorage;
import com.suredy.app.equipoutstorage.service.EquipOutStorageSrv;

@Controller
@RequestMapping(value="/EquipOutStoraneCtrl")
public class EquipOutStoraneCtrl {

	@Autowired
	private EquipOutStorageSrv equipOutStorageSrv;
	@RequestMapping(value="/getEquipOutStorageList")
	public ModelAndView getEquipOutStorageList(String page, String size,String assetId){
		ModelAndView view=new ModelAndView("/app/equipoutstorage/equipback-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		int count  = this.equipOutStorageSrv.Count(assetId);
		List<EquipOutStorage> equipOutStorageList=equipOutStorageSrv.getEquipOutStorageList(pageIndex, pageSize,assetId);
		view.addObject("data",equipOutStorageList);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;		
	}
}
