package com.suredy.app.files.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.app.files.model.Folder;
import com.suredy.core.model.BaseModel;

@Entity
@Table(name = "T_APP_FMFOLDER")
public class FolderEntity extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8915401862239669246L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "SORT", columnDefinition = "int default 0", nullable = false)
	private int sort;
	

	@Column(name = "ISPUBLIC", columnDefinition = "int default 0") // 1--公共资源无需授权, 0--私密资源需要授权访问
	private Integer isPublic;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "CREATIONTIME", nullable = false)
	private Date creationTime;

	@OneToMany(mappedBy = "folder", cascade = {CascadeType.ALL}, targetEntity = UploadFileEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private List<UploadFileEntity> associationFiles = new ArrayList<UploadFileEntity>();

	@OneToMany(mappedBy = "folder", cascade = {CascadeType.ALL}, targetEntity = FolderPermissionEntity.class)
	@Fetch(FetchMode.SUBSELECT)
	private List<FolderPermissionEntity> associationPermissions = new ArrayList<FolderPermissionEntity>();

	@OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL})
	@OrderBy("sort")
	@Fetch(FetchMode.SUBSELECT)
	private List<FolderEntity> children;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "PARENTID")
	private FolderEntity parent;

	@Transient
	private String fullName;

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
	public int getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

	/**
	 * @return the parent
	 */
	public FolderEntity getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(FolderEntity parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<FolderEntity> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<FolderEntity> children) {
		this.children = children;
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
	 * @return the associationFiles
	 */
	public List<UploadFileEntity> getAssociationFiles() {
		return associationFiles;
	}
	/**
	 * @param associationFiles the associationFiles to set
	 */
	public void setAssociationFiles(List<UploadFileEntity> associationFiles) {
		this.associationFiles = associationFiles;
	}

	/**
	 * @return the associationPermissions
	 */
	public List<FolderPermissionEntity> getAssociationPermissions() {
		return associationPermissions;
	}

	/**
	 * @param associationPermissions the associationPermissions to set
	 */
	public void setAssociationPermissions(List<FolderPermissionEntity> associationPermissions) {
		this.associationPermissions = associationPermissions;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.name);
		FolderEntity p = this.getParent();
		while (p != null) {
			sb.append("/").append(p.getName());
			p = p.getParent();
		}
		return sb.substring(0);
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Transient
	public Folder toVO() {
		Folder vo = new Folder();
		vo.setId(id);
		vo.setName(name);
		vo.setIsPublic(isPublic);
		vo.setCreationTime(creationTime);
		vo.setSort(sort);
		vo.setParent(parent == null ? null : parent.getId());
		return vo;
	}

}
