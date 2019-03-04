/**
 * Copyright (c) 2014-2017, Suredy technology Co., Ltd. All rights reserved.
 */
package com.suredy.resource.ctrl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.mvc.model.Tree;
import com.suredy.resource.entity.ReportEntity;
import com.suredy.resource.entity.ReportTypeEntity;
import com.suredy.resource.entity.ResourceEntity;
import com.suredy.resource.model.Report;
import com.suredy.resource.model.ResourceType;
import com.suredy.resource.service.ReportSrv;
import com.suredy.resource.service.ReportTypeSrv;

/**
 * @author ZhangMaoren 2017年3月1日
 *
 */
@Controller
@RequestMapping(value="/config")
public class ReportCtrl extends BaseCtrl{
	private final static Logger log = LoggerFactory.getLogger(ReportCtrl.class);
	
	@Autowired
	private ReportTypeSrv typeSrv;
	
	@Autowired 
	private ReportSrv reportSrv;
		
	@RequestMapping("report/type/tree")
	@ResponseBody
	public Object ReportTypeTree() {
		//此处不带报表信息，仅仅显示报表分类
		List<Tree> typeTree = this.reportSrv.getReportTypeTree(null, false);
		return typeTree == null ? null : typeTree;

	}
	

	
	@RequestMapping(value = "report/manager")
	public ModelAndView reportManage(String page, String size, String typeId) {
		ModelAndView view = new ModelAndView("/config/resource/report-manager");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isEmpty(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isEmpty(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<ReportTypeEntity> types = this.typeSrv.getReportTypes(typeId);
		Integer count = this.reportSrv.Count(types);
		List<Report> data = this.reportSrv.getAll(pageIndex, pageSize, types);
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping(value = "report/type/edit")
	public ModelAndView eidtType(String id, String parentId) {
		ModelAndView view = new ModelAndView("/config/resource/report-type-form");
		ReportTypeEntity data; 
		if (StringUtils.isEmpty(id)) {
			data = new ReportTypeEntity();
			if (StringUtils.isNotEmpty(parentId)) {
				ReportTypeEntity parent = this.typeSrv.get(parentId);
				data.setParent(parent);
			}
		} else {
			data = this.typeSrv.get(id);
		}
		view.addObject("data", data);
		return view;
	}
	
	@RequestMapping("report/type/save")
	@ResponseBody
	public Object updateType(HttpServletRequest request) {
		String id = request.getParameter("id");
		String parent = request.getParameter("parent");
		String name = request.getParameter("name");
		String sort = request.getParameter("sort");
		if ( StringUtils.isEmpty(name)) {
			log.info("Parameter String[text] is blank.");
			return MessageModel.createErrorMessage("参数不足, 分类名称必须填写", null);
		}
		if (StringUtils.isEmpty(id)) {
			Date date = new Date();
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(name);
			resource.setType(ResourceType.ReportType.getType());
			resource.setUri(UUID.randomUUID().toString());
			ReportTypeEntity type = new ReportTypeEntity();
			
			type.setName(name);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			type.setResource(resource);
			if (!StringUtils.isEmpty(parent)) {
				type.setParent(new ReportTypeEntity());
				type.getParent().setId(parent);
			}
			this.typeSrv.save(type);
		} else {
			ReportTypeEntity type = this.typeSrv.get(id);
			if (type == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的报表分类信息", null);
			}
			
			type.setName(name);
			type.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
	
			if (!StringUtils.isEmpty(parent)) {
				if (! parent.equals(id)) {
					type.setParent(new ReportTypeEntity());
					type.getParent().setId(parent);
				}
			} else {
				type.setParent(null);
			}
			ResourceEntity resource = type.getResource();
			resource.setName(name);

			type.setResource(resource);
			this.typeSrv.update(type);
		}
		return MessageModel.createSuccessMessage(null, null);
	}	
	
	@Transactional
	@RequestMapping("report/type/delete")
	@ResponseBody
	public Object deleteType(String typeId) {
		if (StringUtils.isEmpty(typeId)) {
			return MessageModel.createErrorMessage("报表分类ID为空！", null);
		}

		ReportTypeEntity type = this.typeSrv.get(typeId);

		if (type == null) {
			return MessageModel.createErrorMessage("未找到与ID对应的报表分类信息！", null);
		}
		
		if (type.getAssociationReports().size() > 0) {
			return MessageModel.createErrorMessage("当前报表分类下有关联报表,不允许删除分类！", null);
		}
		
		List<ReportTypeEntity> types = this.typeSrv.getReportTypes(typeId);
		List<Report> reports = this.reportSrv.getAll(types);
		if (reports != null && !reports.isEmpty()) {
			return MessageModel.createErrorMessage("当前分类的子分类有关联报表,不允许删除分类！", null);
		}
		
		if (type.getParent() != null) {
			type.getParent().getChildren().remove(type);
		}

		try {
			this.typeSrv.delete(type);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除失败,报表分类信息与安全资源映射错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
	@RequestMapping(value = "report/file/edit")
	public ModelAndView editReport(String reportId) {
		ModelAndView view = new ModelAndView("/config/resource/report-file-form");
		ReportEntity data; 
		if (StringUtils.isEmpty(reportId)) {
			data = new ReportEntity();
			data.setFileId(UUID.randomUUID().toString());
		} else {
			data = this.reportSrv.get(reportId);
		}
		Report temp = data.toVO();
		view.addObject("data", temp);
		return view;
	}
	

	@RequestMapping("report/file/save")
	@ResponseBody
	public Object updateReport(HttpServletRequest request) {
		String id = request.getParameter("id");
		String fileId = request.getParameter("fileId");
		String typeId = request.getParameter("reportTypeId");
		String name = request.getParameter("name");
		String sort = request.getParameter("sort");
		if ( StringUtils.isEmpty(fileId) || fileId.indexOf("-") != -1) {
			log.info("Parameter String[fileId] is blank.");
			return MessageModel.createErrorMessage("参数不足, 未上传报表文件", null);
		}
		if ( StringUtils.isEmpty(typeId)) {
			log.info("Parameter String[typeId] is blank.");
			return MessageModel.createErrorMessage("参数不足, 报表分类必须选择一类", null);
		}
		if ( StringUtils.isEmpty(name)) {
			log.info("Parameter String[name] is blank.");
			return MessageModel.createErrorMessage("参数不足, 报表名称必须填写", null);
		}
		
		if (StringUtils.isEmpty(id)) {
			Date date = new Date();
			ResourceEntity resource = new ResourceEntity();
			resource.setCreateTime(date);
			resource.setLastModifiedTime(date);
			resource.setName(name);
			resource.setType(ResourceType.ReportSource.getType());
			resource.setUri(UUID.randomUUID().toString());
			ReportEntity report = new ReportEntity();
			
			report.setName(name);
			report.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			report.setFileId(fileId);
			ReportTypeEntity type = new ReportTypeEntity();
			type.setId(typeId);
			report.setType(type);
			report.setResource(resource);

			this.typeSrv.save(report);
		} else {
			ReportEntity report = this.reportSrv.get(id);
			if (report == null) {
				return MessageModel.createErrorMessage("参数错误, 未找到与ID对应的报表信息", null);
			}
			
			report.setName(name);
			report.setSort(StringUtils.isEmpty(sort) ? 0 : Integer.parseInt(sort));
			report.setFileId(fileId);
			ReportTypeEntity type = new ReportTypeEntity();
			type.setId(typeId);
			report.setType(type);	

			ResourceEntity resource = report.getResource();
			resource.setName(name);

			report.setResource(resource);
			this.typeSrv.update(report);
		}
		return MessageModel.createSuccessMessage(null, null);
	}	
	
	@RequestMapping("report/file/delete")
	@ResponseBody
	public Object deleteReport(String reportId) {
		if (StringUtils.isEmpty(reportId)) {
			return MessageModel.createErrorMessage("报表ID为空！", null);
		}

		ReportEntity report = this.reportSrv.get(reportId);

		if (report == null) {
			return MessageModel.createErrorMessage("未找到与ID对应的报表信息！", null);
		}
		

		try {
			this.reportSrv.delete(report);
		} catch (Exception e) {
			return MessageModel.createErrorMessage("删除失败,报表信息与安全资源映射错误！", null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}
	
}
