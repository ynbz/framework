package com.suredy.dbmanage.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.dbmanage.entity.DbManage;
import com.suredy.dbmanage.service.BackupsDBSrv;
import com.suredy.dbmanage.service.DbManageSrv;

@Controller
@RequestMapping("/config")
public class DbManageCtrl extends BaseCtrl{
	
	private final static Logger log = LoggerFactory.getLogger(DbManageCtrl.class);
	
	@Autowired
	private DbManageSrv dbmSrv;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	private BackupsDBSrv baDBSrv = new BackupsDBSrv();
	
	@PostConstruct
	public void init() {
		dbmSrv.init();
	}
	
	
	@RequestMapping(value = "/dbmanage/backupsDBList")
	public ModelAndView restoreDBList(String page, String size,String startTime,String endTime) {
		
		ModelAndView view = new ModelAndView("/dbmanage/backupsDB-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<DbManage> data = dbmSrv.getRestoreDBAll(pageIndex, pageSize, startTime, endTime);
		int count = dbmSrv.count( startTime, endTime);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		
		return view;
	}
	
	/**
	 * 备份数据库
	 * @return
	 */
	@RequestMapping({"/dbmanage/backupsDB"})
	@ResponseBody
	public Object backupsDB(){
		//String mes = dbmSrv.backupsDB(this.getUser().getFullName());
		String newDate = sdf.format(new Date());
		String backupsPath = dbmSrv.getRootDir()+newDate;
		String backupsSqlFileName = dbmSrv.getDbName()+".sql";
		File newFile = new File(backupsPath);
		if(!newFile.exists()){
			if(!newFile.mkdirs()){
				log.warn("create mkdir failed.");
				return "create mkdir failed.";
			}
		}
		DbManage dbm = new DbManage();
		dbm.setDbName(dbmSrv.getDbName());
		dbm.setBackupsPerson(this.getUser().getFullName());
		dbm.setRelativeBackupsPath(newDate+"/"+backupsSqlFileName);
		dbm.setBackupsTime(new Date());
		dbm=dbmSrv.save(dbm);
		
		String booMes="true";
		if(dbm!=null){
			booMes=baDBSrv.dbBackUp(dbmSrv.getBackupPath(),dbmSrv.getUrlIp(), dbmSrv.getRoot(), dbmSrv.getRootPass(), dbmSrv.getDbName(), backupsPath, backupsSqlFileName);
		}
		if(booMes.equals("false")){
			log.warn("backups failed.");
			dbmSrv.delete(dbm);
			return MessageModel.createErrorMessage("backups failed.", null);
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 还原数据库
	 * @param id
	 * @return
	 */
	@RequestMapping({"/dbmanage/restoreDB"})
	@ResponseBody
	public Object restoreDB(String id){
		//String mes = dbmSrv.restoreDB(id);
		DbManage dbm = null;
		if(!StringUtils.isBlank(id)){
			dbm = dbmSrv.get(id);
		}
		if(dbm==null){
			log.warn("db Not found.");
			return MessageModel.createErrorMessage("db Not found", null);
		}else{
			boolean booMes = baDBSrv.load(dbmSrv.getRestorePath(),dbmSrv.getUrlIp(), dbmSrv.getRoot(), dbmSrv.getRootPass(), dbmSrv.getDbName(),dbmSrv.getRootDir()+dbm.getRelativeBackupsPath());
			if(!booMes){
				log.warn("file Not found, restore failed.");
				return MessageModel.createErrorMessage("file Not found, restore failed.", null);
			}
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	/**
	 * 
	 * @param chechedids
	 * @return
	 */
	@RequestMapping(value = "/dbmanage/deletedata")
	@ResponseBody
	public Object deletedata(String chechedids) {
		if(!StringUtils.isBlank(chechedids)){
			String[] ids =chechedids.split(",");
			for(String id:ids){
				if(!StringUtils.isBlank(id)){
					DbManage dbm = dbmSrv.get(id);
					if(dbm!=null){
						//删除备份文件
						File dbfile = new File(dbmSrv.getRootDir()+dbm.getRelativeBackupsPath());
						if(dbfile.exists()){
							dbfile.delete();
						}
						//删除目录
						if(dbm.getRelativeBackupsPath().indexOf("/")>0){
							File dbdir = new File(dbmSrv.getRootDir()+dbm.getRelativeBackupsPath().split("/")[0]);
							if(dbdir.isDirectory()){
								dbdir.delete();
							}
						}
						//删除数据
						dbmSrv.delete(dbm);
					}
				}
			}
		}
		return MessageModel.createErrorMessage("flg", true);
	}
	
	
	/**
	 * 下载数据库文件<br>
	 * 
	 * 适配两种模式：<br>
	 * 1、通过id下载。（默认）<br>
	 * 2‘通过相对路径下载
	 * 
	 * @param fileId
	 * @param req
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = {"/dbmanage/download"})
	@ResponseBody
	public ResponseEntity<?> downloadOrViewFile(String fileId, HttpServletRequest req) throws UnsupportedEncodingException {
		HttpHeaders header = new HttpHeaders();
		// 使用此方法的URI
		String URI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		String id = StringUtils.isBlank(fileId) ? null : fileId;
		String relativeFilePath = null;

		boolean isDownload = URI.startsWith("/config/dbmanage/download");

		DbManage dbm = null;
		if(!StringUtils.isBlank(id)){
			dbm=dbmSrv.get(id);
		}
		
		if (dbm == null) {
			log.warn("Can not found file info from DB. Id: " + id + ". relativeFilePath: " + relativeFilePath);

			header.setContentType(dbmSrv.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + dbmSrv.EM_CANOT_FOUND_FILE + "</h1>", header, HttpStatus.OK);
		}

		File file = dbmSrv.getExistsFileOf(dbm.getRelativeBackupsPath());

		if (file == null) {
			log.warn("The file is not exists. It is: " + dbm.getRelativeBackupsPath());

			header.setContentType(dbmSrv.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + dbmSrv.EM_CANOT_FOUND_FILE + "</h1>", header, HttpStatus.OK);
		}

		InputStreamResource in = null;
		try {
			in = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			log.error("", e);
		}

		if (in == null) {
			log.warn("Failed to download file: " + dbm.getRelativeBackupsPath());

			header.setContentType(dbmSrv.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + dbmSrv.EM_FAILED_TO_DOWNLOAD_FILE + "</h1>", header, HttpStatus.OK);
		}

		header.setContentType(dbmSrv.getDownloadContentType(req.getServletContext(), ".sql"));
		// 这里没有使用header.setContentDispositionFormData，是因为生成的Content-Disposition和标准方式不一样
		header.set(HttpHeaders.CONTENT_DISPOSITION, dbmSrv.getContentDispositionValue(dbm.getDbName()+".sql", isDownload));//model.getFileName()

		return new ResponseEntity<InputStreamResource>(in, header, HttpStatus.OK);
	}
	
	
	
	

}
