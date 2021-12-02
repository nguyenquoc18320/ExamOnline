package com.anhquoc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;

public interface ITestService {
	public List<TestEntity> getTestByCourse(Long courseid);

	public TestEntity getTesteByTestId(Long testid);

	public TestEntity updateTest(TestEntity test);

	public TestEntity deleteTest(Long courseid, Long testid);

	// get tests user joined
	public List<TestEntity> getTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageble);

	public List<TestEntity> getTestsOfJoinedCourse(Long userid, Long courseid);

	// get tests which are not done and user joined
	public List<TestEntity> getNotDoneTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageable);

	public List<TestEntity> getNotDoneTestsOfJoinedCourse(Long userid, Long courseid);

	// get test
	public TestEntity getTestForAttend(Long testid, Long userid);

	// get tests which are done and user joined
	public List<TestEntity> getDoneTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageable);

	public List<TestEntity> getDoneTestsOfJoinedCourse(Long userid, Long courseid);

	// get test user is attending
	public TestEntity getTestUserAttending(Long testid, Long userid);
}
