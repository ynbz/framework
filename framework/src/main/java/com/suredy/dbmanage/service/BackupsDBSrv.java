package com.suredy.dbmanage.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 *数据库备份还原业务类
 * @author sdkj
 * 
 */
public class BackupsDBSrv {

	/**
	 * 备份mysql数据库
	 * 
	 * @param root urlIpIP地址
	 * @param root mysql登录名
	 * @param rootPass 登录密码
	 * @param dbName 要备份的数据库名称
	 * @param backupsPath 备份的路径
	 * @param backupsSqlFileName 备份文件的名字
	 * @return
	 */
	public String dbBackUp(String cmdPath,String urlIp,String root,String rootPass,String dbName,String backupsPath, String backupsSqlFileName) {
		String pathSql = backupsPath +File.separator+ backupsSqlFileName;
		try {
			File fileSql = new File(pathSql);
			if (!fileSql.exists()) {
				fileSql.createNewFile();
			}
			StringBuffer sbs = new StringBuffer();
			sbs.append("\""+cmdPath+"\"");
			sbs.append(" --hex-blob -h " + urlIp + " ");
			sbs.append("-p3306 ");
			sbs.append("-u");
			sbs.append(root + " ");
			sbs.append("-p" + rootPass + " ");
			sbs.append(dbName);
			sbs.append(" --default-character-set=utf8 ");
			System.out.println("cmd命令为：——>>>" + sbs.toString());
			Runtime runtime = Runtime.getRuntime();
			Process child = runtime.exec(sbs.toString());

			// 读取备份数据并生成临时文件
			InputStream in = child.getInputStream();
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathSql), "utf8");
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf8"));
			String line = reader.readLine();
			while (line != null) {
				writer.write(line + "\n");
				line = reader.readLine();
			}
			writer.flush();
			reader.close();
			writer.close();
			
			System.out.println("数据库已备份到——>>" + pathSql);
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		return "true";
	}

	/**
	 * 还原数据库
	 * 
	 * @return
	 */
	public boolean load(String cmdPath,String urlIp,String root,String rootPass,String dbName,String fPath) {// 还原
		try {
			Runtime rt = Runtime.getRuntime();

			StringBuffer sbs = new StringBuffer();
			sbs.append("\""+cmdPath+"\"");
			sbs.append(" -h " + urlIp + " ");
			sbs.append("-p3306 ");
			sbs.append("-u");
			sbs.append(root + " ");
			sbs.append("-p" + rootPass + " ");
			sbs.append(dbName);
			sbs.append(" --default-character-set=utf8");
			Process child = rt.exec(sbs.toString());
			OutputStream out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fPath.replace("/", "\\")), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			OutputStreamWriter writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			out.close();
			br.close();
			writer.close();

			System.out.println("/* Load OK! */");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
