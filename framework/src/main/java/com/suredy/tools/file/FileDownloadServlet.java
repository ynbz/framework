package com.suredy.tools.file;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * 下载文件服务
 * 
 * @author VIVID.G
 * @since 2015-10-28
 * @version v0.1
 */
@WebServlet(urlPatterns = "/download/*", loadOnStartup = 0)
public class FileDownloadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2286849663764439571L;

//	private final static Logger log = LoggerFactory.getLogger(FileDownloadServlet.class);
//
//	private static final long serialVersionUID = 1L;
//
//	private String CONTEXT_PATH;
//
//	public void init(ServletConfig config) throws ServletException {
//		super.init(config);
//
//		CONTEXT_PATH = config.getServletContext().getContextPath();
//	}
//
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		this.doPost(req, resp);
//	}
//
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		// 格式：文件名/后缀/相对路径
//		String params = this.params(req.getRequestURI());
//
//		boolean view = false;
//
//		if ("1".equals(req.getParameter("_v"))) {
//			view = true;
//		}
//
//		if (!this.validate(params)) {
//			log.error("Invalid parameters. It is: [" + params + "]");
//			return;
//		}
//
//		String fileName = this.fileName(params);
//		String relativePath = this.relativePath(params);
//
//		resp.setContentType(this.getServletContext().getMimeType(fileName));
//
//		// 在线阅览的时候不能输出文件名称
//		if (!view)
//			resp.setHeader("Content-Disposition", "attachment; filename=\"" + this.fileFullName(fileName, req.getHeader("User-Agent")) + "\"");
//
//		SuredyFileTool ft = ApplicationContextHelper.getBeanByType(SuredyFileTool.class);
//
//		long count = ft.output(req, resp, relativePath);
//
//		if (count == -1) {
//			resp.setCharacterEncoding("UTF-8");
//			resp.setContentType("text/html; charset=utf-8");
//
//			PrintWriter pw = resp.getWriter();
//
//			pw.write("<script type=\"text/javascript\">");
//			pw.write("alert('无法下载指定文件：" + fileName + "');");
//			pw.write("</script>");
//
//			pw.flush();
//		}
//	}
//
//	private String params(String uri) throws UnsupportedEncodingException {
//		String prefix = this.CONTEXT_PATH + "/download/";
//		prefix = prefix.replace("/", "\\/");
//
//		uri = URLDecoder.decode(uri, "UTF-8");
//
//		return uri.replaceFirst(prefix, "");
//	}
//
//	private String relativePath(String params) throws UnsupportedEncodingException {
//		if (!this.validate(params))
//			return null;
//
//		// 取第一段内容
//		String value = params.substring(0, params.lastIndexOf('/'));
//
//		value = value.replace('/', File.separatorChar);
//
//		return URLDecoder.decode(value, "UTF-8");
//	}
//
//	private String fileName(String params) throws UnsupportedEncodingException {
//		if (!this.validate(params))
//			return null;
//
//		// 取第二段内容
//		String value = params.substring(params.lastIndexOf('/') + 1);
//
//		return URLDecoder.decode(value, "UTF-8");
//	}
//
//	private boolean validate(String params) {
//		if (StringUtils.isBlank(params))
//			return false;
//
//		String[] tmp = params.split("\\/");
//
//		if (tmp.length < 2)
//			return false;
//
//		return true;
//	}
//
//	private String fileFullName(String showName, String userAgent) {
//		// new a temp fileName
//		if (StringUtils.isBlank(showName))
//			showName = UUID.randomUUID().toString().replace("-", "");
//
//		try {
//			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
//				// 各种IE的情况
//				showName = URLEncoder.encode(showName, "UTF-8");
//			} else {
//				showName = new String(showName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
//			}
//		} catch (UnsupportedEncodingException e) {
//		}
//
//		return showName;
//	}

}
