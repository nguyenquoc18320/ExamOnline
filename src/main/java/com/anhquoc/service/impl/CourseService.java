package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.CourseRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.ICourseService;

@Service
public class CourseService implements ICourseService {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private UserRepository userRepository;

	public CourseEntity createCourse(CourseEntity course) {
		try {
			UserEntity user = course.getUser();

			// check whether the user exists
			user = userRepository.findOneById(user.getId());
			if (user == null) {
				return null;
			}

			// create new course
			course.setId(null);
//		course.setStatus(false);
			courseRepository.save(course);
			return course;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	@Override
	public List<CourseEntity> getCourseByUser(Long userid, Pageable pageable) {
		UserEntity user = userRepository.findOneById(userid);
		if (user == null) {
			return new ArrayList<CourseEntity>();
		}

		List<CourseEntity> courses = courseRepository.findAllByUser(user, pageable);
		return courses;
	}

	@Override
	public int totalCourseOfUser(Long userid) {
		UserEntity user = userRepository.findOneById(userid);

		return (int) courseRepository.findAllByUser(user).size();
	}

	/*
	 * update course: check whether the course belongs to user return update course
	 */
	@Override
	public CourseEntity update(CourseEntity course, Long userid) {
//		if(course.getId()==null) {
//			return null;
//		}
		CourseEntity currentCourse = courseRepository.findOneById(course.getId());

		// check whether the course exist
		if (currentCourse == null) {
			return null;
		}

		//check whether the course belongs to the user
		if (currentCourse.getUser().getId() != userid) {
			return null;
		}

		//update
		currentCourse.setName(course.getName());
		currentCourse.setDescription(course.getDescription());
		currentCourse.setStatus(course.getStatus());
		
		courseRepository.save(currentCourse);
		return currentCourse;
	}
	
	/*
	 * get a course with its id
	 * check whether the course belongs to the user
	 */
	@Override
	public CourseEntity getCourse(Long userid, Long courseid) {
		CourseEntity course = courseRepository.findOneById(courseid);

		// check whether the course exist
		if (course == null) {
			return null;
		}

		//check whether the course belongs to the user
		if (course.getUser().getId() != userid) {
			return null;
		}
		return course;
	}
	@Override
	public List<CourseEntity> getCourseByUserId(Long userid) {
		UserEntity user = userRepository.findOneById(userid);
		if (user == null) {
			return null;
		}

		List<CourseEntity> courses = courseRepository.findAllByUser(user);
		return courses;
	}
	@Override
	public CourseEntity getCourseByCourseId(Long courseid) {
		CourseEntity course = courseRepository.findOneById(courseid);
		if (course == null) {
			return null;
		}
		return course;
		
	}
	
}
