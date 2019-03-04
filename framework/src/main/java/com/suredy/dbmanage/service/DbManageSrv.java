package com.suredy.dbmanage.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.suredy.core.service.BaseSrvWithEntity;
import com.suredy.dbmanage.entity.DbManage;

@Service("DbManageSrv")
public class DbManageSrv extends BaseSrvWithEntity<DbManage>  {

	

	@Value("${db.username}")
	private String root; // 数据库连接名

	@Value("${jdbc.password}")
	private String rootPass; // 数据库连接路径

	@Value("${db.ip}")
	private String urlIp;
	
	@Value("${db.name}")
	private String dbName;
	
	@Value("${db.backuppath}")
	private String backupPath;
	
	@Value("${db.restorepath}")
	private String restorePath;
	
	private final static Logger log = LoggerFactory.getLogger(DbManageSrv.class);
	
	private String rootDir; // 文件存储根目录
	
	public final BitSet WWW_FORM_URL = new BitSet(256); // 定义URL中不需要编码的字符
	
	public final String EM_CANOT_FOUND_FILE = "无法找到相应备份文件！";
	public final String EM_FAILED_TO_DOWNLOAD_FILE = "备份文件下载失败！";
	
	public void init() {
		// 初始化文件存储路径
		this.setRootDir(this.appendDir(this.getDiskRoot(), ".db-backups"));
		
		
		// 初始化URL Encoding中的元字符
		for (int i = 'A'; i <= 'Z'; i++) {
			this.WWW_FORM_URL.set(i);
			this.WWW_FORM_URL.set(i + 32);
		}
		// numeric characters
		for (int i = '0'; i <= '9'; i++) {
			this.WWW_FORM_URL.set(i);
		}
		// special chars
		this.WWW_FORM_URL.set('-');
		this.WWW_FORM_URL.set('_');
		this.WWW_FORM_URL.set('.');
		this.WWW_FORM_URL.set('*');
	}
	
	/**
	 * 得到备份列表
	 * @param page
	 * @param size
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<DbManage> getRestoreDBAll(int page, int size,String startTime,String endTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
		DbManage search = new DbManage();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("backupsTime"));
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("backupsTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("backupsTime",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// TODO:
		List<DbManage> pos =(List<DbManage>) this.readPageByCriteria(dc, page, size);
		
		return pos == null?null:pos;
	}
	
	public int count(String startTime,String endTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");	
		DbManage search = new DbManage();
		DetachedCriteria dc = this.getDc(search);
		dc.addOrder(Order.desc("backupsTime"));
		try {
			if(!StringUtils.isBlank(startTime)){
				dc.add(Restrictions.ge("backupsTime",formatter.parse(startTime)));
			}
			if(!StringUtils.isBlank(endTime)){
				dc.add(Restrictions.le("backupsTime",formatter.parse(endTime)));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// TODO:
		List<DbManage> pos =(List<DbManage>) this.readByCriteria(dc);
		return pos == null?null:pos.size();
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
	 * 通过相对路径获取文件对象
	 * 
	 * @param relativeFilePath
	 * @return 如果文件存在，则返回文件对象。其它情况返回null。
	 */
	public File getExistsFileOf(String relativeFilePath) {
		String absoluteFilePath = this.absoluteFilePath(relativeFilePath);

		if (StringUtils.isBlank(absoluteFilePath)) {
			log.warn("Can not create a absolute file path by: " + relativeFilePath);

			return null;
		}

		File f = new File(absoluteFilePath);

		if (f.isFile())
			return f;

		return null;
	}
	
	/**
	 * 构造文件的绝对路径
	 * 
	 * @param relativeFilePath
	 * @return
	 */
	public String absoluteFilePath(String relativeFilePath) {
		if (StringUtils.isBlank(relativeFilePath)) {
			log.warn("Invalid relative path. It is blank.");

			return null;
		}

		return this.getRootDir() + relativeFilePath;
	}
	
	/**
	 * 获取html的content type
	 * 
	 * @return
	 */
	public MediaType getHtmlContentType() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("charset", "utf-8");

		return new MediaType(MediaType.TEXT_HTML, parameters);
	}
	
	/**
	 * 获取content type
	 * 
	 * @param context
	 * @return
	 */
	public MediaType getDownloadContentType(ServletContext context, String suffix) {
		MediaType ret = MediaType.APPLICATION_OCTET_STREAM;

		if (context == null || StringUtils.isBlank(suffix))
			return ret;

		String mimeType = context.getMimeType(suffix);

		if (StringUtils.isBlank(mimeType))
			return ret;

		try {
			ret = MediaType.parseMediaType(mimeType);
		} catch (Exception e) {
			log.error("", e);
		}

		return ret;
	}
	
	/**
	 * 获取下载文件时的Content-Disposition值
	 * 
	 * @param download
	 * @return
	 */
	public String getContentDispositionValue(String fileName, boolean download) {
		String value = download ? "attachment;" : "inline;";

		if (StringUtils.isBlank(fileName))
			return value;

		String encodeFileName = new String(URLCodec.encodeUrl(this.WWW_FORM_URL, fileName.getBytes()));

		value += "filename=" + encodeFileName; // IE可以识别的模式

		value += ";filename*=utf-8''" + encodeFileName; // 现代浏览器可以识别的模式

		return value;
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
	
	public String getDbName() {
		return dbName;
	}
	
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public String getBackupPath() {
		return backupPath;
	}

	public void setBackupPath(String backupPath) {
		this.backupPath = backupPath;
	}

	public String getRoot() {
		return root;
	}
	
	public void setRoot(String root) {
		this.root = root;
	}

	public String getRootPass() {
		return rootPass;
	}
	
	public void setRootPass(String rootPass) {
		this.rootPass = rootPass;
	}
	
	public String getUrlIp() {
		return urlIp;
	}
	
	public void setUrlIp(String urlIp) {
		this.urlIp = urlIp;
	}

	public String getRestorePath() {
		return restorePath;
	}
	
	public void setRestorePath(String restorePath) {
		this.restorePath = restorePath;
	}

}
