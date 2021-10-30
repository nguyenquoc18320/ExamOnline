package com.anhquoc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anhquoc.entity.CourseEntity;

public interface ICourseService {
	public List<CourseEntity> getCourseByUser(Long userid, Pageable pageable);
	public int totalCourseOfUser(Long userid);
	public CourseEntity update(CourseEntity course, Long userid);
	public CourseEntity getCourse(Long userid, Long courseid);
}
