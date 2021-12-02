package com.anhquoc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.UserEntity;

public interface IJoinCourseService {
	public String joinCourse(Long courseid, Long userid, List<String> emailList);
	public List<UserEntity> getUsersJoinedCourse(Long ownerid, Long courseid, String participantName, Pageable pageable);
	public List<UserEntity> getUsersJoinedCourse(Long ownerid, Long courseid, String participantName);
	public String deleteUser(Long ownerid, Long userid, Long couresid);
	public List<CourseEntity> getCoursesByUser(Long userid, Pageable pageable);
	public List<CourseEntity> getCoursesByUser(Long userid);
}
