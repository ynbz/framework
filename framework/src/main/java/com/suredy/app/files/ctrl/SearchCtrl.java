package com.suredy.app.files.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.files.entity.UploadFileEntity;
import com.suredy.app.files.service.UploadFileSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.security.entity.User2RoleEntity;
import com.suredy.security.model.User;
import com.suredy.security.service.User2RoleSrv;
import com.suredy.tools.file.SuredyFileService;
import com.suredy.tools.file.model.FileModel;
import com.suredy.tools.file.srv.FileModelSrv;

/**
 * @Description 文件管理
 * @author zyh
 * @date 2017-03-07
 *
 */
@Controller
@RequestMapping({"/files"})
public class SearchCtrl extends BaseCtrl {

	@Autowired
	private UploadFileSrv ufSrv;

	@Autowired
	private FileModelSrv modelSrv;

	@Autowired
	private SuredyFileService fileSrv;
	
	@Autowired
	private User2RoleSrv u2rSrv;


	// -----------------------------added for full text search
	// begin.---------------------------------------

	@Value("${suredy.search.url}")
	private String solrServerUrl; // 全文检索服务器

	private static SolrClient server = null;

	// 建立客户端连接
	private SolrClient getClient() throws SolrServerException {
		if (server == null) {
			if (StringUtils.isEmpty(solrServerUrl)) {
				throw new SolrServerException("Solr Server URL is null, can't initialize Solr client.");
			}
			server = new HttpSolrClient(solrServerUrl);
		}
		return server;
	}

	/**
	 * 单文件索引
	 * 
	 * @param path
	 * @param name
	 * @throws IOException
	 * @throws SolrServerException
	 * @throws Exception
	 */
	private void indexFile(String id, String path, String type, String name, String title, String keyword, String size) throws IOException, SolrServerException {
		ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
		String contentType = getContentType(name);
		if (!contentType.equals("others")) {
			SolrClient client = getClient();
			File file = new File(path);
			if (file.exists()) {
				up.addFile(file, contentType);
				up.setParam("literal.id", (id == null ? path : id));
				up.setParam("literal.path", path);
				up.setParam("literal.type", type);
				up.setParam("literal.name", name);
				up.setParam("literal.prefix", name.substring(name.lastIndexOf(".")));
				up.setParam("literal.title", title);
				up.setParam("literal.size", size);
				up.setParam("literal.keyword", keyword);
				up.setParam("fmap.author", "author");
				up.setParam("fmap.last_modified", "lastModified");
				up.setParam("fmap.content_type", "contentType");
				up.setParam("fmap.content", "text");

				up.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
				client.request(up);
				client.commit();
			}
		}
	}

