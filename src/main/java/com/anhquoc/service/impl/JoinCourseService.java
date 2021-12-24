package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.JoinCourse;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.entity.UserJoinCourseKey;
import com.anhquoc.repository.AnswerRepository;
import com.anhquoc.repository.CourseRepository;
import com.anhquoc.repository.JoinCourseRepository;
import com.anhquoc.repository.TestUserRepository;
import com.anhquoc.repository.UserRepository;
import com.anhquoc.service.IJoinCourseService;

@Service
public class JoinCourseService implements IJoinCourseService {
	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JoinCourseRepository joinCourseRepository;
	
	@Autowired
	TestUserRepository testUserRepository;
	
	@Autowired 
	AnswerRepository answerRepository;

	/*
	 * users join a course
	 * userid is owner
	 */
	@Override
	public String joinCourse(Long courseid, Long ownerid, List<String> emailList) {
		
		// Check courseExist
		CourseEntity course = courseRepository.getCourseByBlocked(courseid, false);

		// don't get the course
		if (course == null) {
			return "The course doesn't exist. It can be blocked or deleted";
		}

		// check permission
		// if course is private, only course owner can add participants
		if (course.getStatus() == false) {
			if (course.getUser().getId() != ownerid) {
				return "The course is private!";
			}
		}

		// emails not existing
		String errorEmails = "";

		for (String email : emailList) {
			// get user by email
			UserEntity user = userRepository.findOneByEmail(email);
			
			//user joining course must not be admin or owner
			if (user == null || user.getId()==ownerid || user.getRole().getId()==1) {
				errorEmails += " " + email + ";";
			} else {
				JoinCourse joinCourse = new JoinCourse(user, course, true);
				joinCourseRepository.save(joinCourse);
			}
		}

		if (!errorEmails.isEmpty()) {
			return "Not find: " + errorEmails + ". Other emails is added";
		}

		return "Successfully";
	}

	/*
	 * get users who joined a course, with pagination or not ownerid to check
	 * permission
	 * Can search participants with Name
	 */
	@Override
	public List<UserEntity> getUsersJoinedCourse(Long ownerid, Long courseid, String participantName,  Pageable pageable) {
		List<UserEntity> userList = new ArrayList<UserEntity>();

		// check the course:
		CourseEntity course = courseRepository.findOneByIdAndBlockedAndDeleted(courseid, false, false);

		// check owner course
		UserEntity user = userRepository.getOne(ownerid);
		if (user.getRole().getId() != 1 && user.getId() != ownerid) {
			return userList;
		}

		if (course != null) {
//			List<JoinCourse> joinCourse = joinCourseRepository.findUsersByCourseidAndStatus(courseid, pageable);
//			for (JoinCourse j : joinCourse) {
//				userList.add(j.getUser());
//			}
			userList = joinCourseRepository.getUsersByCourseidAndName(courseid, participantName, pageable);
		}
		return userList;
	}

	//get without pagination
	@Override
	public List<UserEntity> getUsersJoinedCourse(Long ownerid, Long courseid, String participantName) {
		List<UserEntity> userList = new ArrayList<UserEntity>();

		// check the course:
		CourseEntity course = courseRepository.findOneByIdAndBlockedAndDeleted(courseid, false, false);

		// check owner course
		UserEntity user = userRepository.getOne(ownerid);
		if (user.getRole().getId() != 1 && user.getId() != ownerid) {
			return userList;
		}

		if (course != null) {
			userList = joinCourseRepository.getUsersByCourseidAndName(courseid, participantName);
		}

		return userList;
	}

	/*
	 * to DELETE a user in a course, changing status to false
	 */
	@Override
	public String deleteUser(Long ownerid, Long userid, Long couresid) {
		UserEntity user = userRepository.getOne(userid);
		CourseEntity course = courseRepository.getCourseByBlocked(couresid, false);
		
		//user must not be owner or admin
		if(ownerid == userid && user.getRole().getId()==1) {
			return "Cann't delete the user";
		}
		
		//check owner 
		if(course.getUser().getId() != ownerid) {
			return "Cann't delete the user";
		}
		
		//set all attempts to 0
		testUserRepository.setAttemptsToZero(userid, couresid);
		//set all attempts in answer to zeros
		answerRepository.setAttemptsToZero(userid, couresid);
		
		JoinCourse joinCourse = new JoinCourse(user, course, false);
		joinCourseRepository.save(joinCourse);
		return user.getName() +" is deleted!";
	}

	/*
	 * get cours list a user joined
	 */
	@Override
	public List<CourseEntity> getCoursesByUser(Long userid, Pageable pageable) {
		List<CourseEntity> courses = new ArrayList<CourseEntity>();
		courses = joinCourseRepository.getCoursesByUser(userid, pageable);
		return courses;
	}

	@Override
	public List<CourseEntity> getCoursesByUser(Long userid) {
		List<CourseEntity> courses = new ArrayList<CourseEntity>();
		courses = joinCourseRepository.getCoursesByUser(userid);
		return courses;
	}	
	
	
	/*
	 * a user join public course
	 */
	@Override
	public boolean joinPublicCourse(Long userid, Long courseid) {
		//check whether the course is public or not
		UserEntity user = userRepository.findOneById(userid);
		
		CourseEntity course= courseRepository.findOneByIdAndBlockedAndDeleted(courseid, false, false);
		
		if(course.getStatus() == false)
			return false;
		
		//check author
		if(course.getUser().getId() == userid) {
			return false;
		}

		UserJoinCourseKey key = new UserJoinCourseKey();
		key.setCourseid(courseid);
		key.setUserid(userid);
		
		JoinCourse join = new JoinCourse();
		join.setId(key);
		join.setCourse(course);
		join.setUser(user);
		join.setStatus(true);
		
		joinCourseRepository.save(join);
		return true;
	}
	
	/*
	 * check joint course
	 */
	@Override
	public boolean checkJoinCourse(Long userid, Long courseid) {
		CourseEntity course = joinCourseRepository.getCourseByUser(userid, courseid);
		if(course!=null)
			return true;
		return false;
	}
	
	/*
	 * get number of users join course
	 */
	@Override
	public int numberUsersJoiningCourse(Long courseid) {
		try {
			return joinCourseRepository.numberUsersJoiningCourse(courseid);
		}catch(Exception ex) {
			return 0;
		}
	}
	
}
