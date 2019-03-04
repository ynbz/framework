package com.suredy.config.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.suredy.core.ctrl.BaseCtrl;

@Controller
@RequestMapping(value="/config")
public class ConfigIndexCtrl extends BaseCtrl {
	
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String Index() {
		return "config/index";
	}
}
