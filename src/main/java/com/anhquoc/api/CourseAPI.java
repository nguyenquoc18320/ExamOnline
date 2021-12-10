package com.anhquoc.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
//	@GetMapping("/user-course/{userid}")
//	public OutPutPagination<CourseEntity> getCourseByUserIDForPagination(@PathVariable("userid") Long userid,
//			@RequestParam(value="coursename", required = false, defaultValue = "") String coursename,
//			@RequestParam(value="status", required=false, defaultValue = "") String status, 
//			@RequestParam(value="blocked", required=false, defaultValue = "") String blocked,
//			@RequestParam("page") int page, @RequestParam("limit") int limit) {
//		
//		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
//		output.setPage(page);
//		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("name"));
//		
//		output.setEntityList(courseService.getCourseByUser(userid, pageable));
//		output.setTotalPage((int) Math.ceil((float) courseService.totalCourseOfUser(userid) / limit));
//
//		return output;
//	}

	/*
	 * search course of user by name
	 */
	@GetMapping("/course/search")
	public OutPutPagination<CourseEntity> getCourseOfUserByCourseName(@RequestParam("userid") Long userid,
			@RequestParam(value = "coursename", required = false, defaultValue = "") String courseName,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "blocked", required = false, defaultValue = "") String blocked,
			@RequestParam("page") int page, @RequestParam("limit") int limit) {
		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("name"));

		List<CourseEntity> courses = new ArrayList<CourseEntity>();
		try {
			// get with status and blocked
			if (!status.isEmpty() && !blocked.isEmpty()) {
				
				courses = courseService.getCoursesOfUser(userid, courseName, Boolean.parseBoolean(status),
						Boolean.parseBoolean(blocked), pageable);
				output.setTotalPage((int) Math.ceil((float) courseService.getCoursesOfUser(userid, courseName,
						Boolean.parseBoolean(status), Boolean.parseBoolean(blocked)).size() / limit));
				
			} else if (!status.isEmpty()) {// with status
				
				courses = courseService.getCoursesOfUserWithStatus(userid, courseName, Boolean.parseBoolean(status), pageable);
				output.setTotalPage((int) Math.ceil((float) courseService.getCoursesOfUserWithStatus(userid, courseName,
						Boolean.parseBoolean(status)).size() / limit));
				
			} else if (!blocked.isEmpty()) {// with blocked

				courses = courseService.getCoursesOfUserWithBlocked(userid, courseName, Boolean.parseBoolean(blocked), pageable);
				output.setTotalPage((int) Math.ceil((float) courseService.getCoursesOfUserWithBlocked(userid, courseName,
						Boolean.parseBoolean(blocked)).size() / limit));
				
			} else {
				
				courses = courseService.getCoursesOfUser(userid, courseName, pageable);
				output.setTotalPage(
						(int) Math.ceil((float) courseService.getCoursesOfUser(userid, courseName).size() / limit));
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		output.setEntityList(courses);

		return output;
	}

	
	/*
	 * Get list of courses of a user to use for pagination
	 */
//	@GetMapping("/user-course/{userid}")
//	public OutPutCouresPagination getCourseByUserIDForPagination(@PathVariable("userid") Long userid, @RequestParam("page") int page, @RequestParam("limit") int limit) {
//		OutPutCouresPagination output = new OutPutCouresPagination();
//		output.setPage(page);
//		Pageable pageable = PageRequest.of(page-1, limit);
//		output.setCourseList(courseService.getCourseByUser(userid, pageable));
//		output.setTotalPage((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
////		System.out.println((int)Math.ceil((float)courseService.totalCourseOfUser(userid)/limit));
//		return output;
//	}
	
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

				output.setTotalPage((int) Math.ceil(
						(float) courseService.getCourseByNameAndAuthor(courseName, authorName, null).size() / limit));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		output.setEntityList(courses);
//		output.setTotalPage((int) Math.ceil((float) courseService.getCourseOfUserByCourseName(userid, courseName).size() / limit));

		return output;
	}

	
	/*
	 * delete a course userid: person requires to delete the course
	 */
	@PutMapping("/course/delete")
	public String deleteCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid) {
		String result = "";
		result = courseService.deleteCourse(userid, courseid);

		return result;
	}
	
	/*
	 * block a course userid: person requires to delete the course
	 */
	@PutMapping("/course/block")
	public String blockCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid) {
		String result = "";
		result = courseService.blockCourse(userid, courseid);

		return result;
	}
	
	/*
	 * block a course userid: person requires to delete the course
	 */
	@PutMapping("/course/unblock")
	public String ublockCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid) {
		String result = "";
		result = courseService.unblockCourse(userid, courseid);

		return result;
	}

	
	@GetMapping("/course-by-user/{userid}")
	public List<CourseEntity> getCourseByUser(@PathVariable("userid") Long userid){
		return courseService.getCourseByUserId(userid);
	}
	
	@GetMapping("/course/{courseid}")
	public CourseEntity getCourseByCourseID(@PathVariable("courseid") Long courseid){		
		return courseService.getCourseByCourseId(courseid);
	}
	
	
	/*
	 * get public coures for user in home page
	 */
	@GetMapping("/course/public-courses")
	public OutPutPagination<CourseEntity> getPublicCourses(@RequestParam("userid") Long userid, 
			@RequestParam(value="courseName", required = false, defaultValue = "") String courseName,
			@RequestParam("page") int page, @RequestParam("limit") int limit){
		
		OutPutPagination<CourseEntity> output = new OutPutPagination<CourseEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("name"));

		List<CourseEntity> courses = new ArrayList<>();
		
		courses = courseService.getPublicCourses(userid, courseName, pageable);
		output.setEntityList(courses);
		
		output.setTotalPage((int) Math.ceil((float) courseService.getPublicCourses(userid, courseName).size() / limit));
		return output;
	}
	
	/*
	 * get course for admin
	 */
	@GetMapping("/admin")
	public CourseEntity getCourseForAdmin(@RequestParam("courseid") Long courseid) {
		CourseEntity course = courseService.getCourseForAdmin(courseid);		
		return course;
	}
}
