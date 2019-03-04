package com.suredy.app.equipasset.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suredy.Constants;
import com.suredy.app.equipasset.model.CheckupLog;
import com.suredy.app.equipasset.model.EquipAsset;
import com.suredy.app.equipasset.model.EquipAssetUpdateLog;
import com.suredy.app.equipasset.service.CheckupLogSrv;
import com.suredy.app.equipasset.service.EquipAssetSrv;
import com.suredy.app.equipasset.service.EquipAssetUpdateLogSrv;
import com.suredy.app.equipoutstorage.model.EquipOutStorage;
import com.suredy.app.equipoutstorage.service.EquipOutStorageSrv;
import com.suredy.app.type.model.ClassifyManage;
import com.suredy.app.type.model.Type;
import com.suredy.app.type.service.ClassifyManageSrv;
import com.suredy.app.type.service.TypeSrv;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.service.OrderEntity;
import com.suredy.security.model.User;
import com.suredy.security.service.UserSrv;

/**
 * 
 * @author lhl
 * 
 */
@Controller
@RequestMapping(value = "/ChannelCtrl")
public class EquipAssetCtrl extends BaseCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private EquipAssetSrv equipAssetSrv;
	@Autowired
	private ClassifyManageSrv classifyManageSrv;

	@Autowired
	private TypeSrv typeSrv;

	@Autowired
	private EquipOutStorageSrv equipOutStorageSrv;

	@Autowired
	private CheckupLogSrv checkupLogSrv;
	
	@Autowired
	private UserSrv userSrv;

	
	@Autowired
	private EquipAssetUpdateLogSrv eqLogSrv;

	@RequestMapping(value = "/getEquipData")
	public ModelAndView getEquipData(String page, String size, String typeId, EquipAsset equipasset, String sortField, String sort) {
		User user = this.getUser();
		List<String> allowed = this.userSrv.getPermissions(user.getUniqueCode());
		ModelAndView view = new ModelAndView("/app/equipasset/equipasset-list");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		equipasset.setIdentifying(1);
		Long count = (long) 0;
		List<EquipAsset> data = new ArrayList<EquipAsset>();
		List<EquipAsset> newdata = new ArrayList<EquipAsset>();
		List<ClassifyManage> csdata = new ArrayList<ClassifyManage>();
		List<ClassifyManage> cssearchdata = new ArrayList<ClassifyManage>();
		count = this.equipAssetSrv.Count(typeId, equipasset);
		data = this.equipAssetSrv.getEquipData(pageIndex, pageSize, typeId, equipasset, sortField, sort);
		if (!StringUtils.isBlank(typeId)) {
			csdata = classifyManageSrv.getClassifyDataIsShow(typeId);
			cssearchdata = classifyManageSrv.getClassifyDataIssearch(typeId);
		}
		if (data.size() > 0) {
			newdata = this.datadispose(data);

		}

		view.addObject("data", newdata);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("csdata", csdata);
		view.addObject("childid", typeId);
		view.addObject("searchdata", cssearchdata);
		view.addObject("searchsize", cssearchdata.size());
		view.addObject("equipStatus", EquipAssetCtrl.getEquipStatus());
		view.addObject("equipStatuAlls", EquipAssetCtrl.getEquipStatuAlls());
		view.addObject("permissions", allowed);
		if (StringUtils.isNoneEmpty(sortField)) {
			view.addObject("sortField", sortField);
			view.addObject("sort", sort);
		}
		return view;
	}
	
	/**
	 * 待报废列表
	 * @param page
	 * @param size
	 * @param typeId
	 * @param equipasset
	 * @param sortField
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/getObsEquipData")
	public ModelAndView getObsEquipData(String page, String size, String typeId, EquipAsset equipasset, String sortField, String sort) {
		User user = this.getUser();
		List<String> allowed = this.userSrv.getPermissions(user.getUniqueCode());
		ModelAndView view = new ModelAndView("/app/equipasset/obsolescent-eqList");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		equipasset.setStatus("6");
		equipasset.setIdentifying(1);
		Long count = (long) 0;
		List<EquipAsset> data = new ArrayList<EquipAsset>();
		List<EquipAsset> newdata = new ArrayList<EquipAsset>();
		List<ClassifyManage> csdata = new ArrayList<ClassifyManage>();
		List<ClassifyManage> cssearchdata = new ArrayList<ClassifyManage>();
		count = this.equipAssetSrv.Count(typeId, equipasset);
		data = this.equipAssetSrv.getEquipData(pageIndex, pageSize, typeId, equipasset, sortField, sort);
		if (!StringUtils.isBlank(typeId)) {
			csdata = classifyManageSrv.getClassifyDataIsShow(typeId);
			cssearchdata = classifyManageSrv.getClassifyDataIssearch(typeId);
		}
		if (data.size() > 0) {
			newdata = this.datadispose(data);

		}

		view.addObject("data", newdata);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("csdata", csdata);
		view.addObject("childid", typeId);
		view.addObject("searchdata", cssearchdata);
		view.addObject("searchsize", cssearchdata.size());
		view.addObject("equipStatus", EquipAssetCtrl.getEquipStatus());
		view.addObject("equipStatuAlls", EquipAssetCtrl.getEquipStatuAlls());
		view.addObject("permissions", allowed);
		if (StringUtils.isNoneEmpty(sortField)) {
			view.addObject("sortField", sortField);
			view.addObject("sort", sort);
		}
		return view;
	}
	
	
	/**
	 * 报废列表
	 * @param page
	 * @param size
	 * @param typeId
	 * @param equipasset
	 * @param sortField
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/getScrapEquipData")
	public ModelAndView getScrapEquipData(String page, String size, String typeId, EquipAsset equipasset, String sortField, String sort) {
		User user = this.getUser();
		List<String> allowed = this.userSrv.getPermissions(user.getUniqueCode());
		ModelAndView view = new ModelAndView("/app/equipasset/scrap-eqList");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		equipasset.setStatus("0");
		equipasset.setIdentifying(1);
		Long count = (long) 0;
		List<EquipAsset> data = new ArrayList<EquipAsset>();
		List<EquipAsset> newdata = new ArrayList<EquipAsset>();
		List<ClassifyManage> csdata = new ArrayList<ClassifyManage>();
		List<ClassifyManage> cssearchdata = new ArrayList<ClassifyManage>();
		count = this.equipAssetSrv.Count(typeId, equipasset);
		data = this.equipAssetSrv.getEquipData(pageIndex, pageSize, typeId, equipasset, sortField, sort);
		if (!StringUtils.isBlank(typeId)) {
			csdata = classifyManageSrv.getClassifyDataIsShow(typeId);
			cssearchdata = classifyManageSrv.getClassifyDataIssearch(typeId);
		}
		if (data.size() > 0) {
			newdata = this.datadispose(data);

		}

		view.addObject("data", newdata);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("csdata", csdata);
		view.addObject("childid", typeId);
		view.addObject("searchdata", cssearchdata);
		view.addObject("searchsize", cssearchdata.size());
		view.addObject("equipStatus", EquipAssetCtrl.getEquipStatus());
		view.addObject("equipStatuAlls", EquipAssetCtrl.getEquipStatuAlls());
		view.addObject("permissions", allowed);
		if (StringUtils.isNoneEmpty(sortField)) {
			view.addObject("sortField", sortField);
			view.addObject("sort", sort);
		}
		return view;
	}

	private List<EquipAsset> datadispose(List<EquipAsset> data) {
		List<EquipAsset> newdata = new ArrayList<EquipAsset>();
		List<EquipStatus> equipStatus = EquipAssetCtrl.getEquipStatuAlls();
		for (EquipAsset eas : data) {
			if (eas.getStatus().equals("2") || eas.getStatus().equals("3")) {
				eas.setStatusName("待分配");
				eas.setResponsible("");
				eas.setResponsiblePhone("");
				eas.setUserName("");
				eas.setUserPhone("");
				eas.setUserPlace("");
			} else {
				for (EquipStatus es : equipStatus) {
					String ess = es.getIndex() + "";
					if (ess.equals(eas.getStatus())) {
						eas.setStatusName(es.getStatusName());
					}
				}
			}

			newdata.add(eas);
		}
		return newdata;
	}

	/**
	 * 
	 * @param equipid 设备id（修改才会有）
	 * @param nodeid 类型id（选中类型）
	 * @return 打开保存修改页面
	 */
	@RequestMapping(value = "/getSaveAndUpdatePage")
	@ResponseBody
	public ModelAndView getSaveAndUpdatePage(String equipid, String nodeid) {
		ModelAndView view = new ModelAndView("/app/equipasset/equipasset-info");
		EquipAsset equipdata = new EquipAsset();
		List<ClassifyManage> csdata = new ArrayList<ClassifyManage>();
		if (!StringUtils.isEmpty(equipid)) {
			equipdata = equipAssetSrv.get(equipid);
			if (!StringUtils.isEmpty(equipdata.getType().getId())) {
				csdata = classifyManageSrv.getClassifyData(equipdata.getType().getId(), 1, 999);
			}

			if (equipdata.getStatus().equals("4")) {
				view.addObject("equipStatus", EquipAssetCtrl.getYitouyun());//已投运状态
			} else if (equipdata.getStatus().equals("5")) {
				view.addObject("equipStatus", EquipAssetCtrl.getQita());//其它状态
			} else {
				view.addObject("equipStatus", EquipAssetCtrl.getEquipStatus());
			}
		} else {
			view.addObject("equipStatus", EquipAssetCtrl.getEquipStatus());
		}
		if (!StringUtils.isEmpty(nodeid)) {
			Type type = typeSrv.getByIdEager(nodeid);
			csdata = classifyManageSrv.getClassifyData(nodeid, 1, 999);
			equipdata.setType(type);
		}
		List<User> users = this.userSrv.getAll();
		view.addObject("thisnodeid", nodeid);
		view.addObject("csdata", csdata);
		view.addObject("equipid", equipid);
		view.addObject("equipdata", equipdata);
		view.addObject("users", users);
		return view;
	}
	

	/**
	 * 
	 * 
	 * @return 
	 */
	@RequestMapping(value = "/getSelectUser")
	@ResponseBody
	public ModelAndView getSelectUser(String page, String size,String name ,String unit) {
		ModelAndView view = new ModelAndView("/app/equipasset/selectUser");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;
		
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		}
		if (!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}

		List<User> users = this.userSrv.getByFilter(pageIndex, pageSize, unit, name);
		Integer count = this.userSrv.CountByFilter(unit, name);
		
		
		view.addObject("data", users);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("name", name);
		view.addObject("unit", unit);
		return view;
	}
	

	/**
	 * 
	 * @param equipasset
	 * @return 保存或修改数据
	 */
	@RequestMapping(value = "/saveAndApdatedata")
	@ResponseBody
	public Object saveAndApdatedata(EquipAsset equipasset,String identifying) {
		EquipAssetUpdateLog eqLog = null;
		Date updateEq = new Date();
		String update = this.getUser().getName();
		
		boolean flg = false;
		if(identifying!=null&&!identifying.isEmpty()){
			equipasset.setIdentifying(Integer.parseInt(identifying));
		}else{
			equipasset.setIdentifying(1);
		}
		if (equipasset.getId() != null) {
			EquipAsset equipasset1 = equipAssetSrv.get(equipasset.getId());
			if (!equipasset1.getStatus().equals(equipasset.getStatus()) && equipasset1.getIdentifying() == 1) {//状态改变才能添加记录
				EquipOutStorage equipOutStorage = new EquipOutStorage();
				equipOutStorage.setAssetId(equipasset.getAssetId());
				equipOutStorage.setEquipStatus(equipasset.getStatus());
				equipOutStorage.setUserName(equipasset.getUserName());
				equipOutStorage.setUserId(equipasset.getUserID());
				equipOutStorage.setRecipients(equipasset.getResponsible());
				equipOutStorage.setRecipientsId(equipasset.getResponsibleId());
				equipOutStorage.setBackStatus(0);
				equipOutStorage.setIsBorrow(equipasset.getIsBorrow() + "");
				equipOutStorage.setIsApplyfor("2"); // 人工修改
				equipOutStorage.setUpdateperpeoid(this.getUser().getId());
				equipOutStorage.setComm("设备状态人工修改把：" + equipasset1.getStatus() + ",修改为：" + equipasset.getStatus());
				equipOutStorage.setUserPalce(equipasset.getUserPlace());
				equipOutStorage.setUpdatetime(new Date());
				equipOutStorage = equipOutStorageSrv.save(equipOutStorage);
			}
			
			if(equipasset1.getIdentifying()!=0){
				try {
					eqLog =equipAssetSrv.updateReflect(equipasset1, equipasset,classifyManageSrv.getClassifyData(equipasset1.getType().getId(), 1, 999));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		equipasset.setIsFull(1);//设置设备信息完善
		EquipAsset eas = equipAssetSrv.saveOrUpdate(equipasset);
		
		if(eqLog!=null){
			eqLog.setName(update);
			eqLog.setUpdateEq(updateEq);
			eqLog.setUpdateType(0);
			equipasset.setUpdateEq(updateEq);
			eqLogSrv.save(eqLog);
		}else if(equipasset.getId()==null||equipasset.getId().isEmpty()){
			eqLog = new EquipAssetUpdateLog();
			eqLog.setName(update);
			eqLog.setUpdateEq(updateEq);
			eqLog.setUpdateType(0);
			eqLog.setLaterContent("新增设备");
			eqLog.setEqId(eas.getId());
			equipasset.setUpdateEq(updateEq);
			eqLogSrv.save(eqLog);
		}
		
		if(equipasset.getIsPublic()== null){
			equipasset.setIsPublic(0);
		}
		
		if (!StringUtils.isEmpty(eas.getId())) {
			flg = true;
		}
		return MessageModel.createSuccessMessage(null, flg);
	}
	

	/**
	 * 
	 * @param eqIds 设备ids
	 * @return 设备入库批量处理
	 */
	@RequestMapping(value = "byWarehouse")
	@ResponseBody
	public Object byWarehouse(String[] eqIds) {
		Date updateEq = new Date();
		String update = this.getUser().getName();
		Integer successInt=0;
		if(eqIds!=null&&eqIds.length>0){
			for(String id:eqIds){
				EquipAsset eq = equipAssetSrv.get(id);
				if(eq!=null){
					eq.setIdentifying(1);
					EquipAssetUpdateLog eqLog = new EquipAssetUpdateLog();
					eqLog.setName(update);
					eqLog.setUpdateEq(updateEq);
					eqLog.setUpdateType(0);
					eqLog.setLaterContent("新增设备");
					eqLog.setEqId(eq.getId());
					eq.setUpdateEq(updateEq);
					eqLogSrv.save(eqLog);
					equipAssetSrv.saveOrUpdate(eq);
					successInt++;
				}
			}
		}else{
			EquipAsset equipasset = new EquipAsset();
			equipasset.setIdentifying(0);
			equipasset.setIsFull(1);
			equipasset.setUpUserId(this.getUser().getId());
			List<EquipAsset> eqs = equipAssetSrv.getEquipData(equipasset);
			if(eqs!=null){
				for(EquipAsset eq:eqs){
					eq.setIdentifying(1);
					EquipAssetUpdateLog eqLog = new EquipAssetUpdateLog();
					eqLog.setName(update);
					eqLog.setUpdateEq(updateEq);
					eqLog.setUpdateType(0);
					eqLog.setLaterContent("新增设备");
					eqLog.setEqId(eq.getId());
					eq.setUpdateEq(updateEq);
					eqLogSrv.save(eqLog);
					equipAssetSrv.saveOrUpdate(eq);
					successInt++;
				}
			}
		}
		
		return MessageModel.createSuccessMessage("successInt", successInt);
	}

	@RequestMapping(value = "/deletedata")
	@ResponseBody
	public Object deletedata(String chechedids) {
		boolean flg = equipAssetSrv.deletedata(chechedids);
		return MessageModel.createErrorMessage("flg", flg);
	}
	
	@RequestMapping(value = "/scrapData")
	@ResponseBody
	public Object scrapData(String chechedids) {
		boolean flg = equipAssetSrv.scrapData(chechedids);
		return MessageModel.createErrorMessage("flg", flg);
	}

	/**
	 * 
	 * @param equipid
	 * @return 查看页面
	 * 
	 */
	@RequestMapping(value = "/getLookPage")
	@ResponseBody
	public ModelAndView getLookPage(String equipid) {
		ModelAndView view = new ModelAndView("/app/equipasset/equipasset-look");
		EquipAsset equipdata = new EquipAsset();
		List<ClassifyManage> csdata = new ArrayList<ClassifyManage>();
		if (!StringUtils.isEmpty(equipid)) {
			equipdata = equipAssetSrv.get(equipid);
			if (!StringUtils.isEmpty(equipdata.getType().getId())) {
				csdata = classifyManageSrv.getClassifyData(equipdata.getType().getId(), 1, 999);
			}

			if (equipdata != null) {
				if (equipdata.getStatus().equals("2") || equipdata.getStatus().equals("3")) {
					equipdata.setStatusName("待分配");
					equipdata.setResponsible("");
					equipdata.setResponsiblePhone("");
					equipdata.setUserName("");
					equipdata.setUserPhone("");
					equipdata.setUserPlace("");
				} else {
					List<EquipStatus> equipStatus = EquipAssetCtrl.getEquipStatuAlls();
					for (EquipStatus es : equipStatus) {
						String ess = es.getIndex() + "";
						if (ess.equals(equipdata.getStatus())) {
							equipdata.setStatusName(es.getStatusName());
						}
					}
				}
			}
		}
		view.addObject("csdata", csdata);
		view.addObject("equipid", equipid);
		view.addObject("equipdata", equipdata);
		view.addObject("equipStatus", EquipAssetCtrl.getEquipStatuAlls());
		return view;
	}

	/**
	 * 获取巡检日志
	 * 
	 * @param assetId
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/checkup-log/{assetId}", method = RequestMethod.GET)
	public String checkupLogList(@PathVariable String assetId, Integer page, Integer pageSize, Model model) {
		String view = "app/equipasset/checkup-log";

		if (StringUtils.isBlank(assetId)) {
			model.addAttribute("invalid", true);
			return view;
		}

		if (page == null || page < 1)
			page = 1;

		if (pageSize == null || pageSize < 1)
			pageSize = 30;

		CheckupLog search = new CheckupLog();
		search.setAssetId(assetId);
		OrderEntity order=new OrderEntity();
		order.asc("time");
		List<CheckupLog> data = this.checkupLogSrv.readPageByEntity(search, page, pageSize,order);
		int count = 0;

		if (data != null && !data.isEmpty()) {
			count = this.checkupLogSrv.getCountByEntity(search);
		}

		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("count", count);

		return view;
	}
	
	/**
	 * 
	 * 
	 * @return 
	 */
	@RequestMapping(value = "/eqm-checkup-update/checkupSave")
	@ResponseBody
	public ModelAndView checkupSave(String assetId) {
		ModelAndView view = new ModelAndView("/app/equipasset/checkupSave");
		view.addObject("assetId", assetId);
		return view;
	}

	/**
	 * 记录巡检日志
	 * 
	 * @param status
	 * @param assetId
	 * @param remark
	 * @return see: {@link MessageModel}
	 */
	@RequestMapping(value = "/eqm-checkup/record", method = RequestMethod.POST)
	@ResponseBody
	public Object recordCheckupLog(String checkupStatus, String assetId, String remark) {
		if (!ArrayUtils.contains(CheckupLog.STATUS, Integer.parseInt(checkupStatus)))
			return MessageModel.createErrorMessage("无效的巡检状态！", null);

		if (StringUtils.isBlank(assetId))
			return MessageModel.createErrorMessage("空的“资产ID”！", null);

		
		CheckupLog log = new CheckupLog();

		log.setStatus(Integer.parseInt(checkupStatus));
		log.setTime(new Date());
		log.setUser(this.getUser().getFullName());
		//log.setPlace(place);
		log.setAssetId(assetId);
		log.setRemark(remark);

		log = this.checkupLogSrv.save(log);

		if (log == null)
			return MessageModel.createErrorMessage("保存巡检信息失败！", null);

		return MessageModel.createSuccessMessage("操作成功！", null);
	}
	
	/**
	 * 获取修改日志
	 * 
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/eq-log/{id}", method = RequestMethod.GET)
	public String eqLogList(@PathVariable String id, Integer page, Integer pageSize, Model model) {
		String view = "app/equipasset/eq-log";

		if (StringUtils.isBlank(id)) {
			model.addAttribute("invalid", true);
			return view;
		}

		if (page == null || page < 1)
			page = 1;

		if (pageSize == null || pageSize < 1)
			pageSize = 25;

		EquipAssetUpdateLog search = new EquipAssetUpdateLog();
		search.setEqId(id);
		OrderEntity order=new OrderEntity();
		order.asc("updateEq");
		List<EquipAssetUpdateLog> data = this.eqLogSrv.readPageByEntity(search, page, pageSize,order);
		int count = 0;

		if (data != null && !data.isEmpty()) {
			count = this.eqLogSrv.getCountByEntity(search);
		}

		model.addAttribute("data", data);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("count", count);

		return view;
	}
	
	/**
	 * 
	 * @param assetId
	 * @return 判断资产id是否存在 返回 true or false
	 */
	@RequestMapping(value = "/judgeAssetId")
	@ResponseBody
	public Object judgeAssetId(String assetId, String equipId) {
		return MessageModel.createErrorMessage("flg", this.equipAssetSrv.judgeAssetId(assetId, equipId));
	}

	public static List<EquipStatus> getEquipStatus() {
		List<EquipStatus> equipStatusList = new ArrayList<EquipStatus>();
		equipStatusList.add(EquipStatus.BeiYong);
		equipStatusList.add(EquipStatus.YiChuKu);
		equipStatusList.add(EquipStatus.BaoFei);
		equipStatusList.add(EquipStatus.QiTa);
		return equipStatusList;
	}

	public static List<EquipStatus> getUpLoadEquipStatus() {
		List<EquipStatus> equipStatusList = new ArrayList<EquipStatus>();
		equipStatusList.add(EquipStatus.BeiYong);
		// equipStatusList.add(EquipStatus.ZhanYong);
		equipStatusList.add(EquipStatus.YiChuKu);
		return equipStatusList;
	}

	public static List<EquipStatus> getEquipStatuAlls() {
		List<EquipStatus> equipStatusList = new ArrayList<EquipStatus>();
		equipStatusList.add(EquipStatus.BeiYong);
		equipStatusList.add(EquipStatus.YiChuKu);
		equipStatusList.add(EquipStatus.BaoFei);
		equipStatusList.add(EquipStatus.QiTa);
		equipStatusList.add(EquipStatus.ZhanYong);
		equipStatusList.add(EquipStatus.DaichuKu);
		equipStatusList.add(EquipStatus.DaiBaoFei);
		return equipStatusList;
	}

	// 已投运设备状态
	public static List<EquipStatus> getYitouyun() {
		List<EquipStatus> equipStatusList = new ArrayList<EquipStatus>();
		equipStatusList.add(EquipStatus.YiChuKu);
		equipStatusList.add(EquipStatus.QiTa);
		return equipStatusList;
	}

	// 其他状态修改
	public static List<EquipStatus> getQita() {
		List<EquipStatus> equipStatusList = new ArrayList<EquipStatus>();
		equipStatusList.add(EquipStatus.BeiYong);
		equipStatusList.add(EquipStatus.YiChuKu);
		equipStatusList.add(EquipStatus.QiTa);
		return equipStatusList;
	}

	// 定义状态{0:报废,1:备用,2:占用,3:待出库,4:已出库}
	public enum EquipStatus {
		BaoFei("已报废", 0), BeiYong("待分配", 1), ZhanYong("占用", 2), DaichuKu("待出库", 3), YiChuKu("已投运", 4), QiTa("其他", 5), DaiBaoFei("待报废", 6);

		// 成员变量
		private String statusName;
		private int index;

		// 构造方法
		private EquipStatus(String statusName, int index) {
			this.statusName = statusName;
			this.index = index;
		}

		// 覆盖方法
		@Override
		public String toString() {
			return this.index + "_" + this.statusName;
		}

		public String getStatusName() {
			return statusName;
		}

		public int getIndex() {
			return index;
		}

	};

	@RequestMapping(value = "/equipBack")
	public ModelAndView equipBack() {
		ModelAndView view = new ModelAndView("/app/equipback/equipBack-info");
		return view;
	}

	@RequestMapping(value = "/searchBack")
	@ResponseBody
	public Object searchBack(String assetId) {
		Map<String, String> map = new HashMap<String, String>();
		EquipAsset eq = this.equipAssetSrv.getEquipAsset(assetId);
		if (eq != null) {
			String status = "";
			List<EquipStatus> equipStatus = EquipAssetCtrl.getEquipStatuAlls();
			for (EquipStatus es : equipStatus) {
				String ess = es.getIndex() + "";
				if (ess.equals(eq.getStatus())) {
					status = es.getStatusName();
				}
			}
			map.put("equipModel", eq.getEquipModel());
			map.put("typeName", eq.getTypeName());
			map.put("status", status);
			map.put("assetId", eq.getAssetId());
			map.put("userPlace", eq.getUserPlace());
			map.put("userName", eq.getUserName());
			map.put("responsible", eq.getResponsible());
			map.put("isBorrow", eq.getIsBorrow() + "");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/updateBack")
	@ResponseBody
	public Object updateBack(String assetId, String backtime) {
		Boolean flg = false;
		if (StringUtils.isEmpty(assetId))
			MessageModel.createErrorMessage("flg", flg);

		EquipAsset equipAsset = equipAssetSrv.getEquipAsset(assetId);
		flg = equipOutStorageSrv.updateOutBacktime(equipAsset, backtime);
		equipAsset.setStatus("1");
		equipAsset.setResponsible("");
		equipAsset.setResponsibleId("");
		equipAsset.setUserID("");
		equipAsset.setUserName("");
		equipAsset.setUserPlace("");
		equipAsset.setReceiveDate(null);
		equipAsset.setUserPhone("");
		equipAsset.setResponsiblePhone("");
		equipAsset.setUserUnitId("");
		equipAsset.setUserUnit("");
		if (equipAsset.getIsBorrow() != null && equipAsset.getIsBorrow() == 0) {
			equipAsset.setIsNewEquip(0);
		}
		equipAsset.setIsBorrow(null);
		EquipAsset ea = this.equipAssetSrv.update(equipAsset);
		// 设备归还需要处理第一次归还的设备 没有领用记录的时候
		if (!StringUtils.isEmpty(ea.getId())) {
			flg = true;
		}
		return MessageModel.createErrorMessage("flg", flg);
	}
	
	
	
	/**
	 * 下载设备
	 * 
	 * @return
	 */
	@RequestMapping("downloadEquipAsset")
	public void downloadEquipAsset(String typeId, EquipAsset equipasset,HttpServletRequest request,HttpServletResponse response) {
		DownloadUtil util = new DownloadUtil() ;
		equipasset.setIdentifying(1);
		util.exportToExcel_3(request.getSession().getServletContext().getRealPath("/"),"设备信息.xls",equipAssetSrv.getDowEquipData(typeId, equipasset),response,request);
	}
	
	
	/**
	 * 设备待领取列表
	 * @param person
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "get_equipment")
	public ModelAndView get_equipment(String person,String page, String size) {
		ModelAndView view = new ModelAndView("/app/equipasset/get_equipment");
		int pageIndex = 1, pageSize = Constants.DEFAULT_PAGE_SIZE;;
		if (!StringUtils.isBlank(page)) {
			pageIndex = Integer.parseInt(page);
		} 
		if(!StringUtils.isBlank(size)) {
			pageSize = Integer.parseInt(size);
		}
		
		Long count = this.equipAssetSrv.getEqByStatusCount(person);
		
		List<EquipAsset> data = this.equipAssetSrv.getEqByStatus(person, pageIndex, pageSize);
		
		view.addObject("data", data);
		view.addObject("pageSize", pageSize);
		view.addObject("pageIndex", pageIndex);
		view.addObject("count", count);
		view.addObject("person", person);
		return view;
	}


}
