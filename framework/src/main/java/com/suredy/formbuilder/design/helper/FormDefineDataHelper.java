package com.suredy.formbuilder.design.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.suredy.formbuilder.design.constant.FormDefineDataKey;

/**
 * form数据工具类
 * 
 * @author VIVID.G
 * @since 2017-3-1
 * @version v0.1
 */
@Service
public class FormDefineDataHelper {
	private final Logger log = LoggerFactory.getLogger(FormDefineDataHelper.class);

	/* json工具类 */
	private final ObjectMapper JSON = new ObjectMapper();

	public ArrayNode parseFormDefineData(String formDefineJson) {
		if (StringUtils.isBlank(formDefineJson)) {
			log.warn("Empty form define json data. Use empty array instead.");
			formDefineJson = "[]";
		}

		try {
			JsonNode node = this.JSON.readTree(formDefineJson);

			if (!node.isArray())
				throw new Exception("Invalid form define data. It is not an json arry data.");

			return (ArrayNode) node;
		} catch (Exception e) {
			log.error("Parse json data error. Use empty array instead.", e);
			try {
				return this.JSON.readValue("[]", ArrayNode.class);
			} catch (Exception e1) {
			}
		}

		return null;
	}

	/**
	 * 获取定义表单元素项<br>
	 * 这里根据表单域的名称获取，前端生成的表单域中的name应该保持唯一<br>
	 * 后台不对元素的唯一性进行校验，以保证代码逻辑的简洁
	 * 
	 * @param formDefineData
	 * @param nameValue
	 * @return
	 * @throws Exception
	 */
	public ObjectNode itemWithNameInForm(ArrayNode formDefineData, String nameValue) throws Exception {
		if (StringUtils.isBlank(nameValue))
			return null;

		List<ObjectNode> nodes = this.itemWithKeyAndValueInForm(formDefineData, FormDefineDataKey.KEY_NAME, nameValue);

		if (nodes == null || nodes.isEmpty())
			return null;

		return nodes.get(0);
	}

	/**
	 * 查找包含指定属性的表单元素
	 * 
	 * @param formDefineData
	 * @param fieldKey
	 * @return
	 * @throws Exception
	 */
	public List<ObjectNode> itemWithKeyInForm(ArrayNode formDefineData, String fieldKey) throws Exception {
		if (formDefineData == null)
			throw new Exception("Null form define data.");

		if (StringUtils.isBlank(fieldKey))
			return null;

		List<ObjectNode> ret = new ArrayList<ObjectNode>();

		for (int i = 0; i < formDefineData.size(); i++) {
			ObjectNode node = this.value(formDefineData, i, ObjectNode.class);

			if (node == null)
				throw new Exception("Invalid form define data.");

			if (!node.has(fieldKey))
				continue;

			ret.add(node);
		}

		return ret.isEmpty() ? null : ret;
	}

	/**
	 * 查找指定属性和值的表单元素
	 * 
	 * @param formDefineData
	 * @param fieldKey
	 * @return
	 * @throws Exception
	 */
	public List<ObjectNode> itemWithKeyAndValueInForm(ArrayNode formDefineData, String fieldKey, Object val) throws Exception {
		if (val == null)
			return null;

		List<ObjectNode> nodes = this.itemWithKeyInForm(formDefineData, fieldKey);

		if (nodes == null || nodes.isEmpty())
			return null;

		List<ObjectNode> ret = new ArrayList<ObjectNode>();

		for (ObjectNode n : nodes) {
			if (!val.equals(this.stringValue(n, fieldKey)))
				continue;

			ret.add(n);
		}

		return ret.isEmpty() ? null : ret;
	}

	public Integer integerValue(ObjectNode node, String key) {
		return this.value(node, key, Integer.class);
	}

	public Float floatValue(ObjectNode node, String key) {
		return this.value(node, key, Float.class);
	}

	public Double doubleValue(ObjectNode node, String key) {
		return this.value(node, key, Double.class);
	}

	public String stringValue(ObjectNode node, String key) {
		return this.value(node, key, String.class);
	}

	public Boolean booleanValue(ObjectNode node, String key) {
		return this.value(node, key, Boolean.class);
	}

	public <T> T value(ObjectNode node, String key, Class<T> clazz) {
		if (node == null || StringUtils.isBlank(key)) {
			log.warn("Invalid parameters. Null object node or empty key.");
			return null;
		}

		if (clazz == null) {
			log.warn("Invalid class template.");
			return null;
		}

		return this.valueAs(node.get(key), clazz);
	}

	public <T> T value(ArrayNode node, int index, Class<T> clazz) {
		if (node == null || index < 0) {
			log.warn("Invalid parameters. Null object node or empty key.");
			return null;
		}

		if (clazz == null) {
			log.warn("Invalid class template.");
			return null;
		}

		return this.valueAs(node.get(index), clazz);
	}

	@SuppressWarnings("unchecked")
	private <T> T valueAs(JsonNode node, Class<T> clazz) {
		if (node == null || node.isNull()) {
			log.warn("Null json node or null value.");
			return null;
		}

		if (clazz == null) {
			log.warn("Invalid class template.");
			return null;
		}

		if (clazz == Integer.class) {
			return (T) this.asInteger(node.asText());
		}

		if (clazz == Float.class) {
			return (T) this.asFloat(node.asText());
		}

		if (clazz == Double.class) {
			return (T) this.asDouble(node.asText());
		}

		if (clazz == String.class) {
			return (T) node.asText();
		}

		if (clazz == Boolean.class) {
			return (T) this.asBoolean(node.asText());
		}

		if (clazz == ArrayNode.class && node.isArray() || clazz == ObjectNode.class && node.isObject()) {
			return (T) node;
		}

		log.error("Error, It exceed my mind.");

		return null;
	}

	private Integer asInteger(String val) {
		if (StringUtils.isBlank(val))
			return null;

		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Float asFloat(String val) {
		if (StringUtils.isBlank(val))
			return null;

		try {
			return Float.parseFloat(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Double asDouble(String val) {
		if (StringUtils.isBlank(val))
			return null;

		try {
			return Double.parseDouble(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Boolean asBoolean(String val) {
		if (StringUtils.isBlank(val))
			return null;

		val = val.trim();

		if (!ArrayUtils.contains(new String[] {"true", "false", "0", "1"}, val))
			return null;

		if ("0".equals(val))
			return false;

		if ("1".equals(val))
			return true;

		try {
			return Boolean.parseBoolean(val);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return null;
	}

}