	private static String getContentType(String name) {
		String contentType = null;
		String prefix = name.substring(name.lastIndexOf(".") + 1);
		if (prefix.equalsIgnoreCase("xls")) {
			contentType = "application/vnd.ms-excel";
		} else if (prefix.equalsIgnoreCase("xlsx")) {
			contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if (prefix.equalsIgnoreCase("doc")) {
			contentType = "application/msword";
		} else if (prefix.equalsIgnoreCase("docx")) {
			contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if (prefix.equalsIgnoreCase("ppt")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if (prefix.equalsIgnoreCase("pptx")) {
			contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		} else if (prefix.equalsIgnoreCase("pdf")) {
			contentType = "application/pdf";
		} else if (prefix.equalsIgnoreCase("txt")) {
			contentType = "text/plain";
		} else if (prefix.equalsIgnoreCase("rtf")) {
			contentType = "application/x-rtf";
		} else {
			contentType = "others";
		}
		return contentType;
	}

	@Transactional
	@RequestMapping(value = "/index/update", method = RequestMethod.POST)
	@ResponseBody
	public Object searchUpdate() {

		List<UploadFileEntity> managers = this.ufSrv.readByEntity(null);

		for (UploadFileEntity manager : managers) {
			String fileIds = manager.getFileUrlId();
			DetachedCriteria dc = this.modelSrv.getDc();
			if (!StringUtils.isBlank(fileIds)) {
				String[] ids = fileIds.split(",");
				dc.add(Restrictions.in("id", ids));
			}
			@SuppressWarnings("unchecked")
			List<FileModel> files = (List<FileModel>) this.modelSrv.readByCriteria(dc);
			if (files != null && !files.isEmpty()) {
				for (FileModel file : files) {
					String id = file.getId();	
					try {
						String path = this.fileSrv.getExistsFileOf(file.getRelativeFilePath()).getAbsolutePath();
						path = path.replaceAll(Matcher.quoteReplacement(File.separator), "/");
						String name = file.getFileName();
						String title = manager.getTitle();
						String type = manager.getFolder().getId();
						String size = String.valueOf(file.getSize());
						String keyword = manager.getKeyWord();
						indexFile(id, path, type, name, title, keyword, size);
					} catch (IOException | SolrServerException e) {
						System.out.println(file.getRelativeFilePath());
						e.printStackTrace();
						return MessageModel.createErrorMessage("更新索引库错误", e);
					}
				}
			}
		}

		return MessageModel.createSuccessMessage(null, null);
	}

	@RequestMapping(value = "/search")
	public ModelAndView searchDocument(HttpServletRequest request) {

		ModelAndView view = new ModelAndView("/app/files/search-result");
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

		String page = request.getParameter("page");
		String size = request.getParameter("size");
		String keyword = request.getParameter("keyword");

		int pageIndex = 1, pageSize = 10;// Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		int start = pageSize * (pageIndex - 1);
		Long count = 0L;
		SolrDocumentList documents;
		if (StringUtils.isNotEmpty(keyword)) {

			SolrQuery query = new SolrQuery();
			// 查询关键词，*:*代表所有属性、所有值，即所有index, 此处仅仅查询text字段
			query.set("q", "title:" + keyword + " OR name:" + keyword + " OR keyword:" + keyword + " OR text:" + keyword);
			// 查询过滤,允许访问的文件分类
			
			// type:(aaa OR bbb OR ccc)
			if (!allowedFolders.isEmpty()) {
				StringBuffer fq = new StringBuffer();
				fq.append("type:(");
				for (int i = 0; i < allowedFolders.size() - 1; i++) {
					fq.append(allowedFolders.get(i)).append(" OR ");
				}
				fq.append(allowedFolders.get(allowedFolders.size() - 1)).append(")");
				query.addFilterQuery(fq.substring(0));
			}
			

			// 分页，start=0就是从0开始，rows=10当前返回10条记录，第二页就是变化start这个值为10就可以了。
			query.set("start", start);
			query.set("rows", pageSize);
			// 排序，如果按照id排序，那么 写为： id desc(or asc)
			// query.addSort("title", ORDER.desc);
			query.addSort("lastModified", ORDER.desc);
			// the response writer type
			query.set("wt", "json");

			query.setHighlight(true); // 开启高亮组件
			query.addHighlightField("text");// 高亮字段
			query.setHighlightSimplePre("<font color=\"#e11\">");// 标记
			query.setHighlightSimplePost("</font>");
			query.setHighlightSnippets(1);// 结果分片数，默认为1
			query.setHighlightFragsize(512);// 每个分片的最大长度，默认为100
			query.set("timeAllowed", "30000"); // miliseconds
			QueryResponse response = null;
			try {
				SolrClient client = getClient();
				response = client.query(query);
				if (response != null) {
					Map<String, Map<String, List<String>>> highlightMap = response.getHighlighting();
					documents = response.getResults();
					count = documents.getNumFound();
					List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
					for (SolrDocument document : documents) {
						Map<String, Object> doc = new HashMap<String, Object>();
						// 复制查询结果，设置高亮
						String id = (String) document.getFieldValue("id");
						if (id != null) {
							Collection<String> fields = document.getFieldNames();
							for (String field : fields) {
								doc.put(field, document.getFieldValue(field));
							}

							List<String> summaries = highlightMap.get(document.get("id").toString()).get("text");
							if (summaries != null && !summaries.isEmpty()) {
								String summary = summaries.get(0);
								if (StringUtils.isNotEmpty(summary)) {
									doc.put("summary", summary);
								}
							}
							data.add(doc);
						}
					}

					view.addObject("data", data);
				}

			} catch (Exception e) {
				view.addObject("reason", "未能连接全文检索服务,可能服务未启动或者指向的搜索域配置有误!");
			}
		}

		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("keyword", keyword);

		return view;
	}

	// -----------------------------added for full text search
}
