package com.anhquoc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anhquoc.entity.TestUserEntity;

public interface ITestUserservice {

	public TestUserEntity getBestResult(Long testid, Long userid);
	public TestUserEntity getContinuingTestUser(Long testid, Long userid);
	public TestUserEntity startingTestUser(Long testid, Long userid) ;
	//finish test
	public TestUserEntity finishTest(Long testid, Long userid);
	public List<TestUserEntity> getResults(Long testid, Long userid, Pageable pageable);
	public List<TestUserEntity> getResults(Long testid, Long userid);
	public List<TestUserEntity> getResultsByTestId(Long testid);
}
