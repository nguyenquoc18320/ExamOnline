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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.api.pagination.OutPutPagination;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.JoinCourseRepository;
import com.anhquoc.service.impl.JoinCourseService;

@CrossOrigin
@RestController
public class JoinCourseAPI {
	
	@Autowired
	JoinCourseService joinCourseService;
	
	/*
	 * a user join a course
	 */
	@PutMapping(value="/join-course")
	public String joinCourse(@RequestParam("courseid") Long courseid, @RequestParam("userid") Long userid, @RequestBody List<String> emailList) {
		String result = joinCourseService.joinCourse(courseid, userid, emailList);
		return result;
	}
	
	/*
	 * get user list who joined a course
	 * can search with name
	 */
	@GetMapping(value="/join-course/get-list")
	public OutPutPagination<UserEntity> listUsersJoinedCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid, 
			@RequestParam(value="name", required = false, defaultValue = "") String name,
			@RequestParam("page") int page, @RequestParam("limit") int limit) {
		OutPutPagination<UserEntity> output = new OutPutPagination<UserEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("user.name"));

		List<UserEntity> users = new ArrayList<>();
		users = joinCourseService.getUsersJoinedCourse(userid, courseid, name, pageable);	
		output.setEntityList(users);
		output.setTotalPage((int) Math.ceil((float) joinCourseService.getUsersJoinedCourse(userid, courseid, name).size() / limit));
		return output;
	}
	
	/*
	 * delete a user from course
	 * owner id, who do delete func
	 */
	@PutMapping(value="/join-course/delete")
	public String deleteUserInCourse(@RequestParam("ownerid") Long ownerid, @RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid) {
		String result = joinCourseService.deleteUser(ownerid, userid, courseid);
		return result;
	}
	
//	/*
//	 * Search paticcipants in a course
//	 * userid: user who searches 
//	 */
//	@GetMapping(value="/join-course/search")
//	public OutPutPagination<UserEntity> searchPaticipantsWithName(@RequestParam("userid") Long ownerid, @RequestParam("")) {
//		
//	}
}
