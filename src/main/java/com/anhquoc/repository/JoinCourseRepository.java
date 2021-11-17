package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.JoinCourse;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.entity.UserJoinCourseKey;

@Repository
public interface JoinCourseRepository extends JpaRepository<JoinCourse, UserJoinCourseKey>{
	List<JoinCourse> findAllByIdCourseidAndStatus(Long courseid, boolean status, Pageable pageable);
	List<JoinCourse> findAllByIdCourseidAndStatus(Long courseid, boolean status);
	
	@Query(value = "SELECT j.user FROM JoinCourse j WHERE j.id.courseid = ?1 AND j.status=True")
	List<UserEntity> getUsersByCourseid(Long courseid, Pageable pageable);
	
	
	//get user by name (like)
	@Query(value = "SELECT j.user FROM JoinCourse j WHERE j.id.courseid = ?1 AND j.user.name LIKE %?2% AND j.status=True")
	List<UserEntity> getUsersByCourseidAndName(Long courseid, String participantName, Pageable pageable);
	
	@Query(value = "SELECT j.user FROM JoinCourse j WHERE j.id.courseid = ?1 AND j.user.name LIKE %?2% AND j.status=True")
	List<UserEntity> getUsersByCourseidAndName(Long courseid, String participantName);
}
