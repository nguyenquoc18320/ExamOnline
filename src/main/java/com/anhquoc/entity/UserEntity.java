package com.anhquoc.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserEntity extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private String gender;

	@Column(name = "dateofbirth")
	private Date dateOfBirth;

	// @Column(name = "email", unique = true, nullable = false)
	// private String email;

	
	@Column(length = 64, name = "email", unique = true, nullable = false)
	private String email;
	
	@Column(name = "image")
	private String image;
	
	@Column(name="status")
	private boolean status;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//	    @JsonIgnoreProperties()
	private List<CourseEntity> courses = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "roleid", nullable = false)
	private RoleEntity role;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	List<JoinCourse> joinList = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "Answer", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<QuestionEntity> questions = new ArrayList<>();
	
	@OneToMany(mappedBy="user",fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private List<TestUserEntity> testUser;
	
	

	@Override
	public int compareTo(UserEntity e) {
		return this.getName().compareTo(e.getName());
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CourseEntity> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<QuestionEntity> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionEntity> questions) {
		this.questions = questions;
	}

	public List<JoinCourse> getJoinList() {
		return joinList;
	}

	public void setJoinList(List<JoinCourse> joinList) {
		this.joinList = joinList;
	}

	public List<TestUserEntity> getTestUser() {
		return testUser;
	}

	public void setTestUser(List<TestUserEntity> testUser) {
		this.testUser = testUser;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
}
