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

import org.hibernate.annotations.GenericGenerator;

import com.suredy.security.model.User;

@Entity
@Table(name = "T_E_YH")
public class UserEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6379714772597470702L;
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "ID_E_YH", length = 32)
	private String id;
	
	@Column(name = "WYMC_ZZDY")
	private String unitUC; //unit Identification code
		
	@Column(name = "WYMC")
	private String uniqueCode; //user Identification code
	
	@Column(name = "DM")
	private String code;
	
	@Column(name = "MC")
	private String name;
	
	@Column(name = "SHORTPINYIN")
	private String shortPinyin;
	
	@Column(name = "JC")
	private String alias;
	
	@Column(name = "ZW")
	private String title;

	@Column(name = "PLXH")
	private Integer sort;
	
	@Column(name = "QC")
	private String fullName;
	
	@Column(name = "JKCZLX", columnDefinition = "int default 0")
	private Integer actionType;
	
	@Column(name = "YXBZ", columnDefinition = "int default 0")
	private Integer available;
	
	@Column(name = "DMC")
	private String dominoName;

	
	@Column(name = "YJMC")
	private String email;
	
	@Column(name = "YJYHBZ", columnDefinition = "int default 0")
	private Integer isMailUser;
	

	@Column(name = "SSXT")
	private String SSXT;
	
	@Column(name = "KL")
	private String password;
	
	
	@Column(name = "MS")
	private String description;
	
	@Column(name = "ID_SXWD")
	private String docId;
	
	@Column(name = "ID_E_YH_ZS", length = 32)
	private String actUserId;
	
	@Column(name="IsEmployee")
	private Integer isEmployee;
	
	@Column(name="IsLongUser")
	private Integer isLongUser;
	
	@Column(name="Phone")
	private String userphone;
	
	
	@OneToMany(mappedBy = "user", cascade={CascadeType.ALL}, targetEntity = User2RoleEntity.class)  
	private Set<User2RoleEntity> associationUser2Roles = new HashSet<User2RoleEntity>();

	@OneToMany(mappedBy = "user", cascade={CascadeType.ALL}, targetEntity = User2PermissionEntity.class)  
	private Set<User2PermissionEntity> associationUser2Permissions = new HashSet<User2PermissionEntity>();
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_ZZDY")
	private UnitEntity unit;

	
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ID_E_ZZ")
	private OrgEntity org;
	
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
	 * @return the unitUC
	 */
	public String getUnitUC() {
		return unitUC;
	}

	
	/**
	 * @param unitUC the unitUC to set
	 */
	public void setUnitUC(String unitUN) {
		this.unitUC = unitUN;
	}

	
	
	
	/**
	 * @return the buildIn
	 */
	public String getSSXT() {
		return SSXT;
	}


	
	/**
	 * @param buildIn the buildIn to set
	 */
	public void setSSXT(String buildIn) {
		this.SSXT = buildIn;
	}


	/**
	 * @return the UnitEntity
	 */
	public UnitEntity getUnit() {
		return unit;
	}

	
	/**
	 * @param UnitEntity the UnitEntity to set
	 */
	public void setUnit(UnitEntity unit) {
		this.unit = unit;
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
	 * @return the shortPinyin
	 */
	public String getShortPinyin() {
		return shortPinyin;
	}


	
	/**
	 * @param shortPinyin the shortPinyin to set
	 */
	public void setShortPinyin(String shortPinyin) {
		this.shortPinyin = shortPinyin;
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
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
	 * @return the actionType
	 */
	public Integer getActionType() {
		return actionType;
	}

	
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
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
	 * @return the dominoName
	 */
	public String getDominoName() {
		return dominoName;
	}

	
	/**
	 * @param dominoName the dominoName to set
	 */
	public void setDominoName(String dominoName) {
		this.dominoName = dominoName;
	}

	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/**
	 * @return the isMailUser
	 */
	public Integer getIsMailUser() {
		return isMailUser;
	}

	
	/**
	 * @param isMailUser the isMailUser to set
	 */
	public void setIsMailUser(Integer isMailUser) {
		this.isMailUser = isMailUser;
	}

	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the actUserId
	 */
	public String getActUserId() {
		return actUserId;
	}

	
	/**
	 * @param actUserId the actUserId to set
	 */
	public void setActUserId(String actUserId) {
		this.actUserId = actUserId;
	}

	
	/**
	 * @return the orgId
	 */
	public OrgEntity getOrg() {
		return org;
	}

	
	/**
	 * @param org the Org to set
	 */
	public void setOrg(OrgEntity org) {
		this.org = org;
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
	 * @return the associationUser2Permissions
	 */
	public Set<User2PermissionEntity> getAssociationUser2Permissions() {
		return associationUser2Permissions;
	}


	
	
	public Integer getIsEmployee() {
		return isEmployee;
	}


	
	public void setIsEmployee(Integer isEmployee) {
		this.isEmployee = isEmployee;
	}

	

	
	

	
	public Integer getIsLongUser() {
		return isLongUser;
	}


	
	public void setIsLongUser(Integer isLongUser) {
		this.isLongUser = isLongUser;
	}


	
	
	public String getUserphone() {
		return userphone;
	}


	
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}


	/**
	 * @param associationUser2Permissions the associationUser2Permissions to set
	 */
	public void setAssociationUser2Permissions(Set<User2PermissionEntity> associationUser2Permissions) {
		this.associationUser2Permissions = associationUser2Permissions;
	}

	@Transient
	public User toVO(){
		User vo = new User();
		vo.setActionType(actionType);
		vo.setActUserId(actUserId);
		vo.setAlias(alias);
		vo.setAvailable(available);
		vo.setCode(code);
		vo.setDescription(description);
		vo.setDocId(docId);
		vo.setEmail(email);
		vo.setFullName(fullName);
		vo.setId(id);
		vo.setIsMailUser(isMailUser);
		vo.setName(name);
		vo.setShortPinyin(shortPinyin);
		vo.setPassword(password);
		vo.setSort(sort);
		vo.setTitle(title);
		vo.setUniqueCode(uniqueCode);
		vo.setUnitUniqueCode(unitUC);
		vo.setOrgId(org == null ? null : org.getId());
		vo.setOrgName(org == null ? null : org.getName());
		vo.setUnitId(unit == null ? null : unit.getId());
		vo.setUnitName(unit == null ? null : unit.getName());
		vo.setFullUnitName(unit == null ? null : unit.getFullName());
		vo.setIsEmployee(isEmployee);
		vo.setIsLongUser(isLongUser);
		vo.setUserphone(userphone);
		return vo;
	}

}
