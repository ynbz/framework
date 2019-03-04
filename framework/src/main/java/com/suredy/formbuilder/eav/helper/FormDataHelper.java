package com.suredy.formbuilder.eav.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.BooleanNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.suredy.formbuilder.design.constant.FormDefineDataKey;
import com.suredy.formbuilder.design.constant.FormItemType;
import com.suredy.formbuilder.design.helper.FormDefineDataHelper;
import com.suredy.formbuilder.eav.constant.DataType;
import com.suredy.formbuilder.eav.model.FormAttribute;
import com.suredy.formbuilder.eav.model.FormAttributeMetadata;
import com.suredy.formbuilder.eav.model.FormAttributeValue;
import com.suredy.formbuilder.eav.model.FormEntry;

@Service
public class FormDataHelper {
	private final Logger log = LoggerFactory.getLogger(FormDataHelper.class);

	@Autowired
	private FormDefineDataHelper formDefineDataHelper;

	/**
	 * 转换为form属性值对象
	 * 
	 * @param formDefineJson
	 * @param values
	 * @return
	 */
	public List<FormAttributeValue> parseToFormAttributeValue(String formDefineJson, Map<String, String[]> values) {
		if (StringUtils.isBlank(formDefineJson) || values == null || values.isEmpty()) {
			log.error("Invalid parameters. Empty form define json or form data.");
			return null;
		}

		ArrayNode formDefineData = this.formDefineDataHelper.parseFormDefineData(formDefineJson);

		if (formDefineData == null || formDefineData.size() <= 0) {
			log.warn("Empty form define data.");
			return null;
		}

		List<FormAttributeValue> data = new ArrayList<FormAttributeValue>();

		for (Map.Entry<String, String[]> entry : values.entrySet()) {
			String name = entry.getKey();
			String[] vals = entry.getValue();

			if (StringUtils.isBlank(name) || vals.length <= 0) {
				log.warn("Empty form field name or empty values.");
				continue;
			}

			// 前端组件针对数组值会增加一个方括号
			if (name.endsWith("[]")) {
				name = name.substring(0, name.length() - 2);

				if (name.length() == 0) {
					log.warn("Empty form field name.");
					continue;
				}
			}

			List<FormAttributeValue> tmp;

			try {
				tmp = this.convertToFormAttributeValue(formDefineData, name, vals);
			} catch (Exception e) {
				log.error("Create new form value error.", e);
				return null;
			}

			if (tmp == null || tmp.isEmpty()) {
				log.warn("Form field[" + name + "] does not matched.");
				continue;
			}

			data.addAll(tmp);
		}

		return data.isEmpty() ? null : data;
	}

	/**
	 * 翻译表单数据
	 * 
	 * @param data
	 * @return
	 */
	public ArrayNode translateFormDataToArrayNode(FormEntry data) {
		if (data == null || data.getFormDefine() == null) {
			log.error("Empty form data or form define data.");
			return null;
		}

		ArrayNode json = this.formDefineDataHelper.parseFormDefineData(data.getFormDefine().getFormData());

		if (json == null || json.size() <= 0) {
			log.warn("Empty form define data.");
			return null;
		}

		List<FormAttributeValue> forProcessData;

		if (data.getValues() == null) {
			log.warn("No form data can be loaded. User empty list instead.");
			forProcessData = new ArrayList<FormAttributeValue>();
		} else {
			forProcessData = new ArrayList<FormAttributeValue>(data.getValues()); // 使用新的对象，避免更改数据模型
		}

		for (JsonNode node : json) {
			try {
				this.loadDataIntoJsonFormItem(node, forProcessData);
			} catch (Exception e) {
				log.error("Load form data error.", e);
				return null;
			}
		}

		return json;
	}

	/**
	 * 只获取表单定义对象
	 * 
	 * @param json
	 * @return
	 */
	public ArrayNode translateFormDefineJsonData(String formDefineJson) {
		return this.formDefineDataHelper.parseFormDefineData(formDefineJson);
	}

	/**
	 * 创建表单值对象列表
	 * 
	 * @param formDefineData
	 * @param name
	 * @param values
	 * @return
	 * @throws Exception
	 */
	private List<FormAttributeValue> convertToFormAttributeValue(ArrayNode formDefineData, String name, String[] values) throws Exception {
		if (formDefineData == null || formDefineData.size() <= 0) {
			log.warn("Empty form define data.");
			return null;
		}

		if (StringUtils.isBlank(name) || values.length <= 0) {
			log.warn("Empty form field name or empty values.");
			return null;
		}

		// get form field defination
		ObjectNode field = this.formDefineDataHelper.itemWithNameInForm(formDefineData, name);

		// not defined in form
		if (field == null)
			return null;

		// convert
		List<FormAttributeValue> ret = this.parseItemAdapter(field, values);

		return ret == null || ret.isEmpty() ? null : ret;
	}

