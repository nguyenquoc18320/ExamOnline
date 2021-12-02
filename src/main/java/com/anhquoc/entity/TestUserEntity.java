package com.anhquoc.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="test_user")
public class TestUserEntity {
	@EmbeddedId
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	TestUserKey id;
	
	@ManyToOne
	@MapsId("userid")
	@JoinColumn(name="userid")
	UserEntity user;
	
	@ManyToOne
	@MapsId("testid")
	@JoinColumn(name="testid")
	TestEntity test;
	
	
	@Column(name="end_time")
	private Date endTime;
	
	@Column(name="attempt")
	int attempt;
	
	@Column(name="score")
	float score;
	
	

	public TestUserEntity() {
		super();
		this.id = new TestUserKey();
	}

	public TestUserKey getId() {
		return id;
	}

	public void setId(TestUserKey id) {
		this.id = id;
	}

	public Date getStartTime() {
		return (Date) this.id.getStartTime();
	}
	
	public void setStartTime(Date now) {
		this.id.setStartTime(now);
	}
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}
	
	public void setTestId(Long testid) {
		this.id.setTestid(testid);
	}
	
	public void setUserid(Long userid) {
		this.id.setUserid(userid);
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public TestEntity getTest() {
		return test;
	}

	public void setTest(TestEntity test) {
		this.test = test;
	}
	
}
