package com.suredy.core.ctrl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.suredy.Constants;
import com.suredy.core.model.BaseFlowModel;
import com.suredy.core.service.BaseSrv;
import com.suredy.security.model.User;

import eabnp.basic.result.IResult;
import eabnp.eflow.client.impl.local.IEFlowClientRmi;
import eabnp.eflow.info.ctrlinfo.processctrl.ProcessCtrl;
import eabnp.eflow.info.ctrlinfo.todone.TodoneInfo;
import eabnp.eflow.info.ctrlinfo.todone.TodoneListInfo;
import eabnp.eflow.session.ESession;

public class BaseFlowCtrl extends BaseCtrl {

	private final static Logger log = LoggerFactory.getLogger(BaseFlowCtrl.class);

	@Autowired
	@Qualifier("com.suredy.core.service.BaseSrv")
	private BaseSrv baseSrv;

	
	@Value("${FLOW.RIM}")
	private String eflow_rmi;
	
	@Value("${SYS.NAME}")
	private String sysName;

	protected void setESession(ESession es) {
		this.setSessionAttribute(Constants.FLOW_SESSION, es);
	}

	protected ESession getESession() {
		Object val = this.getSessionAttribute(Constants.FLOW_SESSION);

		return (ESession) val;
	}

	protected IEFlowClientRmi getEFlowClient() {
		IEFlowClientRmi client = null;

		try {
			RmiProxyFactoryBean factory = new RmiProxyFactoryBean();

			factory.setServiceInterface(IEFlowClientRmi.class);
			factory.setServiceUrl(eflow_rmi);
			factory.setCacheStub(false);
			factory.setLookupStubOnStartup(true);
			factory.afterPropertiesSet();

			client = (IEFlowClientRmi) factory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return client;
	}

	protected String launchFlow(String flowCode) {
		String sessionId = getESession().getSessionId();

		try {
			IResult result = getEFlowClient().Exec("launchProcess", new Object[] {sessionId, flowCode});

			if (result.isSuccess())
				return ((ProcessCtrl) result.getResultObject()).getProcessId();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return null;
	}

	protected void addBlankTodo(String flowUN, String nodeName, String title, String id, String url, String processId) {
		String sessionId = getESession().getSessionId();

		try {
			int i = 0;

			getEFlowClient().Exec("addTodo", new Object[] {sessionId, processId, getFlowUser().getCode(), getFlowUser().getName(), getFlowUser().getCode(), getFlowUser().getName(), flowUN, title, nodeName, url, id, Integer.valueOf(i)});
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	protected void delTodo(String processId){
		String sessionId = getESession().getSessionId();
		IResult result;
		try {
			result = getEFlowClient().Exec("getTodoList",new Object[] {sessionId,sysName,null});
			
			if (result.isSuccess()){
				
				List list = ((TodoneListInfo) result.getResultObject()).getTodoList();
				if(!list.isEmpty()){
					TodoneInfo todoInfo;
					String todoIdList = "";
					for(Object todo:list){
						todoInfo = (TodoneInfo) todo;
						if (todoInfo.getProcessId().equals(processId)) {
							todoIdList +=";" + todoInfo.getId();
						}
					}
					getEFlowClient().Exec("delTodo",new Object[] {sessionId,todoIdList});
				}
			}
		}catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	protected String createTodoUri(String formUri, String fileId) {
		if (StringUtils.isBlank(formUri) || StringUtils.isBlank(fileId))
			throw new IllegalArgumentException("Invalid parameters. Please check it now.");

		if (formUri.indexOf("{id}") == -1)
			throw new IllegalArgumentException("Invalid parameter String[formUri]. It must contain a placeholder '{id}'.");

		String cp = this.getContextPath();

		if (!StringUtils.isBlank(cp) && formUri.startsWith(cp))
			cp = "";

		if (!formUri.startsWith("/"))
			cp += "/";

		formUri = cp + formUri;

		return formUri.replace("{id}", fileId);
	}

	protected <T extends BaseFlowModel> T createFlow(T t, String fileTypeCode, String flowCode, String formUri,String title) {
		if (t == null || StringUtils.isBlank(fileTypeCode) || StringUtils.isBlank(flowCode) || StringUtils.isBlank(formUri))
			throw new IllegalArgumentException("Invalid parameters. Please check it now.");

		if (this.getUser() == null)
			throw new RuntimeException("Can not get login user info.");

		String processId = this.launchFlow(flowCode);

		if (StringUtils.isBlank(processId)) {
			log.error("Can not create a new flow with code[" + flowCode + "].");
			return null;
		}

		t.setProcessId(processId);
		t.setFileTypeCode(fileTypeCode);
		t.setCreateTime(new Date());
		t.setCreatorCode(this.getFlowUser().getCode());
		t.setCreatorFullName(this.getFlowUser().getFullName());
		t.setCreatorUnitCode(this.getFlowUser().getUnitUniqueCode());

		t = this.baseSrv.saveOrUpdate(t);

		if (t == null)
			throw new RuntimeException("Save business flow entity error.");

		this.addBlankTodo(flowCode, title, title, t.getId(), this.createTodoUri(formUri, t.getId()), processId);

		return t;
	}

	public boolean updateTodo(BaseFlowModel t, String title, String url) {
		String sessionId = getESession().getSessionId();
		String processId = t.getProcessId();

		try {
			getEFlowClient().Exec("resetTodoInfo", new Object[] {sessionId, processId, title, url, t.getId()});
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public User getFlowUser() {
		User user = super.getUser();

		if (User.class.isInstance(user))
			return (User) user;

		throw new RuntimeException("Invalid login user info. It is not a flow user.");
	}

}
