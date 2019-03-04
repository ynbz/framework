package com.suredy.eav.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.suredy.core.model.BaseModel;

/**
 * EAV模型之实体对象
 * 
 * @author VIVID.G
 * @since 2017-3-3
 * @version v0.1
 */
@MappedSuperclass
public abstract class EAVEntry<V> extends BaseModel {

	private static final long serialVersionUID = 1L;

	/* 表单属性 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "entry", orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	private List<V> values;

	public List<V> getValues() {
		return values;
	}

	public void setValues(List<V> values) {
		this.values = values;
	}

}
