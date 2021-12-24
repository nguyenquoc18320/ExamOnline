package com.anhquoc.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.entity.TestUserKey;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.AnswerRepository;
import com.anhquoc.repository.CourseRepository;
import com.anhquoc.repository.JoinCourseRepository;
import com.anhquoc.repository.QuestionRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.repository.TestUserRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.ITestUserservice;

@Service
public class TestUserService implements ITestUserservice {
	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private JoinCourseRepository joinCourseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestUserRepository testUserRepository;

	@Autowired
	private TestRepository testRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	/*
	 * get test result if not attending, return
	 */
	@Override
	public TestUserEntity getBestResult(Long testid, Long userid) {

		// check test and course block or not
		TestEntity test = testRepository.findOneById(testid);
		if (test == null || test.isDeleted() == true || test.getCourse().getBlocked() == true) {
			return null;
		}

		List<TestUserEntity> results = testUserRepository.getTestUser(testid, userid);
		TestUserEntity result;

		if (results.size() == 0) {
			return null;
		}

		// get max score, max attempt
		result = results.get(0);

		float maxScore = 0;
		int maxAttempt = 0;
		for (TestUserEntity t : results) {
			if (t.getScore() > maxScore)
				maxScore = t.getScore();

			if (t.getAttempt() > maxAttempt)
				maxAttempt = t.getAttempt();

		}

		result.setScore(maxScore);
		result.setAttempt(maxAttempt);
		return result;
	}

	/*
	 * get continuing TestUser if continuing test, testuser has score -1, and in
	 * time of doing test
	 */
	@Override
	public TestUserEntity getContinuingTestUser(Long testid, Long userid) {
		TestUserEntity testUser = testUserRepository.getMinusOneTestUser(testid, userid);

		// check time
		if (testUser == null)
			return null;
		if (testUser.getStartTime().after(Calendar.getInstance().getTime())
				|| testUser.getEndTime().before(Calendar.getInstance().getTime())) {
			// set result score
			this.finishTest(testid, userid);
			return null;
		}

		return testUser;
	}

	/*
	 * starting new test
	 */
	@Override
	public TestUserEntity startingTestUser(Long testid, Long userid) {
		// check continuing test
		TestUserEntity testUser = getContinuingTestUser(testid, userid);

		if (testUser != null) {
			return testUser;
		}

		TestEntity test = testRepository.getTest(testid, true);
		if (test == null)
			return null;

		// set check user join course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, test.getCourse().getId());
		if (course == null) {
			return null;
		}

		// check time for test
		Date now = Calendar.getInstance().getTime();
		if (test.getStart() != null && test.getStart().after(now)) {
			return null;
		}

		if (test.getEnd() != null && test.getEnd().before(now)) {
			return null;
		}

		// max attempts
		int maxAttempts = 0;
		try {
			maxAttempts = testUserRepository.getMaxAttempts(testid, userid);
		} catch (Exception ex) {

		}
		if (test.getAttemptnumber() != 0 && maxAttempts >= test.getAttemptnumber()) {
			return null;
		}

		// set start-time, end-time for test and score -1
		TestUserKey key = new TestUserKey();
		key.setTestid(testid);
		key.setUserid(userid);
		key.setStartTime(now);

		testUser = new TestUserEntity();
		testUser.setId(key);
		testUser.setScore(-1);
		testUser.setAttempt(maxAttempts + 1);

		testUser.setTest(test);
		UserEntity user = userRepository.findOneById(userid);
		testUser.setUser(user);

		// check end time
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(now);
		endTime.add(Calendar.MINUTE, test.getDuration());
		Date end = endTime.getTime();

		if (test.getEnd() != null && end.after(test.getEnd())) {
			end = test.getEnd();
		}

		testUser.setEndTime(end);

		testUserRepository.save(testUser);
		return testUser;
	}

	/*
	 * finish a test
	 */
	@Override
	public TestUserEntity finishTest(Long testid, Long userid) {
		// check test is happening
		TestUserEntity testUser = testUserRepository.getMinusOneTestUser(testid, userid);

		if (testUser == null) {
			return null;
		}

		// get number of correct answers
		int numberCorrectAnswers = 0;
		try {
			numberCorrectAnswers = answerRepository.getNumberCorrectAnswers(testid, userid, testUser.getAttempt());
		} catch (Exception ex) {

		}

		int numQuestions = questionRepository.findAllByTest(testUser.getTest()).size();
		float score = (float) Math.floor((float) numberCorrectAnswers / numQuestions * 1000) / 100;
		testUser.setScore(score);

		// set endtime
		Date now = (Date) Calendar.getInstance().getTime();
		if (now.before(testUser.getEndTime())) {
			testUser.setEndTime(Calendar.getInstance().getTime());
		}

		testUserRepository.save(testUser);

		return testUser;
	}

	/*
	 * get results
	 */
	@Override
	public List<TestUserEntity> getResults(Long testid, Long userid, Pageable pageable) {
		// check for finish test
		getContinuingTestUser(testid, userid);

		List<TestUserEntity> results = testUserRepository.getResults(testid, userid, pageable);
		return results;
	}

	@Override
	public List<TestUserEntity> getResults(Long testid, Long userid) {
		List<TestUserEntity> results = testUserRepository.getTestUser(testid, userid);
		return results;
	}

	/*
	 * get results for test of author
	 */
	@Override
	public List<TestUserEntity> getResultsForAuthor(Long testid, Long authorid, Pageable pageable) {
		List<Long> userid = testUserRepository.getUserIdAttendingTest(testid, pageable);

		List<TestUserEntity> testUsers = new ArrayList<TestUserEntity>();
		for (Long id : userid) {
			// finish test (by using continuing test
			getContinuingTestUser(testid, id);
			testUsers.add(this.getBestResult(testid, id));
		}

		return testUsers;
	}

	@Override
	public List<TestUserEntity> getResultsForAuthor(Long testid, Long authorid) {
		List<Long> userid = testUserRepository.getUserIdAttendingTest(testid);

		List<TestUserEntity> testUsers = new ArrayList<TestUserEntity>();
		for (Long id : userid) {
			// finish test (by using continuing test
			getContinuingTestUser(testid, id);
			testUsers.add(this.getBestResult(testid, id));
		}

		return testUsers;
	}

	@Override
	public List<TestUserEntity> getResultsByTestId(Long testid) {
		List<TestUserEntity> results = testUserRepository.getTestResultbyTestid(testid);
		return results;
	}

	/*
	 * get result excel file
	 */
	@Override
	public ByteArrayInputStream getResultExcelFile(Long testid, Long authorid) {
		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("sheet1");

		List<TestUserEntity> results = this.getResultsForAuthor(testid, authorid);
		
		TestEntity test = testRepository.findOneById(testid);

		try {
			HSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("Course: " + test.getCourse().getName() + " - Test: " + test.getName());
			
			for (int i = 1; i < results.size()+1; i++) {
				row = sheet.createRow(i);
				row.createCell(0).setCellValue("" + results.get(i-1).getUser().getName());
				row.createCell(1).setCellValue("" + results.get(i-1).getUser().getEmail());
				row.createCell(2).setCellValue("" + results.get(i-1).getScore());
			}


			ByteArrayOutputStream output =  new ByteArrayOutputStream();
			workbook.write(output);

			// closing the workbook
			workbook.close();

			return new ByteArrayInputStream(output.toByteArray());
			
		} catch (Exception ex) {
			return null;
		}
	}
}
