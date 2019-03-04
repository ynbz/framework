package com.suredy.security.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.suredy.security.model.Unit;

@Entity
@Table(name = "T_E_ZZDY")
public class UnitEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6123018506783633526L;
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "ID_E_ZZDY", length = 32)
	private String id;
	
	@Column(name = "WYMC")
	private String uniqueCode; //UnitEntity Identification code
	
	@Column(name = "DM")
	private String code;
	
	@Column(name = "MC")
	private String name;
	
	@Column(name = "JC")
	private String alias;
	
	@Column(name = "PLXH")
	private Integer sort;
	
	@Column(name = "QC")
	private String fullName;
	
	@Column(name = "YXBZ", columnDefinition = "int default 1")
	private Integer available;
	
	@Column(name = "MS")
	private String description;
	
	@Column(name = "ID_SXWD")
	private String docId;
	

	@OneToMany(mappedBy = "unit", cascade={CascadeType.ALL}, targetEntity = UserEntity.class)  
	@OrderBy("sort, CONVERT_GBK(name)")
	@Fetch(FetchMode.SUBSELECT)
	private Set<UserEntity> associationUsers = new LinkedHashSet<UserEntity>();

	@OneToMany(mappedBy = "parent", cascade={CascadeType.ALL}, targetEntity = UnitEntity.class)
	@OrderBy("sort, CONVERT_GBK(name)")
	@Fetch(FetchMode.SUBSELECT)
	private Set<UnitEntity> children;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_ZZ")
	private OrgEntity org;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_ZZDY_SJ")
	private UnitEntity parent;
	
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
	 * @return the orgId
	 */
	public OrgEntity getOrg() {
		return org;
	}

	
	/**
	 * @param org the org to set
	 */
	public void setOrg(OrgEntity org) {
		this.org = org;
	}

	
	
	/**
	 * @return the parent
	 */
	public UnitEntity getParent() {
		return parent;
	}

	
	/**
	 * @param parent the parent to set
	 */
	public void setParent(UnitEntity parent) {
		this.parent = parent;
	}


	
	/**
	 * @return the children
	 */
	public Set<UnitEntity> getChildren() {
		return children;
	}


	
	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<UnitEntity> children) {
		this.children = children;
	}


	/**
	 * @return the uniqueCode
	 */
	public String getUniqueCode() {
		return uniqueCode;
	}

	
	/**
	 * @param uniqueCode the uniqueCode to set
	 */
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}

	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
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
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	
	/**
	 * @return the available
	 */
	public Integer getAvailable() {
		return available;
	}

	
	/**
	 * @param available the available to set
	 */
	public void setAvailable(Integer available) {
		this.available = available;
	}

	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * @return the docId
	 */
	public String getDocId() {
		return docId;
	}

	
	/**
	 * @param docId the docId to set
	 */
	public void setDocId(String docId) {
		this.docId = docId;
	}

	
	
	/**
	 * @return the associationUsers
	 */
	public Set<UserEntity> getAssociationUsers() {
		return associationUsers;
	}


	
	/**
	 * @param associationUsers the associationUsers to set
	 */
	public void setAssociationUsers(Set<UserEntity> associationUsers) {
		this.associationUsers = associationUsers;
	}

	@Transient
	public Unit toVO(){
		Unit vo = new Unit();
		vo.setAlias(alias);
		vo.setAvailable(available);
		vo.setCode(code);
		vo.setDescription(description);
		vo.setDocId(docId);
		vo.setFullName(fullName);
		vo.setId(id);
		vo.setName(name);
		vo.setOrgId(org == null ? null : org.getId());
		vo.setParentId(parent == null ? null : parent.getId());
		vo.setParentName(parent == null ? null : parent.getName());
		vo.setSort(sort);
		vo.setUniqueCode(uniqueCode);
		return vo;
	}
	
}