	/**
	 * 数据转换的类型适配
	 * 
	 * @param field
	 * @param values
	 * @return
	 * @throws Exception
	 */
	private List<FormAttributeValue> parseItemAdapter(ObjectNode field, String[] values) throws Exception {
		if (field == null || values == null || values.length <= 0)
			return null;

		FormItemType type = FormItemType.typeOf(this.formDefineDataHelper.stringValue(field, FormDefineDataKey.KEY_TYPE));

		switch (type) {
			case INPUT_TEXT:
			case INPUT_HIDDEN:
			case INPUT_CHECKBOX:
			case INPUT_RADIO:
			case INPUT_TEXT_DATE:
			case INPUT_FILE:
			case TEXTAREA:
				if (values.length != 1)
					throw new Exception("Text like form field only accept one value.");

				FormAttributeValue v = this.parseTextLikeItem(field, values[0], false);

				if (v == null)
					return null;

				return Arrays.asList(v);
			case INPUT_RADIO_GROUP:
			case SELECT:
				if (values.length != 1)
					throw new Exception("Text like form field only accept one value.");

				v = this.parseTextLikeItem(field, values[0], true);

				if (v == null)
					return null;

				return Arrays.asList(v);
			case INPUT_CHECKBOX_GROUP:
				return this.parseMultiSelectLikeItem(field, values);
			case BUTTON:
				break;
			default:
				throw new Exception("Invalid form item define.");
		}

		return null;
	}

