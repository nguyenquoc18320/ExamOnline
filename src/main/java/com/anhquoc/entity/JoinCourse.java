package com.anhquoc.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="join_course")
public class JoinCourse {
	@EmbeddedId
	private UserJoinCourseKey id ;
	
	@ManyToOne
	@MapsId("userid")
	@JoinColumn(name="user_id")
	private UserEntity user;
	
	@ManyToOne
	@MapsId("courseid")
	@JoinColumn(name = "course_id")
	private CourseEntity course;
	
	@Column(name="status")
	private boolean status;

	public JoinCourse( ) {

	}
	
	public JoinCourse( UserEntity user, CourseEntity course, boolean status) {
		this.id = new UserJoinCourseKey(course.getId(), user.getId());
		this.user = user;
		this.course = course;
		this.status = status;
	}

	public UserJoinCourseKey getId() {
		return id;
	}

	public void setId(UserJoinCourseKey id) {
		this.id = id;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public CourseEntity getCourse() {
		return course;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
