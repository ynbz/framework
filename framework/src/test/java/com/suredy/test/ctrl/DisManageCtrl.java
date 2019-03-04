package com.suredy.test.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.suredy.Constants;
import com.suredy.core.ctrl.BaseFlowCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.flow.form.entity.FormEntity;
import com.suredy.flow.form.service.FormSrv;
import com.suredy.flow.manager.entity.FlowEntity;
import com.suredy.flow.manager.service.FlowManagerSrv;
import com.suredy.test.model.DisManage;
import com.suredy.test.service.DisManageSrv;
import com.suredy.tools.file.SuredyDiskFileTool;
import com.suredy.tools.file.srv.FileModelSrv;

/**
 * @Title DisManageCtrl
 * @Package com.suredy.test.ctrl
 * @Description 发文控制类
 * @author zyh
 * @date 2017-03-09
 *
 */
@Controller
@RequestMapping(value = "/dism")
public class DisManageCtrl extends BaseFlowCtrl{
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	private DisManageSrv dismSrv;
	
	@Autowired
	private FormSrv formSrv;
	
	@Autowired
	private FlowManagerSrv flowManagerSrv;
	
	@Autowired
	protected FileModelSrv fileModelSrv;
	
	@Autowired
	protected SuredyDiskFileTool fileTool; // 文件操作工具
	
	@Value("${file.server.root.dir}")
	private String rootDir; // 文件存储根目录
	

	
	@RequestMapping(value = "/list")
	public ModelAndView list(String page, String size,String startTime,String endTime,String fileTypeCode) {
		
		ModelAndView view = new ModelAndView("/test/dismanage/list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		List<DisManage> data = dismSrv.getAll(pageIndex, pageSize, startTime, endTime,fileTypeCode);
		int count = dismSrv.count( startTime, endTime,fileTypeCode);
		
		view.addObject("data", data);
		view.addObject("fileTypeCode", fileTypeCode);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		return view;
	}
	
	@RequestMapping({"/newDis/{fileType}"})
	@ResponseBody
	public ModelAndView newDis(@PathVariable String fileType) {
		FormEntity form = null;
		ModelAndView view = null;
		if(!StringUtils.isBlank(fileType)){
			form = formSrv.getByFileType(fileType);
			if(form!=null){
				FlowEntity flm = flowManagerSrv.getById(form.getFlowId());
				DisManage dism = new DisManage();
				dism.setDrafter(this.getUser().getFullName());
				dism.setDraftDate(new Date());
				dism.setTitle(form.getName());
				this.createFlow(dism, form.getFileType(),flm.getCode(),form.getDoSthPath(),dism.getTitle());
				view = new ModelAndView(new RedirectView(this.createTodoUri(form.getDoSthPath(), dism.getId())));//createTodoUri
			}else{
				view = new ModelAndView("flow-errorMessage");
			}
		}else{
			view = new ModelAndView("flow-errorMessage");
		}
		
		return view;
	}
	
	/**
	 * 打开保存/修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "view/{id}")
	public ModelAndView view(@PathVariable String id) {
		ModelAndView view = new ModelAndView("/test/dismanage/addDis");
		
		DisManage dism;
		if(StringUtils.isEmpty(id)){
			dism=new DisManage();
		}else{
			dism = this.dismSrv.get(id);
		}
		
		view.addObject("fileTypeName", "发文");
		view.addObject("dism", dism);
		return view;
	}
	
	
	/**
	 * 保存/修改
	 * @param request
	 * @return
	 */
	@RequestMapping("saveOreditDisM")
	@ResponseBody
	public Object doEditRole(HttpServletRequest request) {
		DisManage dism = null;
		
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String asUnit = request.getParameter("asUnit");
		String ctUnit = request.getParameter("ctUnit");
		String sDepartment = request.getParameter("sDepartment");
		String disUnit = request.getParameter("disUnit");
		String issuePerson = request.getParameter("issuePerson");
		String issueDate = request.getParameter("issueDate");
		String drafter = request.getParameter("drafter");
		String draftDate = request.getParameter("draftDate");
		String phone = request.getParameter("phone");
		String disType = request.getParameter("disType");
		String disWrodSize = request.getParameter("disWrodSize");
		String degreeOfE = request.getParameter("degreeOfE");
		String printNumber = request.getParameter("printNumber");
		String subjectTerm = request.getParameter("subjectTerm");
		String comment = request.getParameter("comment");
		String fileType = request.getParameter("fileType");
		FormEntity form = null;
		if(!StringUtils.isBlank(fileType)){
			form = formSrv.getByFileType(fileType);
		}
		
		if(!StringUtils.isBlank(id)){
			dism = dismSrv.get(id);
		}else{
			dism = new DisManage();
		}
		
		try {
			dism.setTitle(title);
			dism.setAsUnit(asUnit);
			dism.setCtUnit(ctUnit);
			dism.setsDepartment(sDepartment);
			dism.setDisUnit(disUnit);
			dism.setIssuePerson(issuePerson);
			dism.setIssueDate(formatter.parse(issueDate));
			dism.setDrafter(drafter);
			dism.setDraftDate(formatter.parse(draftDate));
			dism.setPhone(phone);
			dism.setDisType(disType);
			dism.setDisWrodSize(disWrodSize);
			dism.setDegreeOfE(degreeOfE);
			dism.setPrintNumber(printNumber);
			dism.setSubjectTerm(subjectTerm);
			dism.setComment(comment);
			
		} catch (Exception e) {
			return MessageModel.createErrorMessage("时间错误！", null);
		}
		
		this.dismSrv.saveOrUpdate(dism);
		if(form!=null)
			this.updateTodo(dism, dism.getTitle(),createTodoUri(form.getDoSthPath(), dism.getId()));
		
		return MessageModel.createSuccessMessage(null, null);
	}
	
}
