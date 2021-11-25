package com.anhquoc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
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
}