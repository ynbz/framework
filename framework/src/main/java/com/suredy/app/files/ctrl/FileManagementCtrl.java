/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 * @since 2017年4月25日
 * @version 1.0
 */
package com.suredy.app.files.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.files.entity.FolderEntity;
import com.suredy.app.files.entity.FolderLogEntity;
import com.suredy.app.files.entity.UploadFileEntity;
import com.suredy.app.files.service.FolderLogSrv;
import com.suredy.app.files.service.FolderSrv;
import com.suredy.app.files.service.UploadFileSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.core.mvc.model.TreeMenu;
import com.suredy.security.entity.User2RoleEntity;
import com.suredy.security.model.User;
import com.suredy.security.service.User2RoleSrv;
import com.suredy.security.service.UserSrv;
import com.suredy.tools.file.SuredyFileService;
import com.suredy.tools.file.model.FileModel;
import com.suredy.tools.file.srv.FileModelSrv;

/**
 * @author ZhangMaoren
 *
 */

@Controller
@RequestMapping({"/files"})
public class FileManagementCtrl extends BaseCtrl {

	@Autowired
	private UploadFileSrv ufSrv;
	
	@Autowired
	private User2RoleSrv u2rSrv;

	@Autowired
	private FolderSrv folderSrv;

	@Autowired
	private FileModelSrv modelSrv;

	@Autowired
	private SuredyFileService fileSrv;
	
	@Autowired
	private FolderLogSrv folderLogSrv;
	
	@Autowired
	private UserSrv userSrv;


	@RequestMapping(value = "/manager")
	public ModelAndView manager(String page, String size, String typeId,HttpServletRequest request) {

		ModelAndView view = new ModelAndView("/app/files/file-list");

		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		int count = 0;
		List<UploadFileEntity> data = new ArrayList<UploadFileEntity>();
		List<FolderEntity> types = this.folderSrv.getFileTypes(typeId);
		if(types!=null&&types.size()>0){
			data = this.ufSrv.getAll(pageIndex, pageSize, types);
			count = this.ufSrv.Count(types);
		}

		if (!data.isEmpty()) {
			view.addObject("data", data);
		}
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		List<String> allowed = this.userSrv.getPermissions(user.getUniqueCode());
		view.addObject("permissions", allowed);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}

	@RequestMapping({"/type-tree"})
	@ResponseBody
	public Object typeTree() {
		// 此处不带文件信息，仅仅显示文件分类
		List<Tree> typeTree = this.ufSrv.getFolderTree(null);
		return typeTree == null ? null : typeTree;
	}

	@RequestMapping({"/folder-tree/user"})
	@ResponseBody
	public Object UserNavigationTree(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		// not login
		if (user == null) {
			return MessageModel.createErrorMessage("用户未登录或会话超时", null);
		}
		List<String> units = new ArrayList<String>();
		units.add(user.getUnitId());
		List<String> roles = new ArrayList<String>();
		
		List<User2RoleEntity> urRelation = this.u2rSrv.getByUser(user.getId());
		if (urRelation != null && !urRelation.isEmpty()) {
			for (User2RoleEntity u2r : urRelation) {
				roles.add(u2r.getRole().getId());
			}
		}

		List<TreeMenu> tree = this.ufSrv.getFolderTreeMenu(null, roles, units);
		return tree == null ? null : tree;
	}

	@RequestMapping({"/type-info"})
	public ModelAndView editType(String id, String parentId) {
		ModelAndView view = new ModelAndView("/app/files/type-info");
		FolderEntity data;
		if (StringUtils.isEmpty(id)) {
			data = new FolderEntity();
			if (StringUtils.isNotEmpty(parentId)) {
				FolderEntity parent = this.folderSrv.get(parentId);
				data.setParent(parent);
				data.setIsPublic(parent.getIsPublic());
			}
		} else {
			data = this.folderSrv.get(id);
		}
		view.addObject("data", data);
		return view;
	}

