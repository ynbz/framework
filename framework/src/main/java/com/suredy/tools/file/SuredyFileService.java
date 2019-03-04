package com.suredy.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.suredy.core.mvc.model.MessageModel;
import com.suredy.tools.file.model.FileModel;

/**
 * 文件服务<br>
 * 
 * 包含：<br>
 * 1、上传<br>
 * 2、下载<br>
 * 3、预览（需要浏览器支持）<br>
 * 
 * @author VIVID.G
 * @since 2016-11-3
 * @version v0.1
 */
@Controller
@RequestMapping("/file")
public class SuredyFileService extends BaseFileService {
	private final static Logger log = LoggerFactory.getLogger(SuredyFileService.class);

	protected final String EM_SERVICE_DISABLED = "文件服务不可用！";
	protected final String EM_NO_UPLOAD_FILE = "未上传任何文件！";
	protected final String EM_INVALID_FILE_INFO = "文件信息无效！";
	protected final String EM_FAILED_TO_DELETE_FILE = "文件删除失败！";
	protected final String EM_CANOT_FOUND_FILE = "无法找到相应文件！";
	protected final String EM_FAILED_TO_DOWNLOAD_FILE = "文件下载失败！";

	protected final String EM_CANOT_STORE_FILE = "无法将文件保存到服务器！";

	@Value("${file.server.enable}")
	private Boolean enable; // 是否启用文件上传服务

	@PostConstruct
	public void init() {
		super.init();

		// 禁用文件服务
		if (!Boolean.TRUE.equals(this.enable)) {
			this.setEnable(false);
			log.warn("Suredy file service is disabled.");
		}
	}

	/**
	 * 文件服务是否启用
	 * 
	 * @return
	 */
	public boolean enable() {
		return this.enable.booleanValue();
	}

	/**
	 * 这里返回值必须是String<br>
	 * 
	 * 在IE8、9下，使用iframe异步提交文件<br>
	 * 此时的Accept为text/html。SpringMvc会根据此值使用text/plain作为响应的Content-type<br>
	 * 
	 * 如果响应的Content-type是application/json的话，IE8、9会作为数据下载<br>
	 * 只有当Content-type为text-plain的时候，才能使用iframe正常接收<br>
	 * 
	 * @see org.springframework.web.bind.annotation.RequestMapping#produces
	 *      使用起来太复杂，不同浏览器的Accept也有差异。放弃
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest request, String extendData) {
		if (!this.enable())
			return this.jsonErrMessage(this.EM_SERVICE_DISABLED, null);

		// 解析文件
		List<MultipartFile> files = this.parseMultipartFile(request);

		if (files == null || files.isEmpty()) {
			log.warn("No file uploaded.");
			return this.jsonErrMessage(this.EM_NO_UPLOAD_FILE, null);
		}

		Map<String, Object> ret = new LinkedHashMap<String, Object>();

		for (MultipartFile mf : files) {
			String field = mf.getName();

			FileModel data = this.storeFile(mf, extendData);
			
			if (data == null) { // 记录一个占位符
				ret.put(field, false);
			} else { // 记录数据库信息
				//判断是否有多个相同内容文件重复上传
				FileModel search = new FileModel();
				search.setShaCode(data.getShaCode());
				int num = this.fileModelSrv.getCountByEntity(search);
				if (num > 1) {
					data.setRepeated(true);
				} else {
					data.setRepeated(false);
				}
				ret.put(field, data);
			}
		}
		return this.jsonSuccessMessage(null, ret);
	};

	/**
	 * 移除文件
	 * 
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ResponseBody
	public Object fileDelete(String fileId) {
		if (!this.enable())
			return MessageModel.createErrorMessage(this.EM_SERVICE_DISABLED, null);

		if (StringUtils.isBlank(fileId)) {
			log.warn("Invalid file id. It is blank.");
			return MessageModel.createErrorMessage("删除失败，" + this.EM_INVALID_FILE_INFO, null);
		}

		if (!this.removeFile(fileId)) {
			log.warn("Failed to delete file: " + fileId);
			return MessageModel.createErrorMessage(this.EM_FAILED_TO_DELETE_FILE, null);
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 下载（在线浏览）文件<br>
	 * 
	 * 适配两种模式：<br>
	 * 1、通过id下载（or在线浏览）。（默认）<br>
	 * 2‘通过相对路径下载（or在线浏览）
	 * 
	 * @param fileId
	 * @param view 是否为在线浏览
	 * @param req
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = {"/download/**", "/view/**"})
	@ResponseBody
	public ResponseEntity<?> downloadOrViewFile(String fileId, HttpServletRequest req) throws UnsupportedEncodingException {
		HttpHeaders header = new HttpHeaders();

		if (!this.enable()) {
			header.setContentType(this.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + this.EM_SERVICE_DISABLED + "</h1>", header, HttpStatus.OK);

		}

		// 使用此方法的URI
		String URI = (String) req.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		String id = StringUtils.isBlank(fileId) ? null : fileId;
		String relativeFilePath = null;

		boolean isDownload = URI.startsWith("/file/download");

		if (StringUtils.isBlank(id)) {
			// 需要解析文件相对路径
			relativeFilePath = URI.replaceFirst("/file/(download|view)/", "").replace('/', File.separatorChar);
		}

		FileModel search = new FileModel();
		search.setId(id);
		search.setRelativeFilePath(relativeFilePath);

		FileModel model = this.fileModelSrv.readSingleByEntity(search);

		if (model == null) {
			log.warn("Can not found file info from DB. Id: " + id + ". relativeFilePath: " + relativeFilePath);

			header.setContentType(this.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + this.EM_CANOT_FOUND_FILE + "</h1>", header, HttpStatus.OK);
		}

		File file = this.getExistsFileOf(model.getRelativeFilePath());

		if (file == null) {
			log.warn("The file is not exists. It is: " + model.getRelativeFilePath());

			header.setContentType(this.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + this.EM_CANOT_FOUND_FILE + "</h1>", header, HttpStatus.OK);
		}

		InputStreamResource in = null;
		try {
			in = new InputStreamResource(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			log.error("", e);
		}

		if (in == null) {
			log.warn("Failed to download file: " + model.getRelativeFilePath());

			header.setContentType(this.getHtmlContentType());

			return new ResponseEntity<String>("<h1>" + this.EM_FAILED_TO_DOWNLOAD_FILE + "</h1>", header, HttpStatus.OK);
		}

		header.setContentType(this.getDownloadContentType(req.getServletContext(), model.getSuffix()));
		header.setContentLength(model.getSize());
		// 这里没有使用header.setContentDispositionFormData，是因为生成的Content-Disposition和标准方式不一样
		header.set(HttpHeaders.CONTENT_DISPOSITION, this.getContentDispositionValue(model.getFileName(), isDownload));

		return new ResponseEntity<InputStreamResource>(in, header, HttpStatus.OK);
	}

	/**
	 * 获取文件列表信息
	 * 
	 * @param fileIds 文件数据的主键ID，使用英文逗号分割
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/list-files", method = RequestMethod.POST)
	@ResponseBody
	public Object fileList(String fileIds, FileModel search) {
		DetachedCriteria dc = this.fileModelSrv.getDc(search);

		if (!StringUtils.isBlank(fileIds)) {
			String[] ids = fileIds.split(",");
			dc.add(Restrictions.in("id", ids));
		}

		@SuppressWarnings("unchecked")
		List<FileModel> files = (List<FileModel>) this.fileModelSrv.readByCriteria(dc);

		return MessageModel.createSuccessMessage(null, files == null || files.isEmpty() ? null : files);
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

}
