package com.suredy.flow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/EFlowR"})
public class EFlowR extends HttpServlet
{
  private static final long serialVersionUID = 1L;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    Map<String,String[]> map = request.getParameterMap();
    String url = "http://127.0.0.1:8180/eAdmWeb/servlet/EFlowClient";
    String paras = "";

    for (String key : map.keySet()) {
      String[] value = (String[])map.get(key);
      if ((value != null) && (value.length > 0)) {
        if (paras.length() == 0)
          paras = paras + key + "=" + value[0];
        else
          paras = paras + "&" + key + "=" + value[0];
      }
    }
    String r = sendPost(url, paras);
    PrintWriter out = response.getWriter();
    out.print(r);
    out.flush();
    out.close();
  }

  public static String sendPost(String url, String param) {
    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(url);
      URLConnection conn = realUrl.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      out = new PrintWriter(conn.getOutputStream());

      out.print(param);

      out.flush();

      in = new BufferedReader(
        new InputStreamReader(conn.getInputStream(), "GBK"));
      String line;
      while ((line = in.readLine()) != null)
      {
        result = result + line;
      }
    } catch (Exception e) {
      System.out.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
      try
      {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (out != null) {
          out.close();
        }
        if (in != null)
          in.close();
      }
      catch (IOException ex)
      {
        ex.printStackTrace();
      }
    }
    return result;
  }
}