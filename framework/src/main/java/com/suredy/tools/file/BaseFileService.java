package com.suredy.tools.file;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.tools.file.model.FileModel;
import com.suredy.tools.file.srv.FileModelSrv;

/**
 * 文件操作基类。 基于SpringMvc的CommonsMultipartResolver，以及Spring的注解功能
 * 
 * @author VIVID.G
 * @since 2016-11-2
 * @version v0.1
 */
public abstract class BaseFileService {
	private static final Logger log = LoggerFactory.getLogger(BaseFileService.class);

	protected final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd"); // 日期格式化
	protected final ObjectMapper JSON_STRING = new ObjectMapper();

	@Autowired
	protected SuredyDiskFileTool fileTool; // 文件操作工具

	@Autowired
	protected CommonsMultipartResolver resolver; // 基于SpringMVC的解析器

	@Autowired
	protected FileModelSrv fileModelSrv;

	@Value("${file.server.root.dir}")
	private String rootDir; // 文件存储根目录

	protected final BitSet WWW_FORM_URL = new BitSet(256); // 定义URL中不需要编码的字符

	@PostConstruct
	protected void init() {
		// 初始化文件存储路径
		if (StringUtils.isBlank(this.rootDir())) {
			this.setRootDir(this.fileTool.appendDir(this.fileTool.getDiskRoot(), ".file-uploaded"));
			log.warn("Not config the fileupload root dir. Now use <" + this.rootDir() + "> instead.");
		}

		// 初始化URL Encoding中的元字符
		for (int i = 'A'; i <= 'Z'; i++) {
			this.WWW_FORM_URL.set(i);
			this.WWW_FORM_URL.set(i + 32);
		}
		for (int i = '0'; i <= '9'; i++) {
			this.WWW_FORM_URL.set(i);
		}
		this.WWW_FORM_URL.set('-');
		this.WWW_FORM_URL.set('_');
		this.WWW_FORM_URL.set('.');
		this.WWW_FORM_URL.set('*');
	}

	/**
	 * 获取文件存储的根目录
	 * 
	 * @return
	 */
	public String rootDir() {
		return this.rootDir;
	}

	/**
	 * 获取本地存储文件的相对路径（支持文件夹）
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public String relativePathOf(File file) {
		if (file == null) {
			log.error("Null file(or folder) is founded.");
			return null;
		}

		String path = file.getAbsolutePath();

		// 不在相对路径下
		if (path.indexOf(this.rootDir()) == -1) {
			log.error("Not the sub-directory of: " + this.rootDir());
			return null;
		}

		path = path.replace(this.rootDir(), "");

		return path;
	}

	/**
	 * 构造文件的绝对路径
	 * 
	 * @param relativeFilePath
	 * @return
	 */
	public String absoluteFilePathFrom(String relativeFilePath) {
		if (StringUtils.isBlank(relativeFilePath)) {
			log.error("A blank relative path.");
			return null;
		}

		return this.fileTool.appendDir(this.rootDir(), relativeFilePath);
	}

	/**
	 * 通过相对路径获取文件对象
	 * 
	 * @param relativeFilePath
	 * @return 如果文件存在，则返回文件对象。其它情况返回null。
	 */
	public File getExistsFileOf(String relativeFilePath) {
		String absoluteFilePath = this.absoluteFilePathFrom(relativeFilePath);

		if (StringUtils.isBlank(absoluteFilePath)) {
			log.error("Can not create a absolute file path by: " + relativeFilePath);
			return null;
		}

		File file = new File(absoluteFilePath);

		if (file.isFile())
			return file;

		return null;
	}

	/**
	 * 存储文件（保存系统文件，并且记录到数据库）
	 * 
	 * @param multipartFile
	 * @param extendData
	 * @return
	 */
	public FileModel storeFile(MultipartFile multipartFile, String extendData) {
		// 存储文件
		File file = this.storeFileToDisk(multipartFile);

		if (file == null || !file.isFile())
			return null;


		// 存储数据库
		FileModel data = this.storeFileToDB(file, multipartFile.getOriginalFilename(), extendData);

		if (data == null) {
			this.fileTool.deleteFromDisk(file.getAbsolutePath()); // 删除已经保存的物理文件
			return null;
		}

		return data;
	}

	/**
	 * 移除文件（包括数据库信息与物理文件）
	 * 
	 * @param fileId
	 * @return
	 */
	public boolean removeFile(String fileId) {
		if (StringUtils.isBlank(fileId)) {
			log.warn("Invalid file id. It is blank.");
			return false;
		}

		FileModel file = this.fileModelSrv.get(fileId);

		if (file == null) {
			log.warn("Can not found the file db info by file id: " + fileId);
			return false;
		}

		file = this.fileModelSrv.delete(file);

		if (file == null) {
			log.warn("Failed to delete file db info.");
			return false;
		}

		// 不考虑物理文件无法删除的情况
		String absolutePath = this.absoluteFilePathFrom(file.getRelativeFilePath());
		if (!this.fileTool.deleteFromDisk(absolutePath)) {
			log.error("Failed to delete file from disk. " + absolutePath);
		}

		return true;
	}

	/**
	 * 当前文件存储的相对路径，使用yyyyMMdd格式存储
	 * 
	 * @return
	 */
	protected String storageDir() {
		return this.fileTool.appendDir(this.rootDir(), this.YYYYMMDD.format(new Date()));
	}

