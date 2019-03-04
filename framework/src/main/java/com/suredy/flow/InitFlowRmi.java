package com.suredy.flow;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 0, urlPatterns = {"/initrmi"})
public class InitFlowRmi extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String context_path="";

	public void init(ServletConfig config) throws ServletException {
		context_path=config.getServletContext().getContextPath();
		try {
			Context namingContext = new InitialContext();
			FlowRmiSrvImpl flowrmi = new FlowRmiSrvImpl("apprmi");
			LocateRegistry.createRegistry(8008);
			namingContext.rebind("rmi://127.0.0.1:8008/apprmi", flowrmi);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
