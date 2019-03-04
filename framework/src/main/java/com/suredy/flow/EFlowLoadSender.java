package com.suredy.flow;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suredy.flow.model.SenderData;

@WebServlet({"/core/EFlowLoad"})
public class EFlowLoadSender extends HttpServlet
{
  private static final long serialVersionUID = 8369933850424663211L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setCharacterEncoding("utf-8");
    String xml = URLDecoder.decode(request.getParameter("xml"), "UTF-8");
    String paraType = URLDecoder.decode(request.getParameter("paraType"), "UTF-8");
    String para = URLDecoder.decode(request.getParameter("para"), "UTF-8");
    String[] paras = para.split(",");
    EFlowSenderGetter x = new EFlowSenderGetter();
    SenderData senderData = x.load(xml, paras, paraType);
    ObjectMapper objectMapper = new ObjectMapper();
    if (senderData != null) {
      PrintWriter out = response.getWriter();
      out.print(objectMapper.writeValueAsString(senderData));
      out.flush();
      out.close();
    }
  }
  
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException
		  {
	  this.doPost(request, response);
		  }
}