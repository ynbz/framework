/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * @since 2017年12月8日
 * @version 1.0
 */
package com.suredy.app.cms.ctrl;

import java.io.Serializable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.ctrl.BaseCtrl;

/**
 * @author ZhangMaoren
 *
 * 2017年12月8日
 */
@Controller
@RequestMapping(value = "app/cms")
public class ArticleCtrl extends BaseCtrl implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7823639854741216082L;

	@RequestMapping(value = "/article/manager")
	public ModelAndView manager(String page, String size, String channelId, String keyword) {
		ModelAndView view = new ModelAndView("/app/cms/article-manager");
		return view;
	}
	
	@RequestMapping(value = "/article/form")
	public ModelAndView articleForm(String articleId) {
		ModelAndView view = new ModelAndView("/app/cms/article-form");
		return view;
	}
}
