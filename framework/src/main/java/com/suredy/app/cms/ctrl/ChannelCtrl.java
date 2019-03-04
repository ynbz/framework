/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * @since 2017年12月8日
 * @version 1.0
 */
package com.suredy.app.cms.ctrl;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.app.cms.entity.Channel;
import com.suredy.app.cms.service.ChannelSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;


/**
 * @author ZhangMaoren
 *
 * 2017年12月8日
 */
@Controller
@RequestMapping(value = "app/cms")
public class ChannelCtrl extends BaseCtrl implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7026618509278228822L;

	@Autowired
	private ChannelSrv channelSrv;
	
	@RequestMapping(value = "/channel/manager")
	public ModelAndView manager(String page, String size) {
		ModelAndView view = new ModelAndView("/app/cms/channel-manager");
		return view;
	}
	

	
	@RequestMapping("/channel/tree")
	@ResponseBody
	public Object channelTree() {
		List<Tree> tree = this.channelSrv.getChannelTree(null);
		return tree == null ? null : tree;
	}
	
	@RequestMapping(value = "/channel/form")
	public ModelAndView channelForm(String channelId, String parentId) {
		ModelAndView view = new ModelAndView("/app/cms/channel-form");
		Channel data;
		if (StringUtils.isEmpty(channelId)) {
			data = new Channel();
			data.setParent(new Channel());
			data.getParent().setId(parentId);
		} else {
			data = this.channelSrv.get(channelId);
		}
		view.addObject("data", data);
		return view;
	}

	@RequestMapping("/channel/save")
	@ResponseBody
	public Object channelSave(HttpServletRequest request) {
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String name = request.getParameter("name");
		String sort = request.getParameter("sort");
		if (StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 栏目名称必须填写", null);
		}
		Channel channel = null;
		if (StringUtils.isEmpty(id)) {
			channel = new Channel();
			channel.setName(name);
			channel.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			if (!StringUtils.isEmpty(parent)) {
				Channel p = this.channelSrv.get(parent);
				channel.setParent(p);
				channel.setFullName(name + "/" + p.getFullName());
			} else {
				channel.setFullName(name);
				channel.setParent(null);
			}
			this.channelSrv.save(channel);
		} else {
			channel = this.channelSrv.get(id);
			if (channel == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的栏目信息", null);
			}

			channel.setName(name);
			channel.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));

			if (!StringUtils.isEmpty(parent)) {
				if (!parent.equals(id)) {
					Channel p = this.channelSrv.get(parent);
					channel.setParent(p);
					channel.setFullName(name + "/" + p.getFullName());
				}
			} else {
				channel.setFullName(name);
				channel.setParent(null);
			}
			this.channelSrv.update(channel);
		}
		return MessageModel.createSuccessMessage(null, channel.getId());
	}

	@Transactional
	@RequestMapping("/channel/delete")
	@ResponseBody
	public Object channelDelete(String channelId) {
		if (StringUtils.isEmpty(channelId)) {
			return MessageModel.createErrorMessage("栏目ID必须提供！", null);
		}

		Channel menu = this.channelSrv.get(channelId);

		if (menu == null) {
			return MessageModel.createErrorMessage("未找到与ID["+ channelId +"]对应的栏目信息！", null);
		}
		Channel parent = menu.getParent();
		if (parent != null) {
			parent.getChildren().remove(menu);
			menu.setParent(null);
		}

		try {
			this.channelSrv.delete(menu);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除不成功,栏目信息错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
}
