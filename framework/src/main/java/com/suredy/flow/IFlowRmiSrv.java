package com.suredy.flow;

import eabnp.eflow.api.TodoItemInfo;
import eabnp.eflow.info.ctrlinfo.processctrl.actresult.ActResultListCtrl;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface IFlowRmiSrv extends Remote
{
  public abstract TodoItemInfo SetTodeInfo(TodoItemInfo paramTodoItemInfo, String paramString1, String paramString2)
    throws RemoteException;

  public abstract boolean actRsProcess(ActResultListCtrl paramActResultListCtrl)
    throws RemoteException;
}