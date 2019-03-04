
package com.suredy.docOCX.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.docOCX.eav.ButtonEnum;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.tools.file.SuredyDiskFileTool;
import com.suredy.tools.file.model.FileModel;
import com.suredy.tools.file.srv.FileModelSrv;
import com.zhuozhengsoft.pageoffice.DocumentVersion;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeLink;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

/**
 * @author zyh
 *
 */
@Controller
@RequestMapping("/dococx")
public class DocOCXCtrl extends BaseCtrl{
	
	@Autowired
	protected FileModelSrv fileModelSrv;
	
	@Autowired
	protected SuredyDiskFileTool fileTool; // 文件操作工具
	
	@Autowired
	private FormSrv formSrv;
	
	@Autowired
	private DocOCXInit doinit;
	
	@Value("${file.server.root.dir}")
	private String rootDir; // 文件存储根目录
	
	@Value("${open_officewindow_style}")
	private String style;
	
	@Value("${cre_upd_active}")
	private String creupdActive;
	
	@Value("${giveremarks_active}")
	private String gireActive;
	
	@Value("${thong_active}")
	private String thongActive;
	
	@Value("${insert_seal_active}")
	private String insertSealActive;
	
	@Value("${revisions_list_active}")
	private String reListActive;
	
	@PostConstruct
	public void init() {
		doinit.init();
	}
	
	/**
	 * 
	 * @param request
	 * @param url
	 * @param temFileid 模板文件id
	 * @return
	 */
	@RequestMapping(value = "/openwindow")
	public ModelAndView openWindow(HttpServletRequest request,String url,String temFileid) {
		if(!StringUtils.isBlank(temFileid)){
			url=url+"&temFileid="+temFileid;
		}
		return  new ModelAndView( new RedirectView(PageOfficeLink.openWindow(request, url,style)));
	}
	
