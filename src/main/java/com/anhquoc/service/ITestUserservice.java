package com.anhquoc.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
	
	public List<TestUserEntity> getResultsForAuthor(Long testid, Long authorid, Pageable pageable);
	public List<TestUserEntity> getResultsForAuthor(Long testid, Long authorid);
	
	public List<TestUserEntity> getResultsByTestId(Long testid);
	
	/*
	 * get result exel file
	 */
	public ByteArrayInputStream getResultExcelFile(Long testid, Long authorid);
}
