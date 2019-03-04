package com.suredy.formbuilder.eav.helper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.suredy.formbuilder.design.model.FormDefine;
import com.suredy.formbuilder.design.srv.FormDefineSrv;
import com.suredy.formbuilder.eav.model.FormAttributeValue;
import com.suredy.formbuilder.eav.model.FormEntry;
import com.suredy.formbuilder.eav.srv.FormEntrySrv;

/**
 * 表单数据处理器
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
@Service
public class FormDataProcesser {
	private final Logger log = LoggerFactory.getLogger(FormDataProcesser.class);

	@Autowired
	private FormDataHelper formDataHelper;

	@Autowired
	private FormDefineSrv formDefineSrv;

	@Autowired
	private FormEntrySrv formEntrySrv;

	/**
	 * 保存表单数据
	 * 
	 * @param formDefineId
	 * @param formTitle
	 * @param request
	 * @return
	 */
	public FormEntry saveData(String formDefineId, HttpServletRequest request) {
		FormDefine formDefine = this.formDefineSrv.get(formDefineId);

		if (formDefine == null)
			return null;

		List<FormAttributeValue> values = this.formDataHelper.parseToFormAttributeValue(formDefine.getFormData(), request.getParameterMap());

		if (values == null) {
			log.warn("Can not convert any data.");
			return null;
		}

		FormEntry entry = new FormEntry();

		entry.setFormDefine(formDefine);
		entry.setValues(values);

		for (FormAttributeValue v : values) {
			v.setEntry(entry);
		}

		this.formEntrySrv.save(entry);

		return entry;
	}

	/**
	 * 移除表单数据
	 * 
	 * @param formEntryId
	 * @return
	 */
	public FormEntry deleteData(String formEntryId) {
		if (StringUtils.isBlank(formEntryId))
			return null;

		FormEntry entry = this.formEntrySrv.get(formEntryId);

		if (entry == null)
			return null;

		entry = this.formEntrySrv.delete(entry);

		return entry;
	}

	/**
	 * 修改表单数据
	 * 
	 * @param formEntryId
	 * @param formTitle
	 * @param request
	 * @return
	 */
	@Transactional
	public FormEntry updateData(String formEntryId, HttpServletRequest request) {
		if (StringUtils.isBlank(formEntryId)) {
			log.warn("Empty form entry id.");
			return null;
		}

		FormEntry entry = this.formEntrySrv.get(formEntryId);

		if (entry == null) {
			log.warn("Can not find the form entry by id: " + formEntryId);
			return null;
		}

		if (entry.getFormDefine() == null || StringUtils.isBlank(entry.getFormDefine().getId())) {
			log.error("The form define data are lost.");
			return null;
		}

		List<FormAttributeValue> values = this.formDataHelper.parseToFormAttributeValue(entry.getFormDefine().getFormData(), request.getParameterMap());

		if (entry.getValues() == null)
			entry.setValues(new ArrayList<FormAttributeValue>());

		// delete all attributes
		entry.getValues().clear();

		if (values == null)
			log.warn("Can not convert any data.");
		else {
			for (FormAttributeValue v : values) {
				entry.getValues().add(v);
				v.setEntry(entry);
			}
		}

		entry = this.formEntrySrv.update(entry);

		return entry;
	}

	/**
	 * 加载表单数据
	 * 
	 * @param formEntryId
	 * @param formDefineId
	 * @return
	 */
	public ArrayNode loadData(String formEntryId, String formDefineId) {
		if (StringUtils.isBlank(formEntryId) && StringUtils.isBlank(formDefineId)) {
			log.error("Requires at least one parameter of formEntryId and formDefineId.");
			return null;
		}

		if (StringUtils.isBlank(formEntryId)) {
			return this.loadData(this.formDefineSrv.get(formDefineId));
		}

		return this.loadData(this.formEntrySrv.get(formEntryId));
	}

	/**
	 * 加载表单数据
	 * 
	 * @param data
	 * @return
	 */
	public ArrayNode loadData(FormEntry data) {
		// 数据为空，或者未关联表单定义数据
		if (data == null || data.getFormDefine() == null)
			return null;

		ArrayNode json = this.formDataHelper.translateFormDataToArrayNode(data);

		if (json == null) {
			log.error("Translate data to json error.");
			return null;
		}

		return json;
	}

	/**
	 * 加载表单数据
	 * 
	 * @param data
	 * @return
	 */
	public ArrayNode loadData(FormDefine data) {
		if (data == null)
			return null;

		return this.formDataHelper.translateFormDefineJsonData(data.getFormData());
	}

}
