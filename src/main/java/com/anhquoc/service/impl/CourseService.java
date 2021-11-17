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
			
			//check to satify that course name is unique
			CourseEntity tempCourse = courseRepository.findOneByUserAndNameAndDeleted(user, course.getName(), false);
			if(tempCourse != null) {
				return null;
			}

			// create new course
			course.setId(null);

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

		List<CourseEntity> courses = courseRepository.findAllByUserAndDeleted(user, false, pageable);
		return courses;
	}

	@Override
	public int totalCourseOfUser(Long userid) {
		UserEntity user = userRepository.findOneById(userid);

		return (int) courseRepository.findAllByUserAndDeleted(user, false).size();
	}

	/*
	 * update course: check whether the course belongs to user return update course
	 */
	@Override
	public CourseEntity update(CourseEntity course, Long userid) {
		try {
			CourseEntity currentCourse = courseRepository.findOneByIdAndBlockedAndDeleted(course.getId(), false, false);

			// check whether the course exist
			if (currentCourse == null) {
				return null;
			}

			// check whether the course belongs to the user
			if (currentCourse.getUser().getId() != userid) {
				return null;
			}
			
			//check to satify that course name is unique
			CourseEntity tempCourse = courseRepository.findOneByUserAndNameAndDeleted(currentCourse.getUser(), course.getName(), false);
			if(tempCourse != null && tempCourse.getId() != course.getId()) {
				return null;
			}

			// update
			currentCourse.setName(course.getName());
			currentCourse.setDescription(course.getDescription());
			currentCourse.setStatus(course.getStatus());

			courseRepository.save(currentCourse);
			return currentCourse;
		} catch (Exception ex) {
			return null;
		}
	}

	/*
	 * get a course with its id check whether the course belongs to the user
	 */
	@Override
	public CourseEntity getCourse(Long userid, Long courseid) {
		CourseEntity course = courseRepository.findOneByIdAndBlockedAndDeleted(courseid, false, false);

		UserEntity user = userRepository.getOne(userid);
		if (user == null) {
			return null;
		}

		// check whether the course exist
		if (course == null) {
			return null;
		}

		// check whether the course belongs to the user
		if (course.getUser().getId() != userid && user.getRole().getId() != 1) {
			return null;
		}
		return course;
	}

	/*
	 * get course of a user(with user id) by course name. return list course(with
	 * pagination)
	 */
	@Override
	public List<CourseEntity> getCourseOfUserByCourseName(Long userid, Pageable pageable, String courseName) {
		List<CourseEntity> courses = new ArrayList<>();
		UserEntity user = userRepository.getOne(userid);
		if (user == null) {
			return courses;
		}

//		courses = courseRepository.findByUserAndNameContaining(user, courseName, pageable);
//		courses = courseRepository.findByUserAndCourseName(user, courseName);
		courses = courseRepository.findCoursesWithUserAndCourseName(user, courseName, pageable);
		return courses;
	}

	/*
	 * get course of a user(with user id) by course name. return list course(without
	 * pagination)
	 */
	@Override
	public List<CourseEntity> getCourseOfUserByCourseName(Long userid, String courseName) {
		List<CourseEntity> courses = new ArrayList<>();
		UserEntity user = userRepository.getOne(userid);
		if (user == null) {
			return courses;
		}

		courses = courseRepository.findCoursesWithUserAndCourseName(user, courseName);
		return courses;
	}

	/*
	 * 
	 * get course with multiple conditions to filter with pagination or not
	 */
	@Override
	public List<CourseEntity> getCourse(String courseName, String authorName, boolean status, boolean blocked,
			Pageable pageable) {
		List<CourseEntity> courses = new ArrayList<>();

		if (pageable != null) {
			courses = courseRepository.findCourse(courseName, authorName, status, blocked, pageable);
		} else {
			courses = courseRepository.findCourse(courseName, authorName, status, blocked);
		}
		return courses;
	}

	/*
	 * 
	 * get course by course name and author's name
	 */
	@Override
	public List<CourseEntity> getCourseByNameAndAuthorAndStatus(String courseName, String authorName, boolean status,
			Pageable pageable) {
		List<CourseEntity> courses = new ArrayList<>();

		if (pageable != null) {
			courses = courseRepository.findCourseByNameAndAuthorAndStatus(courseName, authorName, status, pageable);
		} else {
			courses = courseRepository.findCourseByNameAndAuthorAndStatus(courseName, authorName, status);
		}
		return courses;
	}

	@Override
	public List<CourseEntity> getCourseByNameAndAuthorAndBlocked(String courseName, String authorName, boolean blocked,
			Pageable pageable) {
		List<CourseEntity> courses = new ArrayList<>();

		if (pageable != null) {
			courses = courseRepository.findCourseByNameAndAuthorAndBlocked(courseName, authorName, blocked, pageable);
		} else {
			courses = courseRepository.findCourseByNameAndAuthorAndBlocked(courseName, authorName, blocked);
		}
		return courses;
	}

	@Override
	public List<CourseEntity> getCourseByNameAndAuthor(String courseName, String authorName, Pageable pageable) {
		List<CourseEntity> courses = new ArrayList<>();

		if (pageable != null) {
			courses = courseRepository.findCourseByNameAndAuthor(courseName, authorName, pageable);
		} else {
			courses = courseRepository.findCourseByNameAndAuthor(courseName, authorName);
		}
		return courses;
	}
}
