package com.suredy.docOCX.ctrl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 *  创建文件目录
 * @author zyh
 *
 */
@Component
public class DocOCXInit {
	
	private final static Logger log = LoggerFactory.getLogger(DocOCXInit.class);

	private String rootDir; // 文件存储根目录
	
	
	public void init() {
		// 初始化文件存储路径
		this.setRootDir(this.appendDir(this.getDiskRoot(), "DOCOCXFile"));
		this.createMkdir();
	}
	
	/**
	 * 使用文件路径分隔符拼接字符串
	 * 
	 * @param dir
	 * @return
	 */
	public String appendDir(String... dir) {
		if (ArrayUtils.isEmpty(dir)) {
			log.warn("Invalid param dirs. Need a String[].");

			return null;
		}

		String ret = "";

		for (String d : dir) {
			if (StringUtils.isBlank(d)) {
				log.warn("A blank dir is founded. Ignore it.");

				continue;
			}

			if (ret.length() > 0 && !ret.endsWith(File.separator))
				ret += File.separator;

			ret += d;
		}

		if (ret.length() <= 0)
			return null;

		if (!ret.endsWith(File.separator))
			ret += File.separator;
		
		return ret;
	}
	
	/**
	 * 获取磁盘根路径
	 * 
	 * @return
	 */
	public String getDiskRoot() {
		String path = this.getClass().getResource("/").getPath();

		for (File root : File.listRoots()) {
			if (path.indexOf(root.getAbsolutePath().replace(File.separator, "")) != -1)
				return root.getAbsolutePath();
		}

		return null;
	}
	

	/**
	 * 获取文件存储的根目录
	 * 
	 * @return
	 */
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
	/**
	 * 创建目录
	 */
	private void createMkdir(){
		File m = new File(this.rootDir);
		if(!m.exists())
			m.mkdirs();
	}

}
