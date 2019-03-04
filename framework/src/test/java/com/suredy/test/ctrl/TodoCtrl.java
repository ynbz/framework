package com.suredy.test.ctrl;

import java.rmi.RemoteException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.core.ctrl.BaseFlowCtrl;

import eabnp.basic.result.IResult;
import eabnp.eflow.client.impl.local.IEFlowClientRmi;
import eabnp.eflow.info.ctrlinfo.todone.TodoneListInfo;

@Controller
@RequestMapping("/todo")
public class TodoCtrl extends BaseFlowCtrl {

	private final String MSG_APP_ERROR = "获取代办列表出错，请联系管理员协助处理！";

	@Value("${SYS.NAME}")
	private String sysName;
	
	@RequestMapping({"todoList"})
	public ModelAndView getTodoList() {
		ModelAndView view = new ModelAndView("test/todo/todo_list");

		IEFlowClientRmi client = getEFlowClient();

		try {
			IResult result = client.Exec("getTodoList", new Object[] {getESession().getSessionId(), sysName, ""});

			if (!result.isSuccess()) {
				return view;
			}

			if (result.isSuccess()) {
				TodoneListInfo list = (TodoneListInfo) result.getResultObject();
				view.addObject("data", list.getTodoList());
				view.addObject("count", list.getTodoList().size());
			}
		} catch (RemoteException e) {
			return gotoErrorPage(this.MSG_APP_ERROR);
		}

		return view;
	}

}
