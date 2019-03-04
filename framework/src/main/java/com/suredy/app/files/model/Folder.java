package com.suredy.app.files.model;

import java.io.Serializable;
import java.util.Date;

import com.suredy.app.files.entity.FolderEntity;

public class Folder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7488380145647812410L;

	private String id;

	private String name;

	private Integer sort;

	private Integer isPublic;

	private String parent;
	
	private Date creationTime;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sort
	 */
	public Integer getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	
	
	
	/**
	 * @return the creationTime
	 */
	public Date getCreationTime() {
		return creationTime;
	}

	
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * @return the isPublic
	 */
	public Integer getIsPublic() {
		return isPublic;
	}

	
	/**
	 * @param isPublic the isPublic to set
	 */
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}

	public FolderEntity toPO() {
		FolderEntity po = new FolderEntity();

		if (id != null) {
			po.setId(id);
		}
		po.setSort(sort);
		po.setName(name);
		po.setIsPublic(isPublic);
		po.setCreationTime(creationTime);
		if (parent != null) {
			FolderEntity pm = new FolderEntity();
			pm.setId(parent);
			po.setParent(pm);
		}
		return po;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Folder other = (Folder) o;

		if (id != null ? !id.equals(other.id) : other.id != null) {
			return false;
		}
		if (name != null ? !name.equals(other.name) : other.name != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return ((id != null ? id.hashCode() : 0) + (name != null ? name.hashCode() : 0));
	}

	@Override
	public String toString() {
		return "Folder[" + "id=" + id + ", name='" + name + "']";
	}

}
