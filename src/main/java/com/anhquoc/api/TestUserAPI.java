package com.anhquoc.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.api.pagination.OutPutPagination;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.service.impl.TestUserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@CrossOrigin
@RestController
public class TestUserAPI {
	@Autowired
	private TestUserService testUserService;

	/*
	 * get result test
	 */
	@GetMapping(value = "/test/best-result")
	public TestUserEntity getBestResult(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid) {
		TestUserEntity testUserEntity = testUserService.getBestResult(testid, userid);
		return testUserEntity;
	}

	/*
	 * get end-time when starting or attending test
	 */
	@GetMapping(value = "/test/attending-result")
	public TestUserEntity getResultTestAttending(@RequestParam("testid") Long testid,
			@RequestParam("userid") Long userid) {
		TestUserEntity testUser = testUserService.getContinuingTestUser(testid, userid);
		return testUser;
	}

	/*
	 * finish a test
	 */
	@PutMapping(value = "/test/finish")
	public TestUserEntity finishTest(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid) {
		TestUserEntity testUser = testUserService.finishTest(testid, userid);
		return testUser;
	}

//	/*
//	 * check continuing test
//	 */
//	@GetMapping(value = "test/check-continuing")
//	public boolean checkContinuingTest(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid) {
//		
//	}

	/*
	 * get results of test of user
	 */
	@GetMapping(value = "/test/results")
	public OutPutPagination<TestUserEntity> getResults(@RequestParam("testid") Long testid,
			@RequestParam("userid") Long userid, @RequestParam("page") int page, @RequestParam("limit") int limit) {

		OutPutPagination<TestUserEntity> output = new OutPutPagination<TestUserEntity>();
		output.setPage(page);
		Pageable pageable = PageRequest.of(page - 1, limit);
		List<TestUserEntity> result = testUserService.getResults(testid, userid, pageable);

		output.setEntityList(result);
		output.setTotalPage((int) Math.ceil((float) testUserService.getResults(testid, userid).size() / limit));
		return output;
	}
	
	@GetMapping(value = "/test-result/{testid}")
//	@JsonIgnoreProperties(ignoreUnknown = true)
	public List<TestUserEntity> getResultsByTestId(@PathVariable("testid") Long testid) {
		return testUserService.getResultsByTestId(testid);
	
	}
}
