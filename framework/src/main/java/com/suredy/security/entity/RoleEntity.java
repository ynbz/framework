package com.suredy.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.suredy.security.model.Role;

@Entity
@Table(name = "T_E_QZ")
public class RoleEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7358875239031159902L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "ID_E_QZ", length = 32)
	private String id;
	
	@Column(name = "WYMC")
	private String uniqueCode; //role Identification code
	
	@Column(name = "DM")
	private String code;
	
	@Column(name = "MC")
	private String name;
	
	@Column(name = "JC")
	private String alias;

	@Column(name = "PLXH")
	private Integer sort;
	
	@Column(name = "YXBZ", columnDefinition = "int default 1")
	private Integer available;
	

	@Column(name = "XTQZBZ", columnDefinition = "int default 0")
	private Integer buildIn;
	
	@Column(name = "YJBZ", columnDefinition = "int default 0")
	private Integer isMailGroup;
	
	@Column(name = "FYHQZBZ", columnDefinition = "int default 0")
	private Integer notUserGroup;
	
	@Column(name = "MS")
	private String description;
	
	
	@Column(name = "ID_SXWD")
	private String docId;

	
	@Column(name = "ID_E_YY", length = 32)
	private String appId;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_ZZ")
	private OrgEntity org;
	
	@OneToMany(mappedBy = "role", cascade={CascadeType.ALL}, targetEntity = User2RoleEntity.class)  
	@Fetch(FetchMode.SUBSELECT)
	private Set<User2RoleEntity> associationUser2Roles = new HashSet<User2RoleEntity>();

	@OneToMany(mappedBy = "role", cascade={CascadeType.ALL}, targetEntity = Role2PermissionEntity.class)  
	@Fetch(FetchMode.SUBSELECT)
	private Set<Role2PermissionEntity> associationRole2Permissions = new HashSet<Role2PermissionEntity>();
	
	
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
	 * @return the isMailGroup
	 */
	public Integer getIsMailGroup() {
		return isMailGroup;
	}

	
	/**
	 * @param isMailGroup the isMailGroup to set
	 */
	public void setIsMailGroup(Integer isMailGroup) {
		this.isMailGroup = isMailGroup;
	}

	
	/**
	 * @return the notUserGroup
	 */
	public Integer getNotUserGroup() {
		return notUserGroup;
	}

	
	/**
	 * @param notUserGroup the notUserGroup to set
	 */
	public void setNotUserGroup(Integer notUserGroup) {
		this.notUserGroup = notUserGroup;
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
	 * @return the orgId
	 */
	public OrgEntity getOrg() {
		return org;
	}

	
	/**
	 * @param orgId the orgId to set
	 */
	public void setOrg(OrgEntity org) {
		this.org = org;
	}

	
	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	
	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	


	
	/**
	 * @return the associationUser2Roles
	 */
	public Set<User2RoleEntity> getAssociationUser2Roles() {
		return associationUser2Roles;
	}


	
	/**
	 * @param associationUser2Roles the associationUser2Roles to set
	 */
	public void setAssociationUser2Roles(Set<User2RoleEntity> associationUser2Roles) {
		this.associationUser2Roles = associationUser2Roles;
	}


	
	/**
	 * @return the associationRole2Permissions
	 */
	public Set<Role2PermissionEntity> getAssociationRole2Permissions() {
		return associationRole2Permissions;
	}


	
	/**
	 * @param associationRole2Permissions the associationRole2Permissions to set
	 */
	public void setAssociationRole2Permissions(Set<Role2PermissionEntity> associationRole2Permissions) {
		this.associationRole2Permissions = associationRole2Permissions;
	}

	@Transient
	public Role toVO(){
		Role vo = new Role();
		vo.setAlias(alias);
		vo.setAppId(appId);
		vo.setAvailable(available);
		vo.setBuildIn(buildIn);
		vo.setCode(code);
		vo.setDescription(description);
		vo.setId(id);
		vo.setDocId(docId);
		vo.setIsMailGroup(isMailGroup);
		vo.setName(name);
		vo.setOrgId(org == null ? null : org.getId());
		vo.setSort(sort);
		vo.setOrgName(org == null ? null : org.getName());
		vo.setOrgUniqueCode(org == null ? null : org.getUniqueCode());
		vo.setUniqueCode(uniqueCode);
		return vo;
	}
}