	/**
	 * 普通创建
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/create")
	public ModelAndView crateWord(HttpServletRequest request,String id,String temFileid) {
		
		ModelAndView view = new ModelAndView("/dococx/simple-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		int index=0;//摆放按键的位置
		String[] addButton = creupdActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		File fileMkdirs = new File(filePath);
		if(fileMkdirs!=null&&fileMkdirs.exists()){//查看是否创建了文件
			poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		}else{
			if(!StringUtils.isBlank(temFileid)){
				// 初始化文件存储路径
				if (StringUtils.isBlank(this.rootDir())) {
					this.setRootDir(this.fileTool.appendDir(this.fileTool.getDiskRoot(), ".file-uploaded"));
				}
				FileModel fm = fileModelSrv.get(temFileid);
				if(fm!=null){
					poCtrl1.webOpen(this.rootDir()+fm.getFilePath(),  OpenModeType.docNormalEdit, this.getUser().getName());
				}else{
					poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
				}
			}else{
				poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
			}
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	/**
	 * 普通修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateWord(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/simple-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		int index=0;//摆放按键的位置
		String[] addButton = creupdActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		File fileMkdirs = new File(filePath);
		if(fileMkdirs!=null&&fileMkdirs.exists()){
			poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		}else{
			poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	
	/**
	 * 只读
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/readOnly")
	public ModelAndView readOnlyOpenWord(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/simple-word");
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		//设置PageOffice服务器组件
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		
		poCtrl1.setAllowCopy(false);//禁止拷贝
		poCtrl1.setMenubar(false);//隐藏菜单栏
		poCtrl1.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl1.setCustomToolbar(false);//隐藏自定义工具栏
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentReadOnly");
		
		//打开文件
		poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	/**
	 * 领导批阅
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/giveRemarks")
	public ModelAndView giveRemarks(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/simple-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setMenubar(false);
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		int index=0;//摆放按键的位置
		String[] addButton = gireActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		File fileMkdirs = new File(filePath);
		if(fileMkdirs!=null&&fileMkdirs.exists()){
			poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		}else{
			poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	/**
	 * 套红
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taohong")
	public ModelAndView taohong(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/taohong-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		String filePath =doinit.getRootDir()+id;//文件路径
		String mbName = request.getParameter("mb");
		String taohongWord= doinit.getRootDir()+"taohong.doc";//创建暂时保存套红文件路径
		File file = new File(taohongWord);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int index=0;//摆放按键的位置
		String[] addButton = thongActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (mbName != null && mbName.trim() != "") {
			// 选择模板后执行套红				
			String templateName = request.getParameter("mb");
			String templatePath = request.getSession().getServletContext().getRealPath("dococx/" + templateName);
			copyFile(templatePath, taohongWord); 

			// 填充数据和正文内容到“zhengshi.doc”
			WordDocument doc = new WordDocument();
			DataRegion copies = doc.openDataRegion("PO_Copies");
			copies.setValue("6");
			DataRegion docNum = doc.openDataRegion("PO_DocNum");
			docNum.setValue("001");
			DataRegion issueDate = doc.openDataRegion("PO_IssueDate");
			issueDate.setValue("2013-5-30");
			DataRegion issueDept = doc.openDataRegion("PO_IssueDept");
			issueDept.setValue("开发部");
			DataRegion sTextS = doc.openDataRegion("PO_STextS");
			sTextS.setValue("[word]"+filePath+"[/word]");
			DataRegion sTitle = doc.openDataRegion("PO_sTitle");
			sTitle.setValue("北京某公司文件");
			DataRegion topicWords = doc.openDataRegion("PO_TopicWords");
			topicWords.setValue("Pageoffice、 套红");
			poCtrl1.setWriter(doc);
			poCtrl1.webOpen( taohongWord, OpenModeType.docNormalEdit, "张三");
		}else{
			poCtrl1.webOpen( filePath, OpenModeType.docNormalEdit, "张三");
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
		view.addObject("id", id);
		return view;
	}
	
	// 拷贝文件
	public void copyFile(String oldPath, String newPath){

		try {
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时 
				InputStream inStream = new FileInputStream(oldPath); //读入原文件 
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) { 
					//System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 电子印章
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertSeal")
	public ModelAndView insertSeal(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/simple-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setMenubar(false);
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		int index=0;//摆放按键的位置
		String[] addButton = insertSealActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		File fileMkdirs = new File(filePath);
		if(fileMkdirs!=null&&fileMkdirs.exists()){
			poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		}else{
			poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	
	/**
	 * 修改有痕迹
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/revisionsList")
	public ModelAndView revisionsListOpenword(HttpServletRequest request,String id) {
		ModelAndView view = new ModelAndView("/dococx/revisionsList-word");
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
		poCtrl1.setMenubar(false);
		String filePath = doinit.getRootDir()+id+".doc";//文件路径
		int index=0;//摆放按键的位置
		String[] addButton = reListActive.split(",");
		for(String str:addButton){
			index++;//循环添加按键位置
			poCtrl1.addCustomToolButton(ButtonEnum.typeOf(str), str+"()", index);//添加自定义工具栏按钮
			if("Save".equals(str)){
				try {
					String encUrl = URLEncoder.encode((filePath), "UTF-8");
					poCtrl1.setSaveFilePage("saveFile?filePath="+encUrl);//如要保存文件，此行必须
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		File fileMkdirs = new File(filePath);
		if(fileMkdirs!=null&&fileMkdirs.exists()){
			poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, this.getUser().getName());
		}else{
			poCtrl1.webCreateNew(this.getUser().getName(), DocumentVersion.Word2003);
		}
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
		
		return view;
	}
	
	
	/**
	 * 修改保存
	 * @return
	 */
	@RequestMapping({"/saveFile"})
	@ResponseBody
	public Object simpleSaveWirdFile(HttpServletRequest request,HttpServletResponse response,String filePath){
		FileSaver fs=new FileSaver(request,response);
		try {
			String urlDecode = URLDecoder.decode(filePath, "UTF-8");
			fs.saveToFile(urlDecode);
			fs.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("confirmIsFile")
	@ResponseBody
	public Object confirmIsFile(String id) {
		File file = new File(doinit.getRootDir()+id+".doc");
		if(!file.exists()){
			return MessageModel.createErrorMessage("未创建正文，不能发送！", null);
		}
		
		return MessageModel.createSuccessMessage(null, null);
	}

	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("isForm")
	@ResponseBody
	public Object doEditRole(String fileTypeCode) {
		FormEntity fe = formSrv.getByFileType(fileTypeCode);
		return MessageModel.createSuccessMessage(fe!=null&&fe.getTemTypeId()!=null&&fe.getIsTemplate().equals("2")?fe.getTemTypeId():null, null);
	}
	
	
	@RequestMapping(value = "/selecttem")
	public ModelAndView selectTem(String temTypeId) {
		ModelAndView view = new ModelAndView("/dococx/selectTem");
		view.addObject("temTypeId", temTypeId);
		return  view;
	}
	
	/**
	 * 获取文件存储的根目录
	 * 
	 * @return
	 */
	public String rootDir() {
		return this.rootDir;
	}
	
	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}
	
}