	/**
	 * 获取上传的文件列表
	 * 
	 * @param request
	 * @return
	 */
	protected List<MultipartFile> parseMultipartFile(final HttpServletRequest request) {
		if (request == null || !ServletFileUpload.isMultipartContent(request)) {
			log.error("Not a multipart http servlet request.");
			return null;
		}

		MultipartHttpServletRequest req = null;

		if (!MultipartHttpServletRequest.class.isInstance(request))
			// 转换成MultipartHttpServletRequest
			req = this.resolver.resolveMultipart(request);
		else
			req = (MultipartHttpServletRequest) request;

		Iterator<String> it = req.getFileNames();

		if (it == null)
			return null;

		List<MultipartFile> ret = new ArrayList<MultipartFile>();

		while (it.hasNext()) {
			String field = it.next();

			MultipartFile file = req.getFile(field);

			ret.add(file);
		}

		return ret.isEmpty() ? null : ret;

	}

	/**
	 * 返回json消息
	 * 
	 * @param msg
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	protected String jsonMessage(boolean success, String msg, Object data) {
		MessageModel m = success ? MessageModel.createSuccessMessage(msg, data) : MessageModel.createErrorMessage(msg, data);

		String ret;

		try {
			ret = this.JSON_STRING.writeValueAsString(m);
		} catch (JsonProcessingException e) {
			ret = "{\"success\":" + success + ",\"msg\":\"File server error.\"}";
			log.error("", e);
		}

		return ret;
	}

	/**
	 * 返回一个错误json字符串
	 * 
	 * @param msg
	 * @param data
	 * @return
	 */
	protected String jsonErrMessage(String msg, Object data) {
		return this.jsonMessage(false, msg, data);
	}

	/**
	 * 返回一个成功json字符串
	 * 
	 * @param msg
	 * @param data
	 * @return
	 */
	protected String jsonSuccessMessage(String msg, Object data) {
		return this.jsonMessage(true, msg, data);
	}

	/**
	 * 获取content type
	 * 
	 * @param context
	 * @return
	 */
	protected MediaType getDownloadContentType(ServletContext context, String suffix) {
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
	 * 获取html的content type
	 * 
	 * @return
	 */
	protected MediaType getHtmlContentType() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("charset", "utf-8");

		return new MediaType(MediaType.TEXT_HTML, parameters);
	}

	/**
	 * 获取下载文件时的Content-Disposition值
	 * 
	 * @param download
	 * @return
	 */
	protected String getContentDispositionValue(String fileName, boolean download) {
		String value = download ? "attachment;" : "inline;";

		if (StringUtils.isBlank(fileName))
			return value;

		String encodeFileName = new String(URLCodec.encodeUrl(this.WWW_FORM_URL, fileName.getBytes()));

		value += "filename=" + encodeFileName; // IE可以识别的模式

		value += ";filename*=utf-8''" + encodeFileName; // 现代浏览器可以识别的模式

		return value;
	}

	/**
	 * 存储文件到系统磁盘
	 * 
	 * @param file
	 * @return
	 */
	private File storeFileToDisk(final MultipartFile file) {
		if (file == null) {
			log.error("Null multipart file or blank directory.");
			return null;
		}

		String dir = this.storageDir(); // 存储路径
		String suffix = this.fileTool.fileSuffix(file.getOriginalFilename()); // 上传的文件后缀
		// 新建一个基于存储路径的文件
		File target = this.fileTool.newRandomFileIn(dir, suffix, true);

		if (target == null) {
			log.error("Failed to new a target file in dir: " + dir);
			return null;
		}

		try {
			file.transferTo(target);
		} catch (IllegalStateException | IOException e) {
			log.error("Failed to store file into dir: " + dir, e);
			return null;
		}

		return target;
	}

	/**
	 * 存储文件到数据库
	 * 
	 * @param file 真实存在的物理文件
	 * @param reName 文件真实名称。为空时，使用物理文件名称
	 * @param extendData 扩展信息，可以为空
	 * @return
	 */
	private FileModel storeFileToDB(final File file, String reName, String extendData) {
		if (file == null || !file.isFile()) {
			log.error("Need a exists file.");
			return null;
		}

		String relativeFilePath = this.relativePathOf(file);

		if (relativeFilePath == null) {
			log.error("Can not get a relative file path.");
			return null;
		}

		long size = file.length(); // 文件长度
		
		String fullFileName = StringUtils.isBlank(reName) ? file.getName() : reName; // 文件名称

		String name = this.fileTool.fileShortName(fullFileName); // 上传的文件名称
		String suffix = this.fileTool.fileSuffix(fullFileName); // 上传的文件后缀
		String shaCode = null;
		try {
			shaCode = SuredyFileTool.getShaCode(file);
		} catch (NoSuchAlgorithmException | IOException e) {
			log.error(e.getMessage());
		} 
		// 保存到数据库
		FileModel model = this.fileModelSrv.save(name, suffix, relativeFilePath, size, shaCode, extendData);

		if (model == null)
			return null;

		return model;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public void setFileTool(SuredyDiskFileTool fileTool) {
		this.fileTool = fileTool;
	}

	public void setResolver(CommonsMultipartResolver resolver) {
		this.resolver = resolver;
	}

}
