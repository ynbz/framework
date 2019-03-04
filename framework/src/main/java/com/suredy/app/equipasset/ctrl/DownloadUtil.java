package com.suredy.app.equipasset.ctrl;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * j下载公共类
 * @author sdkj
 *
 */

public  class  DownloadUtil {
	
	public DownloadUtil(){}

	/**
	 * 下载设备信息
	 * @param file
	 *            文件对象
	 * @param objData
	 *            导出内容数组
	 * @param sheetName
	 *            导出工作表的名称
	 * @param columns
	 *            导出Excel的表头数组
	 * @return
	 */
	public int exportToExcel_3(String url, String filename,List<List<String>> listData,HttpServletResponse response, HttpServletRequest rq) {
		int flag = 0;
		String[] columns = new String[]{"资产ID","购买日期","设备类型","设备型号","合同号","项目名称","序列号","RFID","费用来源","财务资产编号"
										,"供应商","生产厂商","新旧设备","领用日期","使用地点","使用范围","责任部门","责任人","责任人电话","使用人"
										,"使用人电话","备注","配置信息"};
		try {
			
			File file = new File(url, filename);

			WritableWorkbook wwb = Workbook.createWorkbook(file);
			WritableSheet ws = wwb.createSheet("设备信息", 0);
			// 创建单元格样式
			WritableCellFormat wcf = new WritableCellFormat();
			wcf.setAlignment(Alignment.CENTRE);

			// 循环写入表头
			for (int i = 0; i < columns.length; i++) {
				ws.addCell(new Label(i, 0, columns[i], wcf));
			}

			// 判断表中是否有数据
			if (listData != null && listData.size() > 0) {

				// 循环写入表中数据
				for (int i = 0; i < listData.size(); i++) {
					List<String> date = listData.get(i);
					for (int j = 0; j < date.size(); j++) {
						ws.addCell(new Label(j, i + 1, String.valueOf(date.get(j))));
					}
				}
			}
			// 写入Exel工作表
			wwb.write();

			// 关闭Excel工作薄对象
				wwb.close();

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			flag = 1;
		}
		downloadFile(url, filename, response,rq,"msexcel");
		return flag;
	}
	
	
	/**
	 * 
	 * @param url 文件路径
	 * @param filename 文件名
	 * @param response
	 */
	public static void downloadFile( String url,String filename,HttpServletResponse response,HttpServletRequest rq,String contentType){
		// 声明一个file对象
		File f = null;
		try {
			// 根据刚刚的文件地址、创建一个file对象
			f = new File(url + "/"+filename);

			// 如果文件不存在
			if (!f.exists()) {
				response.sendError(404, "File not found!");
			}

			// 创建一个缓冲输入流对象
			BufferedInputStream br = new BufferedInputStream(
					new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			setHead(response,rq, f.getName(), contentType);

			// 创建输出流对象
			OutputStream outStream = response.getOutputStream();

			// 开始输出
			while ((len = br.read(buf)) > 0) {
				outStream.write(buf, 0, len);
			}

			// 关闭流对象

			outStream.flush();
			outStream.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
	    String type = fileNameMap.getContentTypeFor(f.getName());
        if(type!=null&&type.substring(0, 5).equals("image")){
        }else{
        	if (f.exists()) {// 下载完毕删除文件
    			f.delete();
    		}
        }
		
	}
	
	
	/**
	 * 设置response的HEAD attachment改为inline;为在线打开
	 * 
	 * @param response
	 * @param fileName
	 * @param contentType
	 * @author
	 */
	public static void setHead(HttpServletResponse response,HttpServletRequest rq,String fileName,String contentType) {
		StringBuffer sb = null;
		try {
			sb = new StringBuffer();
			sb.append("attachment;filename=\"");
			sb.append(fileFullName(fileName,rq));
			sb.append("\";");
			response.setHeader("Content-disposition", sb.toString());
			response.setContentType("application/" + contentType);
			
		} catch (Exception e) {
		} finally {
			if (sb != null) {
				sb.delete(0, sb.length());
				sb = null;
			}
		}
	}
	
	/**
	 * 自定义背景色方法
	 * @param strColor 颜色的值 #123556 #87CEEB
	 * @return
	 */
	public static Colour getNearestColour(String strColor) {    
		Color cl = Color.decode(strColor);    
        Colour color = null;    
        Colour[] colors = Colour.getAllColours();    
        if ((colors != null) && (colors.length > 0)) {    
        	Colour crtColor = null;    
	        int[] rgb = null;    
	        int diff = 0;    
	        int minDiff = 999;    
	        for (int i = 0; i < colors.length; i++) {    
	           crtColor = colors[i];    
	           rgb = new int[3];    
	           rgb[0] = crtColor.getDefaultRGB().getRed();    
	           rgb[1] = crtColor.getDefaultRGB().getGreen();    
	           rgb[2] = crtColor.getDefaultRGB().getBlue();    
	  
	           diff = Math.abs(rgb[0] - cl.getRed()) + Math.abs(rgb[1] - cl.getGreen())+ Math.abs(rgb[2] - cl.getBlue());    
	           if (diff < minDiff) {    
	           minDiff = diff;    
	           color = crtColor;    
	           }    
	        }    
      }    
      if (color == null)    
         color = Colour.BLACK;    
      return color;    
	} 
	
	private static String fileFullName(String showName,HttpServletRequest rq) {
		// new a temp fileName
		if (StringUtils.isBlank(showName))
			showName = UUID.randomUUID().toString().replace("-", "");
		String userAgent=rq.getHeader("User-Agent");
		try {
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				// 各种IE的情况
				showName = URLEncoder.encode(showName, "UTF-8");
			} else {
				showName = new String(showName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
			}
		} catch (UnsupportedEncodingException e) {
		}

		return showName;
	}
}
