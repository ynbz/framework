package com.suredy.test.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.ctrl.BaseCtrl;

@Controller
@RequestMapping({"/document"})
public class DocumentViewCtrl extends BaseCtrl{

	@RequestMapping(value = "/view")
	public ModelAndView uploadView() {
		ModelAndView view = new ModelAndView("/test/pdf-view/viewer");
		return view;
	}
	
}
