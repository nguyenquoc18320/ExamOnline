package com.anhquoc.api;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

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
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.service.impl.CourseService;
import com.anhquoc.service.impl.TestService;

 

@CrossOrigin
@RestController
public class TestAPI {

	@Autowired
	private TestService testService;
	
	@PostMapping("/test")
	public TestEntity createTest(@RequestBody TestEntity test) {
		test = testService.createTest(test);
		return test;
	}
	
	@GetMapping("/test-course/{courseid}")
	public List<TestEntity> getTestByCourse(@PathVariable("courseid") Long courseid){
		return testService.getTestByCourse(courseid);
	}
	
	@GetMapping("/test/{testid}")
	public TestEntity getTesteByTestId(@PathVariable("testid") Long testid){		
		return testService.getTesteByTestId(testid);
	}
	
	@PutMapping("/test/{testid}")
	public TestEntity updateTest(@PathVariable("testid") Long testid, @RequestBody TestEntity test) {
		test.setId(testid);
		TestEntity updatedTest = testService.updateTest(test);
		return updatedTest;
	}
	@PutMapping("/delete-test/{courseid}/{testid}")
	public TestEntity deleteTest(@PathVariable("courseid") Long courseid, @PathVariable("testid") Long testid) {
		return testService.deleteTest(courseid, testid);
	}
	
	/*
	 * get tests of a course which a user joined
	 */
	@GetMapping(value = "/test/joined")
	public OutPutPagination<TestEntity> getTestsOfJoinedCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid,
			@RequestParam("page") int page, @RequestParam("limit") int limit){
		
		OutPutPagination<TestEntity> output = new OutPutPagination<TestEntity>();
		output.setPage(page);
		
		Pageable pageble = PageRequest.of(page-1, limit);
		
		List<TestEntity> tests = new ArrayList<TestEntity>();
		try {
			tests = testService.getTestsOfJoinedCourse(userid, courseid, pageble);
			output.setTotalPage((int) Math.ceil((float) testService.getTestsOfJoinedCourse(userid, courseid).size() / limit));
		}catch (Exception e) {
			
		}
		
		output.setEntityList(tests);
		return output;
	}
	
	/*
	 * get test not done
	 */
	@GetMapping(value = "/test/joined-not-done")
	public OutPutPagination<TestEntity> getNotDoneTestsOfJoinedCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid,
			@RequestParam("page") int page, @RequestParam("limit") int limit){
		
		OutPutPagination<TestEntity> output = new OutPutPagination<TestEntity>();
		output.setPage(page);
		
		Pageable pageble = PageRequest.of(page-1, limit);
		
		List<TestEntity> tests = new ArrayList<TestEntity>();
		try {
			tests = testService.getNotDoneTestsOfJoinedCourse(userid, courseid, pageble);
			output.setTotalPage((int) Math.ceil((float) testService.getNotDoneTestsOfJoinedCourse(userid, courseid).size() / limit));
		}catch (Exception e) {
			
		}
		
		output.setEntityList(tests);
		return output;
	}
	
	/*
	 * get test done
	 */
	@GetMapping(value = "/test/joined-done")
	public OutPutPagination<TestEntity> getDoneTestsOfJoinedCourse(@RequestParam("userid") Long userid, @RequestParam("courseid") Long courseid,
			@RequestParam("page") int page, @RequestParam("limit") int limit){
		
		OutPutPagination<TestEntity> output = new OutPutPagination<TestEntity>();
		output.setPage(page);
		
		Pageable pageble = PageRequest.of(page-1, limit);
		
		List<TestEntity> tests = new ArrayList<TestEntity>();
		try {
			tests = testService.getDoneTestsOfJoinedCourse(userid, courseid, pageble);
			output.setTotalPage((int) Math.ceil((float) testService.getDoneTestsOfJoinedCourse(userid, courseid).size() / limit));
		}catch (Exception e) {
			
		}
		
		output.setEntityList(tests);
		return output;
	}
	
	
	/*
	 * get test for other users
	 */
	@GetMapping(value="/test/attend")
	public TestEntity getTest(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid) {
		TestEntity test = testService.getTestForAttend(testid, userid);
	
		return test;
	}
	
	/*
	 * when starting test, set start-time and end-time for test, but score set -1
	 * 
	 * if while doing test but it outs, you will get test with score -1 but still have time to do test.
	 */
	@GetMapping(value="/test/starting")
	public TestEntity getTestForAttend(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid) {
		TestEntity test = testService.getTestUserAttending(testid, userid);
		return test;
	}
	@PutMapping("/change-status-test/{courseid}/{testid}")
	public TestEntity statusTest(@PathVariable("courseid") Long courseid, @PathVariable("testid") Long testid) {
		return testService.statusTest(courseid, testid);
	}
	@GetMapping("/test-by-course/{courseid}")
	public List<TestEntity> getCourseByUser(@PathVariable("courseid") Long courseid){
		return testService.getTestByCourseId(courseid);
	}
	
	
}