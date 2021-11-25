package com.anhquoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.repository.CourseRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.service.ITestService;

@Service
public class TestService implements ITestService{
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	public TestEntity createTest(TestEntity test) {
		try {
			CourseEntity course = test.getCourse();

			// check whether the course exists
			course = courseRepository.findOneById(course.getId());
			if (course == null) {
				return null;
			}

			test.setId(null);
//		course.setStatus(false);
			testRepository.save(test);
			return test;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	@Override
	public List<TestEntity> getTestByCourse(Long courseid) {
		CourseEntity course = courseRepository.findOneById(courseid);
		if (course == null) {
			return null;
		}
		//.findAllByCourse(course);
		List<TestEntity> tests = testRepository.findTestsByCourse(course);
		return tests;
	}
	
	@Override
	public TestEntity getTesteByTestId(Long testid) {
		TestEntity test = testRepository.findOneById(testid);
		if (test == null) {
			return null;
		}
		return test;
		
	}
	@Override
	public TestEntity updateTest(TestEntity test) {
		CourseEntity course = test.getCourse();
		CourseEntity courseupate = courseRepository.findOneById(course.getId());
		if (courseupate == null) {
			return null;
		}
		//update
//		currentTest.setName(test.getName());
//		currentTest.setDescription(test.getDescription());
//		currentTest.setDuration(test.getDuration());
//		currentTest.setAttemptnumber(test.getAttemptnumber());
		test = testRepository.save(test);
		return test;
	}
	@Override
	public TestEntity deleteTest(Long courseid, Long testid) {
		CourseEntity course = courseRepository.findOneById(courseid);		
		if ( course ==null) {
			return null;
		}
		TestEntity test = testRepository.findOneById(testid);
		
		test.setDeleted(true);
		testRepository.save(test);
		return test;
	}
	
}
