package com.suredy.formbuilder.design.ctrl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.suredy.core.ctrl.BaseCtrl;
import com.suredy.core.mvc.model.MessageModel;
import com.suredy.core.service.GBKOrder;
import com.suredy.core.service.OrderEntity;
import com.suredy.formbuilder.design.model.FormDefine;
import com.suredy.formbuilder.design.srv.FormDefineSrv;

@Controller
@RequestMapping("/formbuilder")
public class FormBuilderCtrl extends BaseCtrl {
	private final Logger log = LoggerFactory.getLogger(FormBuilderCtrl.class);

	private final ObjectMapper JSON = new ObjectMapper();

	@Autowired
	private FormDefineSrv formDefineSrv;

	/**
	 * 打开表单设计器
	 * 
	 * @param formDefineId
	 * @return
	 */
	@RequestMapping(value = {"/open/{design:[0|1]}{formDefineId:.{0}|.{32}}"}, method = RequestMethod.GET)
	public String openForm(@PathVariable String formDefineId, @PathVariable boolean design, Model model) {
		String view = "component/formbuilder/editer";

		FormDefine define = this.getOrCreateFormDefine(formDefineId);

		if (define == null)
			model.addAttribute("notFound", true);

		model.addAttribute("data", define);
		model.addAttribute("design", StringUtils.isBlank(formDefineId) ? false : design);

		return view;
	}

	/**
	 * 创建新的form
	 * 
	 * @param define
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object saveForm(FormDefine define) {
		if (define == null)
			return MessageModel.createErrorMessage("无效的表单模版数据！", null);

		String[] ids = {};

		if (!StringUtils.isBlank(define.getId()))
			ids = new String[] {define.getId()};

		if (this.formDefineSrv.exists(define.getName(), define.getVersion(), ids)) {
			return MessageModel.createErrorMessage("存在相同名称与版本号的表单模版！", null);
		}

		define.setLastEditTime(new Date());

		FormDefine ret = null;

		try {
			ret = this.formDefineSrv.saveOrUpdate(define);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ret == null)
			return MessageModel.createErrorMessage("保存表单模版数据失败，请检查数据是否符合规格要求！", null);

		return MessageModel.createSuccessMessage("success", ret);
	}

	/**
	 * 删除表单定义
	 * 
	 * @param formDefineId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteForm(String[] formDefineIds) {
		if (formDefineIds == null || formDefineIds.length <= 0)
			return MessageModel.createErrorMessage("删除失败，表单定义数据无效！", null);

		try {
			StringBuilder ql = new StringBuilder();
			ql.append("DELETE FROM FormDefine T WHERE T.id in (");
			for (int i = 0; i < formDefineIds.length; i++) {
				ql.append("?,");
			}
			ql.replace(ql.length() - 1, ql.length(), ")");

			int count = this.formDefineSrv.deleteByQL(ql.toString(), (Object[]) formDefineIds);

			return MessageModel.createSuccessMessage("删除成功，共删除「" + count + "」条表单定义数据！", null);
		} catch (Exception e) {
			e.printStackTrace();
			return MessageModel.createErrorMessage("删除失败，服务器异常！", null);
		}
	}

	/**
	 * 表单模版列表
	 * 
	 * @param search
	 * @param orderField
	 * @param page
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listForm(FormDefine search, Integer page, Integer pageSize, String order, Model model) {
		String view = "component/formbuilder/list";

		if (search == null)
			search = new FormDefine();

		OrderEntity oe = this.parseOrder(order);

		if (page == null || page < 1)
			page = 1;

		if (pageSize == null || pageSize < 1)
			pageSize = 30;

		List<FormDefine> data = this.formDefineSrv.readPageByEntity(search, page, pageSize, oe == null ? this.formDefineSrv.getDefOrders() : oe);

		model.addAttribute("data", data == null || data.isEmpty() ? null : data);
		model.addAttribute("page", page);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("order", oe);
		model.addAttribute("search", search);
		model.addAttribute("count", this.formDefineSrv.getCountByEntity(search));

		return view;
	}

	/**
	 * 获取（新建）一个表单定义对象
	 * 
	 * @param formDefindId
	 * @return
	 */
	private FormDefine getOrCreateFormDefine(String formDefineId) {
		FormDefine ret = null;

		if (!StringUtils.isBlank(formDefineId)) {
			ret = this.formDefineSrv.get(formDefineId);

			if (ret == null)
				return null;

			return ret;
		}

		ret = new FormDefine();

		ret.setEnable(false);
		ret.setName("「新建的表单模版」");
		ret.setVersion("1.0");
		ret.setCreateTime(null);
		ret.setLastEditTime(null);
		ret.setFormData(null);
		ret.setFormDesc(null);

		return ret;
	}

	private OrderEntity parseOrder(String order) {
		if (StringUtils.isBlank(order))
			return null;

		OrderEntity ret = null;

		try {
			JsonNode data = this.JSON.readValue(order, JsonNode.class);

			if (data.isObject()) {
				Order o = this.jsonToOrder((ObjectNode) data);

				if (o != null) {
					ret = new OrderEntity();
					ret.add(o);
				}
			} else if (data.isArray()) {
				for (JsonNode d : (ArrayNode) data) {
					if (!d.isObject())
						continue;

					Order o = this.jsonToOrder((ObjectNode) d);

					if (o == null) {
						continue;
					}

					if (ret == null)
						ret = new OrderEntity();

					ret.add(o);
				}
			}
		} catch (Exception e) {
			log.error("Can't parse the order json string.", e);
		}

		return ret;
	}

	private Order jsonToOrder(ObjectNode json) {
		if (json == null)
			return null;

		String[] orders = {"asc", "desc"};

		for (Iterator<String> it = json.fieldNames(); it.hasNext();) {
			String key = it.next();
			String val = json.get(key).asText();

			if (StringUtils.isBlank(val) || !ArrayUtils.contains(orders, val = val.toLowerCase()))
				continue;

			if ("name".equalsIgnoreCase(key)) {
				return "asc".equals(val) ? GBKOrder.asc(key) : GBKOrder.desc(key);
			} else {
				return ("asc".equals(val)) ? Order.asc(key) : Order.desc(key);
			}
		}

		return null;
	}

}
