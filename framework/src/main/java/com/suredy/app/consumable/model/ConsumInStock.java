package com.suredy.app.consumable.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.suredy.core.model.BaseModel;

/**
 * 耗材入库记录Model（表）
 * @author sdkj
 *
 */
@Entity
@Table(name="t_app_consumableInStock")
public class ConsumInStock  extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2605130189835844718L;
	
	private String consumableId; //耗材id
	private String stockNum;//入库数量
	private String operationId;//操作人id
	private String operationName;//操作人姓名
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Column(name = "inStocktime")
	private Date inStocktime;//入库时间
	
	private String comm;
	public String getConsumableId() {
		return consumableId;
	}
	public void setConsumableId(String consumableId) {
		this.consumableId = consumableId;
	}
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public Date getInStocktime() {
		return inStocktime;
	}
	public void setInStocktime(Date inStocktime) {
		this.inStocktime = inStocktime;
	}
	public String getComm() {
		return comm;
	}
	public void setComm(String comm) {
		this.comm = comm;
	}
	
	
	

}
