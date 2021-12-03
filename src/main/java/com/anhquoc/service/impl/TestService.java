package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.CourseRepository;
import com.anhquoc.repository.JoinCourseRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.repository.TestUserRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.ITestService;

@Service
public class TestService implements ITestService{
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private JoinCourseRepository joinCourseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private TestUserRepository testUserRepository;
	
	@Autowired
	private TestUserService testUserService;
	
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

	/*
	 * get test
	 */
	@Override
	public TestEntity getTestForAttend(Long testid, Long userid) {
		TestEntity test = testRepository.findOneById(testid);
		//check test 
		if(test==null || test.isStatus()==false || test.isDeleted()==true) {
			return null;
		}
		
		CourseEntity course = test.getCourse();
		//check course
		if(course.getBlocked()==true || course.getDeleted()==true)
			return null;
		
		//check whether user joined course or not
		if(joinCourseRepository.getCourseByUser(userid, course.getId()) == null) {
			return null;
		}
		
		return test;
	}
	
	/*
	 * get tests in a course user joined
	 */
	@Override
	public List<TestEntity> getTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageable) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 
		
		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
		tests = testRepository.getTestsByCourse(course, true, pageable);
		
		return tests;
	}
	
	@Override
	public List<TestEntity> getTestsOfJoinedCourse(Long userid, Long courseid) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 
		
		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
		tests = testRepository.getTestsByCourse(course, true);
		
		return tests;
	}
	
	/*
	 * get tests which not done and is in join course
	 */
	@Override
	public List<TestEntity> getNotDoneTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageable) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 

		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
//		UserEntity user = userRepository.findOneById(userid);
		tests = testRepository.getNotDoneTestsByCourse(course, true,userid, pageable);
		
		return tests;
	}
	
	@Override
	public List<TestEntity> getNotDoneTestsOfJoinedCourse(Long userid, Long courseid) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 
		
		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
		tests = testRepository.getNotDoneTestsByCourse(course, true, userid);
		
		return tests;
	}
	
	
	/*
	 * get done tests
	 */
	@Override
	public List<TestEntity> getDoneTestsOfJoinedCourse(Long userid, Long courseid, Pageable pageable) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 

		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
		tests = testRepository.getDoneTestsByCourse(course, true,userid, pageable);
		
		return tests;
	}

	@Override
	public List<TestEntity> getDoneTestsOfJoinedCourse(Long userid, Long courseid) {
		List<TestEntity>  tests = new ArrayList<TestEntity>(); 
		
		//check user joined course
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course==null) {
			return tests;
		}
		
		tests = testRepository.getDoneTestsByCourse(course, true, userid);
		
		return tests;
	}

	/*
	 * get test user is attending
	 * 
	 * when starting test, set start-time and end-time for test, but score set -1
	 * 
	 * if while doing test but it outs, you will get test with score -1 but still have time to do test. Otherwise, set real score
	 */

	@Override
	public TestEntity getTestUserAttending(Long testid, Long userid) {
		//check for continuing test
		TestUserEntity testUser = testUserService.getContinuingTestUser(testid, userid);
		
		TestEntity test = testRepository.getTest(testid, true);
		if(testUser != null) {
			return test;
		}
		
		//if starting new test
		testUser = testUserService.startingTestUser(testid, userid);
		//if returning null
		if(testUser != null) {
			return test;
		}
		
		return null;
	}
	
	@Override
	public TestEntity statusTest(Long courseid, Long testid) {
		CourseEntity course = courseRepository.findOneById(courseid);		
		if ( course ==null) {
			return null;
		}
		TestEntity test = testRepository.findOneById(testid);
		
		
		if(test.isStatus()== true) {
			test.setStatus(false);
		}else {
			test.setStatus(true);
		}
		testRepository.save(test);
		return test;
	}
	
}
