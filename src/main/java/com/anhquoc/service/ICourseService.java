package com.anhquoc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anhquoc.entity.CourseEntity;

public interface ICourseService {
	public List<CourseEntity> getCourseByUser(Long userid, Pageable pageable);
	public int totalCourseOfUser(Long userid);
	public CourseEntity update(CourseEntity course, Long userid);
	public CourseEntity getCourse(Long userid, Long courseid);
	public List<CourseEntity> getCourseOfUserByCourseName(Long userid, Pageable pageable, String courseName);
	public List<CourseEntity> getCourseOfUserByCourseName(Long userid, String courseName);
	public List<CourseEntity> getCourse(String courseName, String authorName, boolean status, boolean blocked, Pageable pageable);
	public List<CourseEntity> getCourseByNameAndAuthorAndStatus(String courseName, String authorName, boolean status, Pageable pageable);
	public List<CourseEntity> getCourseByNameAndAuthorAndBlocked(String courseName, String authorName,  boolean blocked, Pageable pageable);
	public List<CourseEntity> getCourseByNameAndAuthor(String courseName, String authorName, Pageable pageable);

}
