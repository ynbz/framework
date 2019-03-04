package com.suredy.test.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.suredy.core.ctrl.BaseCtrl;

@Controller
@RequestMapping("/test")
public class TestIndexCtrl extends BaseCtrl {

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String loginIndex() {
		return "test/index";
	}

}
