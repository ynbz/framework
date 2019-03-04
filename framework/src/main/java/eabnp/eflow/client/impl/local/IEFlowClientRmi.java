package eabnp.eflow.client.impl.local;

import eabnp.basic.result.IResult;
import java.rmi.Remote;
import java.rmi.RemoteException;

public abstract interface IEFlowClientRmi extends Remote
{
  public abstract IResult Exec(String paramString, Object[] paramArrayOfObject)
    throws RemoteException;
}