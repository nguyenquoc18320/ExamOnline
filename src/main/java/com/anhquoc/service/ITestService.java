package com.anhquoc.service;

import java.util.List;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;

public interface ITestService {
	public List<TestEntity> getTestByCourse(Long courseid);
	public TestEntity getTesteByTestId(Long testid);
	public TestEntity updateTest(TestEntity test);
}
