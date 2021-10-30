package com.anhquoc.api;

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

import com.anhquoc.api.pagination.OutPutCouresPagination;
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
	public OutPutCouresPagination getCourseByUserIDForPagination(@PathVariable("userid") Long userid, @RequestParam("page") int page, @RequestParam("limit") int limit) {
		OutPutCouresPagination output = new OutPutCouresPagination();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page-1, limit);
		output.setCourseList(courseService.getCourseByUser(userid, pageable));
		output.setTotalPage((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
//		System.out.println((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
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
}