	@Transactional
	@RequestMapping({"/type-save"})
	@ResponseBody
	public Object saveType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String name = request.getParameter("name");
		String sort = request.getParameter("sort");
		String isPublic = request.getParameter("isPublic");
		if (StringUtils.isEmpty(name)) {
			return MessageModel.createErrorMessage("参数不足, 分类名称必须填写", null);
		}
		if (StringUtils.isEmpty(id)) {
			FolderEntity type = new FolderEntity();
			type.setName(name);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			
			type.setCreationTime(new Date());
			if (!StringUtils.isEmpty(parent)) {
				FolderEntity p = this.folderSrv.get(parent);
				type.setParent(p);
				type.setIsPublic(p.getIsPublic());
			} else {
				type.setIsPublic(Integer.valueOf(isPublic));
			}
			this.folderSrv.save(type);
		} else {
			FolderEntity type = this.folderSrv.get(id);
			if (type == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的分类信息", null);
			}

			type.setName(name);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			
			if (!StringUtils.isEmpty(parent)) {
				if (!parent.equals(id)) {
					FolderEntity p = this.folderSrv.get(parent);
					type.setParent(p);
					type.setIsPublic(p.getIsPublic());
				}
			} else {
				type.setParent(null);
				type.setIsPublic(Integer.valueOf(isPublic));
			}
			updateSubType(type);
			this.folderSrv.update(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	private void updateSubType(FolderEntity type) {
		if (type == null) {
			return;
		}
		Integer p = type.getIsPublic();
		List<FolderEntity> children = type.getChildren();
		if (children != null && !children.isEmpty()) {
			for(FolderEntity child : children) {
				child.setIsPublic(p);
				this.folderSrv.update(child);
				updateSubType(child);
			}
		}
	}

	@RequestMapping({"/type-delete"})
	@ResponseBody
	public Object deleteType(String typeId) {
		List<FolderEntity> types = this.folderSrv.getFileTypes(typeId);
		if (types != null && !types.isEmpty()) {
			int size = this.ufSrv.Count(types);
			if (size > 0) {
				return MessageModel.createErrorMessage("当前分类或者子分类下有关联的文件，请先删除文件再次尝试删除分类！", null);
			}
			FolderEntity type = this.folderSrv.get(typeId);
			if (type.getParent() != null) {
				type.getParent().getChildren().remove(type);
			}
			this.folderSrv.delete(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}



	@Transactional
	@RequestMapping(value = "/document/list")
	public ModelAndView documentList(HttpServletRequest request) {
		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String typeId = request.getParameter("typeId");
		ModelAndView view = new ModelAndView("/app/files/document-list");
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		// not login
		if (user == null) {
			return view;
		}
		List<String> units = new ArrayList<String>();
		units.add(user.getUnitId());
		List<String> roles = new ArrayList<String>();
		
		List<User2RoleEntity> urRelation = this.u2rSrv.getByUser(user.getId());
		if (urRelation != null && !urRelation.isEmpty()) {
			for (User2RoleEntity u2r : urRelation) {
				roles.add(u2r.getRole().getId());
			}
		}
		// 已授权用户访问的文件分类相关安全资源点
		List<String> allowedFolders = this.ufSrv.getAllowedFolders(null, roles, units);
		
		
		int pageIndex = 1, pageSize = 10;// Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		int count = 0;
		List<UploadFileEntity> data = new ArrayList<UploadFileEntity>();
		List<FolderEntity> allowedTypes = new ArrayList<FolderEntity>();
		
		if (StringUtils.isEmpty(typeId)) { //根路径为空,列出全部可访问的分类
			for (String folderId : allowedFolders) {
				allowedTypes.add(this.folderSrv.get(folderId));
			}
		} else { //指定根路径后列出全部可访问分类
			List<FolderEntity> allTypes = this.folderSrv.getFileTypes(typeId);
			if (allTypes != null && !allTypes.isEmpty()) {
				for (FolderEntity temp : allTypes) {
					if (allowedFolders.contains(temp.getId())) {
						allowedTypes.add(temp);
					}
				}
			}
		}
		
		
		

		
		data = this.ufSrv.getAll(pageIndex, pageSize, allowedTypes);
		count = this.ufSrv.Count(allowedTypes);

		if (data != null && !data.isEmpty()) {
			for (UploadFileEntity temp : data) {
				String fileIds = temp.getFileUrlId();
				DetachedCriteria dc = this.modelSrv.getDc();
				if (!StringUtils.isBlank(fileIds)) {
					String[] ids = fileIds.split(",");
					dc.add(Restrictions.in("id", ids));
				}
				@SuppressWarnings("unchecked")
				List<FileModel> files = (List<FileModel>) this.modelSrv.readByCriteria(dc);
				temp.setFiles(files);
				dc = null;
			}

			view.addObject("data", data);
		}
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("typeId", typeId);
		return view;

	}

	@RequestMapping(value = "/file-upload")
	public ModelAndView uploadView(String fileId, String typeId) {
		ModelAndView view = new ModelAndView("/app/files/file-upload");
		UploadFileEntity file = null;
		

		if (!StringUtils.isBlank(fileId)) {
			file = ufSrv.get(fileId);
		} else {
			file = new UploadFileEntity();
			FolderEntity type = null;
			if (!StringUtils.isBlank(typeId)) {
				type = folderSrv.get(typeId);
			}
			file.setFolder(type);
		}

		view.addObject("file", file);
		return view;
	}

	@RequestMapping({"/file-save"})
	@ResponseBody
	public Object saveFile(HttpServletRequest request) {
		String title = request.getParameter("title");
		String keyWord = request.getParameter("keyWord");
		String typeId = request.getParameter("typeId");
		String fileIds = request.getParameter("fileIds");
		String id = request.getParameter("id");
		UploadFileEntity file = null;

		if (StringUtils.isEmpty(id)) {
			file = new UploadFileEntity();
			file.setTitle(title);
			file.setKeyWord(keyWord);
			if (!StringUtils.isBlank(fileIds)) {
				file.setFileUrlId(fileIds);
			}
			file.setUploadTime(new Date());
			file.setUploader(this.getUser().getFullName());
			file.setFolder(folderSrv.get(typeId));
			this.ufSrv.save(file);
		} else {
			file = ufSrv.get(id);
			file.setTitle(title);
			file.setKeyWord(keyWord);
			if (!StringUtils.isBlank(fileIds)) {
				file.setFileUrlId(fileIds);
			}
			file.setUploadTime(new Date());
			file.setUploader(this.getUser().getFullName());
			file.setFolder(folderSrv.get(typeId));

			this.ufSrv.update(file);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping(value = "/file-view")
	public ModelAndView fileView(String fileId) {
		ModelAndView view = new ModelAndView("/app/files/file-view");
		UploadFileEntity file = null;
		if (!StringUtils.isBlank(fileId)) {
			file = ufSrv.get(fileId);
		}
		view.addObject("file", file);
		return view;
	}

	/**
	 * 删除
	 * 
	 * @param fileid
	 * @return
	 */
	@RequestMapping("/file-delete")
	@ResponseBody
	@Transactional
	public Object deleteFile(String fileid) {
		if (StringUtils.isBlank(fileid)) {
			return MessageModel.createErrorMessage("文件ID必须提供", null);
		}
		UploadFileEntity file = this.ufSrv.get(fileid);
		if (file == null) {
			return MessageModel.createErrorMessage("未找到与ID['" + fileid + "']对应的文件信息", null);
		}
		String[] ids = file.getFileUrlId().split(",");
		for (String fileId : ids) {
			this.fileSrv.removeFile(fileId);
		}
		file = this.ufSrv.delete(file);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	@RequestMapping("/file-verify")
	@ResponseBody
	public Object verifyFiles(String fileids) {
		if (StringUtils.isBlank(fileids)) {
			return MessageModel.createErrorMessage("文件ID必须提供", null);
		}
		ufSrv.changeFileStatus(fileids,1);
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping("/log-delete")
	@ResponseBody
	public Object deleteLog(String folderLogIds) {
		if (StringUtils.isBlank(folderLogIds)) {
			return MessageModel.createErrorMessage("文件ID必须提供", null);
		}
		String[] folderLogIdList= folderLogIds.split(",");
		for(String folderLogId :folderLogIdList){
			FolderLogEntity folderLog = folderLogSrv.get(folderLogId);
			if(folderLog!=null){
				folderLogSrv.delete(folderLog);
			}
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping("/log-list")
	@ResponseBody
	public ModelAndView getLogList(String folderId) {
		ModelAndView view = new ModelAndView("/app/files/folder-log-list");
		List<FolderLogEntity> logList = folderLogSrv.getLogList(folderId);
		view.addObject("folderId", folderId);
		view.addObject("logList", logList);
		return view;
	}
	

	@RequestMapping("/log-save")
	@ResponseBody
	public Object saveLog(FolderLogEntity folderLog) {
		if (folderLog==null) {
			return MessageModel.createErrorMessage("对象为空，不能保存！", null);
		}else{
			folderLog.setAddUser(this.getUser().getName());
			folderLog.setLogDate(new Date());
			folderLogSrv.save(folderLog);
			return MessageModel.createSuccessMessage(null, null);
		}
	}

}
