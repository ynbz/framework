package com.suredy.flow;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.lang3.StringUtils;

import com.suredy.app.liveform.model.src.LiveFormSrv;
import com.suredy.app.project.entity.ProjectEntity;
import com.suredy.app.project.service.ProjectSrv;
import com.suredy.core.helper.ApplicationContextHelper;
//import com.suredy.test.model.DisManage;
//import com.suredy.test.model.IncDisManage;
//import com.suredy.test.model.WorkFlow;
//import com.suredy.test.service.DisManageSrv;
//import com.suredy.test.service.IncDisManageSrv;
//import com.suredy.test.service.WorkFlowSrv;

import eabnp.eflow.api.TodoItemInfo;
import eabnp.eflow.info.ctrlinfo.processctrl.actresult.ActResultListCtrl;

public class FlowRmiSrvImpl extends UnicastRemoteObject
  implements IFlowRmiSrv
{
  private static final long serialVersionUID = 1L;
  private String name;
  

  public FlowRmiSrvImpl()
    throws RemoteException
  {
  }

  public TodoItemInfo SetTodeInfo(TodoItemInfo info, String appType, String fileId)
    throws RemoteException
  {
	return null;
//	if(appType.equals("gzlc")){
//		WorkFlowSrv fwsvr = ApplicationContextHelper.getBeanByType(WorkFlowSrv.class);
//		WorkFlow wf = fwsvr.get(fileId);
//		info.setTitle(wf.getTitle());
//		info.setLink(createTodoUri("/workflow/save/{id}", wf.getId()));
//	}
//	
//	if(appType.equals("fwlc")){
//		DisManageSrv dismSrv = ApplicationContextHelper.getBeanByType(DisManageSrv.class);
//		DisManage dm = dismSrv.get(fileId);
//		info.setTitle(dm.getTitle());
//		info.setLink(createTodoUri("/dism/view/{id}", dm.getId()));
//	}
//	
//	if (appType.equals("LiveFormSrv")) {
//		LiveFormSrv srv = ApplicationContextHelper.getBeanByType(LiveFormSrv.class);
//		String ql = "SELECT T.title FROM LiveForm T WHERE T.id = ?";
//		String title = (String) srv.readSingleByQL(ql, fileId);
//		info.setTitle(title);
//		info.setLink(createTodoUri("/live-form/open/{id}", fileId));
//	}
//	
//	if(appType.equals("swlc")){
//		IncDisManageSrv incdismSrv = ApplicationContextHelper.getBeanByType(IncDisManageSrv.class);
//		IncDisManage idm = incdismSrv.get(fileId);
//		info.setTitle(idm.getTitle());
//		info.setLink(createTodoUri("/incdism/view/{id}", idm.getId()));
//	}
//	
//	if(appType.equals("scjh")){
//		ProjectSrv proSrv = ApplicationContextHelper.getBeanByType(ProjectSrv.class);
//		ProjectEntity pro = proSrv.get(fileId);
//		info.setTitle(pro.getTitle());
//		info.setLink(createTodoUri("/project/view/{id}", pro.getId()));
//	}
//    
//    return info;
  }

  public boolean actRsProcess(ActResultListCtrl actRsCtrl)
    throws RemoteException
  {
	  return false;
//	  if ("F=gzlcsq/APP=SuredyOA/O=suredy".equals( actRsCtrl.getFlowUN())) {
//		  WorkFlowSrv srv = ApplicationContextHelper.getBeanByType(WorkFlowSrv.class);
//		  WorkFlow data = srv.getByProcessId(actRsCtrl.getProcessId());
//			if(data!=null)
//				data.setFlowState(actRsCtrl.getProcessChangeCtrl().getStepName());
//			srv.update(data);
//		}
//	  
//	  if ("F=fwgzlc/APP=SuredyOA/O=suredy".equals( actRsCtrl.getFlowUN())) {
//		  DisManageSrv dismSrv = ApplicationContextHelper.getBeanByType(DisManageSrv.class);
//		  DisManage data = dismSrv.getByProcessId(actRsCtrl.getProcessId());
//			if(data!=null)
//				data.setFlowState(actRsCtrl.getProcessChangeCtrl().getStepName());
//			dismSrv.update(data);
//		}
//	  
//	  if ("F=swgzlc/APP=SuredyOA/O=suredy".equals( actRsCtrl.getFlowUN())) {
//		  IncDisManageSrv incdismSrv = ApplicationContextHelper.getBeanByType(IncDisManageSrv.class);
//		  IncDisManage data = incdismSrv.getByProcessId(actRsCtrl.getProcessId());
//			if(data!=null)
//				data.setFlowState(actRsCtrl.getProcessChangeCtrl().getStepName());
//			incdismSrv.update(data);
//		}
//	  
//	  if ("F=scjhlc/APP=SuredyOA/O=suredy".equals( actRsCtrl.getFlowUN())) {
//		  ProjectSrv proSrv = ApplicationContextHelper.getBeanByType(ProjectSrv.class);
//		  ProjectEntity data = proSrv.getByProcessId(actRsCtrl.getProcessId());
//			if(data!=null)
//				data.setFlowState(actRsCtrl.getProcessChangeCtrl().getStepName());
//			proSrv.update(data);
//		}
//    return false;
  }

  public FlowRmiSrvImpl(String name) throws RemoteException
  {
    this.name = name;
  }
  
  protected String createTodoUri(String formUri, String fileId) {
		if (StringUtils.isBlank(formUri) || StringUtils.isBlank(fileId))
			throw new IllegalArgumentException("Invalid parameters. Please check it now.");

		if (formUri.indexOf("{id}") == -1)
			throw new IllegalArgumentException("Invalid parameter String[formUri]. It must contain a placeholder '{id}'.");

		String cp = InitFlowRmi.context_path;

		if (!StringUtils.isBlank(cp) && formUri.startsWith(cp))
			cp = "";

		if (!formUri.startsWith("/"))
			cp += "/";

		formUri = cp + formUri;

		return formUri.replace("{id}", fileId);
	}
}