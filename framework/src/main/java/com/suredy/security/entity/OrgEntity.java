package com.suredy.security.entity;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.suredy.security.model.Org;

@Entity
@Table(name = "T_E_ZZ")
public class OrgEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3691190915130285619L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "ID_E_ZZ", length = 32)
	private String id;
	
	@Column(name = "WYMC")
	private String uniqueCode; //Organization Identification code
	
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

	@Column(name = "XTZZBZ", columnDefinition = "int default 0")
	private Integer buildIn;
	
	@Column(name = "YXBZ", columnDefinition = "int default 1")
	private Integer available;
	
	@Column(name = "MS")
	private String description;
	
	@Column(name = "ID_SXWD")
	private String docId;
	
	@OneToMany(mappedBy = "org", cascade={CascadeType.ALL}, targetEntity = UnitEntity.class)  
	@OrderBy("sort, CONVERT_GBK(name)")
	@Fetch(FetchMode.SUBSELECT)
	private Set<UnitEntity> associationUnits = new LinkedHashSet<UnitEntity>();
	
	@OneToMany(mappedBy = "org", cascade={CascadeType.ALL}, targetEntity = RoleEntity.class)  
	@Fetch(FetchMode.SUBSELECT)
	private Set<RoleEntity> associationRoles = new LinkedHashSet<RoleEntity>();
	
	@OneToMany(mappedBy = "org", cascade={CascadeType.ALL}, targetEntity = UserEntity.class)  
	@OrderBy("sort, CONVERT_GBK(name)")
	@Fetch(FetchMode.SUBSELECT)
	private Set<UserEntity> associationUsers = new LinkedHashSet<UserEntity>();
	

	
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
	 * @return the buildIn
	 */
	public Integer getBuildIn() {
		return buildIn;
	}

	
	/**
	 * @param buildIn the buildIn to set
	 */
	public void setBuildIn(Integer buildIn) {
		this.buildIn = buildIn;
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
	 * @return the associationUnits
	 */
	public Set<UnitEntity> getAssociationUnits() {
		return associationUnits;
	}


	
	/**
	 * @param associationUnits the associationUnits to set
	 */
	public void setAssociationUnits(Set<UnitEntity> associationUnits) {
		this.associationUnits = associationUnits;
	}


	
	/**
	 * @return the associationRoles
	 */
	public Set<RoleEntity> getAssociationRoles() {
		return associationRoles;
	}


	
	/**
	 * @param associationRoles the associationRoles to set
	 */
	public void setAssociationRoles(Set<RoleEntity> associationRoles) {
		this.associationRoles = associationRoles;
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
	public Org toVO(){
		Org  vo = new Org();
		vo.setAlias(alias);
		vo.setAvailable(available);
		vo.setBuildIn(buildIn);
		vo.setCode(code);
		vo.setDescription(description);
		vo.setDocId(docId);
		vo.setFullName(fullName);
		vo.setId(id);
		vo.setName(name);
		vo.setSort(sort);
		vo.setUniqueCode(uniqueCode);
		return vo;
	}
	
}
