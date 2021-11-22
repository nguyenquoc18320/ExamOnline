package com.anhquoc.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserJoinCourseKey implements Serializable{
	@Column(name = "course_id")
	Long courseid;
	
	@Column(name = "user_id")
	Long userid;

	
	
	public UserJoinCourseKey() {
		super();
	}

	public UserJoinCourseKey(Long courseid, Long userid) {
		super();
		this.courseid = courseid;
		this.userid = userid;
	}

	public Long getCourseid() {
		return courseid;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			UserJoinCourseKey ob = (UserJoinCourseKey) obj;
			if(ob.getCourseid()==courseid && ob.getUserid()==this.userid) {
				return true;
			}
		}
		catch (Exception e) {
			return false;
		}
		return false;
	}
	
	
}
