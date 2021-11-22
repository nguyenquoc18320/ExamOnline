package com.anhquoc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temporary_storage")
public class TemporaryStorage {
	@Id
	@Column(length = 64)
	private String subject;
	
	@Column(name = "code")
	private String code;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name ="modified_date")
	private Date modifiedDate;

	public TemporaryStorage() {
		
	}
	
	public TemporaryStorage(String subject, String code) {
		super();
		this.subject = subject;
		this.code = code;
	}

	public TemporaryStorage(String subject, String code, Date createdDate, Date modifiedDate) {
		super();
		this.subject = subject;
		this.code = code;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
