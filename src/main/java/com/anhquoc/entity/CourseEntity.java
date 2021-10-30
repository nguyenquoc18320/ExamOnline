package com.anhquoc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "course")
public class CourseEntity extends BaseEntity {
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="status")
	private boolean status;
	
    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private UserEntity user;

//    @Column(name="delete")
//    private boolean delete;
    
    public CourseEntity() {
    	
    }
    
	public CourseEntity(String name, String description, boolean status, UserEntity user) {
		super();
		this.name = name;
		this.description = description;
		this.status = status;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
}
