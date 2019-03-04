package com.suredy.flow.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.flow.EFlowSenderGetter;
import com.suredy.flow.model.SenderData;

@Controller
@RequestMapping({"/flow"})
public class FlowCtrl extends BaseCtrl {

	@RequestMapping({"/sender"})
	public ModelAndView sender(String xml, String paraType,String para) {
		if(paraType==null){
			paraType="";
		}
		if(para==null){
			para="";
		}
		ModelAndView view = new ModelAndView("component/flow/sender");
		 EFlowSenderGetter x = new EFlowSenderGetter();
		 String[] paras = para.split(",");
		 SenderData senderData = x.load(xml, paras, paraType);
		 ObjectMapper objectMapper = new ObjectMapper();
		 String retval="";
		 try {
			 retval=objectMapper.writeValueAsString(senderData.getUsers());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		 view.addObject("sendData", senderData);
		 view.addObject("treeData", retval);
		return view;
	}
	
	@RequestMapping({"/comment"})
	public ModelAndView comment(String xml, String paraType,String para) {
		ModelAndView view = new ModelAndView("component/flow/comment");
		return view;
	}

	
}
