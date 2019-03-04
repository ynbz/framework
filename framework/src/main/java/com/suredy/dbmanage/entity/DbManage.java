package com.suredy.dbmanage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.suredy.core.model.BaseModel;

/**
 * 数据库备份对象
 * 
 * @author sdkj
 * @since 2017-2-16
 * @version v0.1
 */
@Entity
@Table(name = "tb_db_backups")
public class DbManage extends BaseModel {

	/* 上传时间 */
	@JsonIgnore
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "backups_time", nullable = false)
	private Date backupsTime;

	/* 数据库名 */
	@JsonIgnore
	@Column(name = "db_name", length = 500, nullable = false)
	private String dbName;

	/* 备份路径（相对路径） */
	@JsonIgnore
	@Column(name = "relative_backups_path", length = 500, nullable = false)
	private String relativeBackupsPath;

	/* 备份人 */
	@JsonIgnore
	@Column(name = "backups_person", length = 500, nullable = false)
	private String backupsPerson;

	public Date getBackupsTime() {
		return backupsTime;
	}

	public void setBackupsTime(Date backupsTime) {
		this.backupsTime = backupsTime;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getRelativeBackupsPath() {
		return relativeBackupsPath;
	}

	public void setRelativeBackupsPath(String relativeBackupsPath) {
		this.relativeBackupsPath = relativeBackupsPath;
	}

	public String getBackupsPerson() {
		return backupsPerson;
	}

	public void setBackupsPerson(String backupsPerson) {
		this.backupsPerson = backupsPerson;
	}

}
