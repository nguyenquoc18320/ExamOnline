package com.anhquoc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy="course", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TestEntity> tests = new ArrayList<>();
        
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
	
	public List<TestEntity> getTests() {
		return tests;
	}

	public void setTests(List<TestEntity> tests) {
		this.tests = tests;
	}
	
}
