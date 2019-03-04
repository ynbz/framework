package com.suredy.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.resource.entity.ReportEntity;
import com.suredy.resource.entity.ReportTypeEntity;
import com.suredy.resource.model.Report;
import com.suredy.resource.service.ReportSrv;
import com.suredy.resource.service.ReportTypeSrv;
import com.suredy.security.model.User;
import com.suredy.security.service.UserSrv;
import com.suredy.tools.file.SuredyFileService;
import com.suredy.tools.file.model.FileModel;
import com.suredy.tools.file.srv.FileModelSrv;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseCtrl {
	
	@Autowired 
	private ReportSrv reportSrv;
	
	@Autowired
	private ReportTypeSrv typeSrv;
	
	@Autowired
	private FileModelSrv modelSrv;
	
	@Autowired
	private SuredyFileService fileSrv;
	
	@Autowired
	private UserSrv userSrv;
	
	private final static Logger log = LoggerFactory.getLogger(ReportController.class);

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/framework", "root", "123456");
		if (conn != null) {
			return conn;
		}
		return null;
	}
	
	


	// compile template, run to html.
	@RequestMapping("/run/compile")
	public void runWithSource(HttpServletRequest request, HttpServletResponse response) {
		try {
			String jrxmlSourcePathMain = request.getServletContext().getRealPath("/") + "report/main.jrxml";
			String jrxmlSourcePathSub = request.getServletContext().getRealPath("/") + "report/main_subreport1.jrxml";
			String jrxmlDestSourcePathMain = this.getClass().getClassLoader().getResource("").getPath().substring(1) + "main.jasper";
			String jrxmlDestSourcePathSub = this.getClass().getClassLoader().getResource("").getPath().substring(1) + "main_subreport1.jasper";
			JasperCompileManager.compileReportToFile(jrxmlSourcePathMain, jrxmlDestSourcePathMain);
			JasperCompileManager.compileReportToFile(jrxmlSourcePathSub, jrxmlDestSourcePathSub);

			InputStream input = new FileInputStream(new File(jrxmlDestSourcePathMain));
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/pdf");
			JasperRunManager.runReportToPdfStream(input, out, new HashMap<String, Object>(), getConnection());

			out.flush();
			out.close();
		} catch (JRException | ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getReportFile(String reportId, String fileId){
		if (StringUtils.isEmpty(reportId) || StringUtils.isEmpty(fileId)) {
			return null;
		}
		ReportEntity report = this.reportSrv.get(reportId);
		if (report == null) {
			return null;
		}
		if (!report.getFileId().equals(fileId)) {
			return null;
		}
		FileModel file = this.modelSrv.get(fileId);
		if (file == null) {
			return null;
		}
		String path = this.fileSrv.getExistsFileOf(file.getRelativeFilePath()).getAbsolutePath();
		path = path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
		return path;
	}
	
	@RequestMapping(value = "/list")
	public ModelAndView reportList(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/report/report-list");
		User user = (User) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		// not login
		if (user == null) {
			log.info("Session timeout or User didn't login.");
			return view;
		}
		
		List<String> userSecurityResources = this.userSrv.getPermissions(user.getUniqueCode());
		List<Report> reports;
		String typeId = request.getParameter("typeId");
		if (StringUtils.isEmpty(typeId)) {
			reports = this.reportSrv.getAll(null);
		} else {
			List<ReportTypeEntity> types = this.typeSrv.getReportTypes(typeId);
			reports = this.reportSrv.getAll(types);
		}
		List<Report> data = new ArrayList<Report>();
		for(Report report : reports) {
			if (userSecurityResources.contains(report.getResourceUri())){
				data.add(report);
			}
		}
		if (!data.isEmpty()) {
			view.addObject("data", data);
		}
		return view;
	}
	
	//run report to html
	@RequestMapping(value = "/run/default")
	public ModelAndView runReport(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/report/run-report");
		String reportId = request.getParameter("reportId");
		String fileId = request.getParameter("fileId");
		String path = getReportFile(reportId, fileId);
		File reportFile = new File(path);
		Map<String, Object> params = new HashMap<String, Object>();
		String destFile = request.getServletContext().getRealPath("/report/result.html");
		try {
			Connection conn = getConnection();
			JasperRunManager.runReportToHtmlFile(reportFile.getPath(), destFile, params, conn);
			conn.close();
			view.addObject("result", "report/result.html"); 
		} catch (JRException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		view.addObject("reportId", reportId);
		view.addObject("fileId", fileId);
		return view;
	}


	
	@RequestMapping("/run/pdf")
	public void runToPdf(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		String fileId = request.getParameter("fileId");		
		String path = getReportFile(reportId, fileId);
		File reportFile = new File(path);
		Map<String, Object> params = new HashMap<String, Object>();	
		try {
			Connection conn = getConnection();
			JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), params, conn);
			JRPdfExporter exporter = new JRPdfExporter();
			// 设置输入项
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			conn.close();
			// 设置输出项
			String generateFileName = "结果文件.pdf";
			ServletOutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(generateFileName, "UTF-8"));
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(out);
			exporter.setExporterOutput(exporterOutput);
			exporter.exportReport();
			out.flush();
			out.close();
			exporterOutput.close();
		} catch (JRException | ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}



	@RequestMapping("/run/word")
	public void runToWord(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		String fileId = request.getParameter("fileId");		
		String path = getReportFile(reportId, fileId);
		File reportFile = new File(path);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Connection conn = getConnection();
			// print文件
			JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), params, conn);

			JRDocxExporter exporter = new JRDocxExporter();
			// 设置输入项
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			conn.close();
			// 设置输出项
			String generateFileName = "结果文件.docx";
			ServletOutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/msword");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(generateFileName, "UTF-8"));
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(out);
			exporter.setExporterOutput(exporterOutput);
			exporter.exportReport();
			out.flush();
			out.close();
			exporterOutput.close();

		} catch (JRException | ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/run/excel")
	public void runToExcel(HttpServletRequest request, HttpServletResponse response) {
		String reportId = request.getParameter("reportId");
		String fileId = request.getParameter("fileId");		
		String path = getReportFile(reportId, fileId);
		File reportFile = new File(path);
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			Connection conn = getConnection();
			// print文件
			JasperPrint print = JasperFillManager.fillReport(reportFile.getPath(), params, conn);
			JRXlsxExporter exporter = new JRXlsxExporter();
			// 设置输入项
			ExporterInput exporterInput = new SimpleExporterInput(print);
			exporter.setExporterInput(exporterInput);
			conn.close();
			// 设置输出项
			String generateFileName = "结果文件.xlsx";
			ServletOutputStream out = response.getOutputStream();
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(generateFileName, "UTF-8"));
			OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(out);
			exporter.setExporterOutput(exporterOutput);
			exporter.exportReport();
			out.flush();
			out.close();
			exporterOutput.close();

		} catch (JRException | ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
