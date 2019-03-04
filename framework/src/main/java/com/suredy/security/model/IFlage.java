package com.suredy.security.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 数据标志
 * 
 * @author VIVID.G
 * @since 2016-5-30
 * @version v0.1
 */
@JsonInclude(Include.NON_NULL)
public interface IFlage {

	public String getFlag();

}
