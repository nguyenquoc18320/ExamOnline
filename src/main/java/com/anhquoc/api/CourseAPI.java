package com.anhquoc.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.api.pagination.OutPutPagination;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.service.impl.CourseService;

@CrossOrigin
@RestController
public class CourseAPI {
	@Autowired
	private CourseService courseService;

	@PostMapping("/course")
	public CourseEntity createCourse(@RequestBody CourseEntity course) {
		course = courseService.createCourse(course);
		return course;
	}

	/*
	 * Get list of courses of a user to use for pagination
	 */
	@GetMapping("/user-course/{userid}")
	public OutPutPagination<CourseEntity> getCourseByUserIDForPagination(@PathVariable("userid") Long userid,
			@RequestParam("page") int page, @RequestParam("limit") int limit) {
		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		output.setEntityList(courseService.getCourseByUser(userid, pageable));
		output.setTotalPage((int) Math.ceil((float) courseService.totalCourseOfUser(userid) / limit));
//		System.out.println((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
		return output;
	}

	/*
	 * search course of user by name
	 */
	@GetMapping("/course/search")
	public OutPutPagination<CourseEntity> getCourseOfUserByCourseName(@RequestParam("userid") Long userid,
			@RequestParam("page") int page, @RequestParam("limit") int limit,
			@RequestParam("coursename") String courseName) {
		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<CourseEntity> courses = courseService.getCourseOfUserByCourseName(userid, pageable, courseName);
		output.setEntityList(courses);
//		output.setTotalPage((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
		output.setTotalPage(
				(int) Math.ceil((float) courseService.getCourseOfUserByCourseName(userid, courseName).size() / limit));
		return output;
	}

	@GetMapping("/course")
	public CourseEntity getCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid) {
		CourseEntity course = courseService.getCourse(userid, courseid);
		return course;
	}

	/*
	 * Update course info
	 */
	@PutMapping("/course/{userid}")
	public CourseEntity update(@PathVariable("userid") Long userid, @RequestBody CourseEntity course) {
		CourseEntity updatedCourse = courseService.update(course, userid);
		return updatedCourse;
	}

	/*
	 * get courses with multiple conditions to filter, this function is used for
	 * admin conditions: userid, course name, author's name, status, blocked status
	 */
	@GetMapping("/admin/course")
	public OutPutPagination<CourseEntity> getCourseWithAdminRole(
			@RequestParam(value = "coursename", required = false, defaultValue = "") String courseName,
			@RequestParam(value = "authorname", required = false, defaultValue = "") String authorName,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "blocked", required = false, defaultValue = "") String blocked,
			@RequestParam("page") int page, @RequestParam("limit") int limit) {
		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);

		List<CourseEntity> courses = new ArrayList<>();
		try {
			// with status and blocked
			if (!status.isEmpty() && !blocked.isEmpty()) {
				courses = courseService.getCourse(courseName, authorName, Boolean.parseBoolean(status),
						Boolean.parseBoolean(blocked), pageable);

				output.setTotalPage((int) Math.ceil((float) courseService.getCourse(courseName, authorName,
						Boolean.parseBoolean(status), Boolean.parseBoolean(blocked), null).size() / limit));
			} else if (!status.isEmpty()) {
				courses = courseService.getCourseByNameAndAuthorAndStatus(courseName, authorName,
						Boolean.parseBoolean(status), pageable);

				output.setTotalPage((int) Math.ceil((float) courseService
						.getCourseByNameAndAuthorAndStatus(courseName, authorName, Boolean.parseBoolean(status), null)
						.size() / limit));
			} else if (!blocked.isEmpty()) {
				courses = courseService.getCourseByNameAndAuthorAndBlocked(courseName, authorName,
						Boolean.parseBoolean(blocked), pageable);

				output.setTotalPage((int) Math.ceil((float) courseService
						.getCourseByNameAndAuthorAndBlocked(courseName, authorName, Boolean.parseBoolean(blocked), null)
						.size() / limit));
			} else {
				courses = courseService.getCourseByNameAndAuthor(courseName, authorName, pageable);

				output.setTotalPage((int) Math.ceil((float) courseService
						.getCourseByNameAndAuthor(courseName, authorName,  null)
						.size() / limit));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		output.setEntityList(courses);
//		output.setTotalPage((int) Math.ceil((float) courseService.getCourseOfUserByCourseName(userid, courseName).size() / limit));

		return output;
	}
	

}