	/**
	 * 转换单值的类型
	 * 
	 * @param field
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private FormAttributeValue parseTextLikeItem(ObjectNode field, String value, boolean allowEmpty) {
		if (field == null || value == null)
			return null;

		if ((!allowEmpty && (value = value.trim()).length() == 0))
			return null;

		FormAttributeValue fieldVal = this.newFormAttributevaValue(field);

		fieldVal.setValue(value);

		return fieldVal;
	}

	private List<FormAttributeValue> parseMultiSelectLikeItem(ObjectNode field, String[] values) throws Exception {
		ArrayNode children = this.formDefineDataHelper.value(field, FormDefineDataKey.KEY_VALUES, ArrayNode.class);

		List<String> cVals = new ArrayList<String>();

		for (int i = 0; i < children.size(); i++) {
			ObjectNode node = this.formDefineDataHelper.value(children, i, ObjectNode.class);

			if (node == null)
				throw new Exception("Invalid form define data.");

			String v = this.formDefineDataHelper.stringValue(node, FormDefineDataKey.KEY_VALUE);

			// empty value
			if (v == null)
				v = "";

			cVals.add(v);
		}

		List<FormAttributeValue> ret = new ArrayList<FormAttributeValue>();

		for (String val : values) {
			if (!cVals.contains(val))
				throw new Exception("Field data error. It is: " + field + "[" + val + "]");

			FormAttributeValue tmp = this.newFormAttributevaValue(field);

			tmp.setValue(val);

			ret.add(tmp);
		}

		return ret.isEmpty() ? null : ret;
	}

	private FormAttributeValue newFormAttributevaValue(ObjectNode field) {
		if (field == null)
			return null;

		DataType dataType = DataType.typeOf(this.formDefineDataHelper.stringValue(field, FormDefineDataKey.KEY_DATA_TYPE));
		dataType = dataType == null ? DataType.STRING : dataType;

		FormAttribute attribute = new FormAttribute();
		FormAttributeMetadata metadata = new FormAttributeMetadata();
		FormAttributeValue value = new FormAttributeValue();

		attribute.setName(this.formDefineDataHelper.stringValue(field, FormDefineDataKey.KEY_NAME));
		attribute.setMetadata(metadata);
		attribute.setValue(value);

		metadata.setDataType(dataType);
		metadata.setRequired(this.formDefineDataHelper.booleanValue(field, FormDefineDataKey.KEY_REQUIRED));
		metadata.setFormat(this.formDefineDataHelper.stringValue(field, FormDefineDataKey.KEY_FORMAT));
		metadata.setSearchable(this.formDefineDataHelper.booleanValue(field, FormDefineDataKey.KEY_SEARCHABLE));
		metadata.setAttribute(attribute);

		value.setValue(null);
		value.setEntry(null);
		value.setAttribute(attribute);

		return value;
	}

	/**
	 * 装载数据
	 * 
	 * @param node
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private ObjectNode loadDataIntoJsonFormItem(JsonNode node, List<FormAttributeValue> data) throws Exception {
		if (node == null)
			return null;

		if (!node.isObject())
			throw new Exception("Invalid Form define data.");

		ObjectNode n = (ObjectNode) node;

		if (data == null)
			return n;

		String fieldName = this.formDefineDataHelper.stringValue(n, FormDefineDataKey.KEY_NAME);

		if (StringUtils.isBlank(fieldName)) {
			log.warn("Empty form field attribute: name.");
			return n;
		}

		List<FormAttributeValue> itemData = new ArrayList<FormAttributeValue>();

		for (Iterator<FormAttributeValue> it = data.iterator(); it.hasNext();) {
			FormAttributeValue v = it.next();

			if (fieldName.equals(v.getAttribute().getName())) {
				itemData.add(v);
				it.remove();
			}
		}

		FormItemType type = FormItemType.typeOf(this.formDefineDataHelper.stringValue(n, FormDefineDataKey.KEY_TYPE));

		switch (type) {
			case INPUT_TEXT:
			case INPUT_HIDDEN:
			case INPUT_TEXT_DATE:
			case INPUT_FILE:
			case TEXTAREA:
				return this.loadTextLikeData(n, itemData);
			case INPUT_CHECKBOX:
			case INPUT_RADIO:
				return this.loadCheckboxLikeData(n, itemData);
			case INPUT_CHECKBOX_GROUP:
			case INPUT_RADIO_GROUP:
			case SELECT:
				return this.loadMultiSelectLikeItem(n, itemData);
			case BUTTON:
				break;
			default:
				throw new Exception("Invalid form item type.");
		}

		return null;
	}

	private ObjectNode loadTextLikeData(ObjectNode node, List<FormAttributeValue> itemData) {
		if (node == null || itemData == null)
			return node;

		// clean default data
		node.remove(FormDefineDataKey.KEY_VALUE);

		if (itemData.isEmpty())
			return node;

		FormAttributeValue value = itemData.get(0);

		if (StringUtils.isBlank(value.getValue()))
			return node;

		node.set(FormDefineDataKey.KEY_VALUE, new TextNode(value.getValue()));

		return node;
	}

	private ObjectNode loadCheckboxLikeData(ObjectNode node, List<FormAttributeValue> itemData) {
		if (node == null || itemData == null)
			return node;

		if (itemData.isEmpty())
			return node;

		FormAttributeValue value = itemData.get(0);

		if (StringUtils.isBlank(value.getValue()))
			return node;

		boolean selected = "on".equalsIgnoreCase(value.getValue());
		selected = selected || value.getValue().equals(this.formDefineDataHelper.value(node, FormDefineDataKey.KEY_VALUE, String.class));

		if (selected) {
			node.set(FormDefineDataKey.KEY_CHECKED, new TextNode(FormDefineDataKey.KEY_CHECKED));
		}

		return node;
	}

	private ObjectNode loadMultiSelectLikeItem(ObjectNode node, List<FormAttributeValue> itemData) throws Exception {
		if (node == null || itemData == null)
			return node;

		ArrayNode values = this.formDefineDataHelper.value(node, FormDefineDataKey.KEY_VALUES, ArrayNode.class);

		// no children items
		if (values == null || values.size() <= 0)
			return node;

		Map<String, List<ObjectNode>> maps = new HashMap<String, List<ObjectNode>>();

		// clean default data
		for (JsonNode item : values) {
			if (!item.isObject())
				throw new Exception("Invalid form define data.");

			ObjectNode tmp = (ObjectNode) item;

			tmp.remove(FormDefineDataKey.KEY_SELECTED);

			String v = this.formDefineDataHelper.stringValue(tmp, FormDefineDataKey.KEY_VALUE);

			if (v == null)
				v = "";

			if (!maps.containsKey(v))
				maps.put(v, new ArrayList<ObjectNode>());

			maps.get(v).add(tmp);
		}

		for (FormAttributeValue v : itemData) {
			String storeVal = v.getValue();

			if (storeVal == null)
				storeVal = "";

			List<ObjectNode> items = maps.get(storeVal);

			if (items == null || items.isEmpty())
				continue;

			BooleanNode val = BooleanNode.TRUE;

			for (ObjectNode item : items) {
				item.set(FormDefineDataKey.KEY_SELECTED, val);
			}
		}

		return node;
	}

}
