package com.suredy.pageoffice.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import com.zhuozhengsoft.pageoffice.PageOfficeLink;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;

@Controller
@RequestMapping("/pageoffice")
public class TestPageOfficeCtrl  extends BaseCtrl{
	
	@RequestMapping(value = "/pageofficeview")
	public ModelAndView pageOfficeView() {
		ModelAndView view = new ModelAndView("/test/pageoffice/pageoffice-view");
		return view;
	}
	
	@RequestMapping(value = "/openwindow")
	public ModelAndView openWindow(HttpServletRequest request,String url,String style) {
		return  new ModelAndView( new RedirectView(PageOfficeLink.openWindow(request, url,style)));
	}
	
	/**
	 * 普通修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/simple/openword")
	public ModelAndView simpleOpenWord(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/simpleWord/view-word");
		
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/simpleWord/test.doc");
		
		//******************************卓正PageOffice组件的使用*******************************
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setSaveFilePage("saveFile");//如要保存文件，此行必须
		poCtrl1.addCustomToolButton("保存", "Save()", 1);//添加自定义工具栏按钮
		poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
		//打开文件
		poCtrl1.webOpen(filePath,  OpenModeType.docNormalEdit, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	 
		
		return view;
	}
	
	/**
	 * 普通修改保存
	 * @return
	 */
	@RequestMapping({"/simple/saveFile"})
	@ResponseBody
	public Object simpleSaveWirdFile(HttpServletRequest request,HttpServletResponse response){
		FileSaver fs=new FileSaver(request,response);
		fs.saveToFile(request.getSession().getServletContext().getRealPath("test/pageoffice/simpleWord/")+"/"+fs.getFileName());
		fs.close();
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 只读
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/readOnly/openword")
	public ModelAndView readOnlyOpenWord(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/readOnly/view-word");
		
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/readOnly/test.doc");
		
		//******************************卓正PageOffice组件的使用*******************************
		//设置PageOffice服务器组件
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		
		poCtrl1.setAllowCopy(false);//禁止拷贝
		poCtrl1.setMenubar(false);//隐藏菜单栏
		poCtrl1.setOfficeToolbars(false);//隐藏Office工具条
		poCtrl1.setCustomToolbar(false);//隐藏自定义工具栏
		poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
		
		//打开文件
		poCtrl1.webOpen(filePath, OpenModeType.docReadOnly, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		
		return view;
	}
	
	/**
	 * 套红列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taohong/openword")
	public ModelAndView taohongOpenWord(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/taohong/index");
		return view;
	}
	
	/**
	 * 修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taohong/edit")
	public ModelAndView taohongEdit(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/taohong/edit");
		
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/test.doc");
		
		//***************************卓正PageOffice组件的使用********************************
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.addCustomToolButton("保存", "Save", 1);
	    poCtrl1.addCustomToolButton("领导圈阅", "StartHandDraw", 3);
	    poCtrl1.addCustomToolButton("分层显示手写批注", "ShowHandDrawDispBar", 7);
	    poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
	    poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz");//此行必须
		poCtrl1.setSaveFilePage("saveFile");
		poCtrl1.webOpen(filePath, OpenModeType.docNormalEdit, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
		
		return view;
	}
	
	
	/**
	 * 套红
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taohong/taohong")
	public ModelAndView taohongTaohong(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/taohong/taohong");
		
		String fileName = "";
		String mbName = request.getParameter("mb");


		//***************************卓正PageOffice组件的使用********************************
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		poCtrl1.setCustomToolbar(false);
		poCtrl1.setSaveFilePage("saveFile");
		
		if (mbName != null && mbName.trim() != "") {
			// 选择模板后执行套红
			
			// 复制模板，命名为正式发文的文件名：zhengshi.doc
			fileName = "zhengshi.doc";
			String templateName = request.getParameter("mb");
			String templatePath = request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/" + templateName);
			String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/" + fileName);
			copyFile(templatePath, filePath); 

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
			sTextS.setValue("[word]"+request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/test.doc")+"[/word]");
			DataRegion sTitle = doc.openDataRegion("PO_sTitle");
			 poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
			sTitle.setValue("北京某公司文件");
			DataRegion topicWords = doc.openDataRegion("PO_TopicWords");
			topicWords.setValue("Pageoffice、 套红");
			poCtrl1.setWriter(doc);
			
		} else {
			//首次加载时，加载正文内容：test.doc
			fileName = "test.doc";
			
		}
		
		poCtrl1.webOpen( request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/" + fileName), OpenModeType.docNormalEdit, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须
		
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
	 * 只读
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taohong/readOnly")
	public ModelAndView taohongReadOnly(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/taohong/readOnly");
		
		String fileName = "zhengshi.doc"; //正式发文的文件
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/"+fileName);
		
		//*****************************卓正PageOffice组件的使用****************************
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		
		poCtrl1.setCaption(fileName);			
		poCtrl1.addCustomToolButton("另存到本地", "ShowDialog1()", 5);
	    poCtrl1.addCustomToolButton("页面设置", "ShowDialog2()", 0);
	    poCtrl1.addCustomToolButton("打印", "ShowDialog3()", 6);
	    poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen()", 4);
	    poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
		poCtrl1.setMenubar(false);
		poCtrl1.setOfficeToolbars(false);
		
		//poCtrl1.setSaveFilePage("savefile.jsp");
		poCtrl1.webOpen(filePath, OpenModeType.docAdmin, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		return view;
	}
	
	/**
	 * 普通修改保存
	 * @return
	 */
	@RequestMapping({"/taohong/saveFile"})
	@ResponseBody
	public Object taohongSaveWirdFile(HttpServletRequest request,HttpServletResponse response){
		FileSaver fs=new FileSaver(request,response);
		fs.saveToFile(request.getSession().getServletContext().getRealPath("test/pageoffice/taohong/")+"/"+fs.getFileName());
		fs.close();
		return MessageModel.createSuccessMessage(null, null);
	}
	
	/**
	 * 电子印章
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/insertSeal/openword")
	public ModelAndView insertSealOpenword(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/insertSeal/view-word");
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/insertSeal/template.doc");
		
		//******************************卓正PageOffice组件的使用*******************************
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
		//隐藏菜单栏
		poCtrl1.setMenubar(false);
		//添加自定义按钮
		poCtrl1.addCustomToolButton("加盖印章", "InsertSeal()", 2);
	    poCtrl1.addCustomToolButton("签字", "AddHandSign()", 3);
	    poCtrl1.addCustomToolButton("验证印章", "VerifySeal()", 5);
	    poCtrl1.addCustomToolButton("修改密码", "ChangePsw()", 0);
	    poCtrl1.setCaption("水滴科技office演示");//设置页面的显示标题
		poCtrl1.webOpen(filePath, OpenModeType.docNormalEdit, "张三");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		
		return view;
	}
	
	
	/**
	 * 修改有痕迹
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/revisionsList/openword")
	public ModelAndView revisionsListOpenword(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/test/pageoffice/revisionsList/view-word");
		String filePath = request.getSession().getServletContext().getRealPath("test/pageoffice/revisionsList/test.doc");
		
		/******************************卓正PageOffice组件的使用*******************************/
		//设置PageOffice服务器组件
		PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
		poCtrl1.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须
	        poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened()");
	        poCtrl1.addCustomToolButton("保存", "Save()", 1); 
		poCtrl1.setOfficeToolbars(false);//隐藏office工具栏
	        poCtrl1.setSaveFilePage("saveFile");
		//打开文件
		poCtrl1.webOpen(filePath, OpenModeType.docRevisionOnly, "Tom");
		poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须	
		
		return view;
	}
	
	/**
	 * 普通修改保存
	 * @return
	 */
	@RequestMapping({"/revisionsList/saveFile"})
	@ResponseBody
	public Object revisionsListSaveWirdFile(HttpServletRequest request,HttpServletResponse response){
		FileSaver fs=new FileSaver(request,response);
		fs.saveToFile(request.getSession().getServletContext().getRealPath("test/pageoffice/revisionsList/")+"/"+fs.getFileName());
		fs.close();
		return MessageModel.createSuccessMessage(null, null);
	}
	
	
}
