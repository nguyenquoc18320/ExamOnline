package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.UserEntity;


@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{
	public List<CourseEntity> findAllByUserAndDeleted(UserEntity user, boolean deleted, Pageable pageable);
	public List<CourseEntity> findAllByUserAndDeleted(UserEntity user,  boolean deleted);
	public CourseEntity findOneByIdAndBlockedAndDeleted(Long id, boolean blocked, boolean deleted);
	public CourseEntity findOneByUserAndNameAndDeleted(UserEntity user, String name, boolean deleted);
//	public List<CourseEntity> findByUserAndNameContaining(UserEntity user, String courseName, Pageable pageable);
	
	/*
	 * get course with user
	 */
	//course name
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.deleted=False ")
	public List<CourseEntity> findCoursesByUser(UserEntity user, String courseName, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.deleted=False")
	public List<CourseEntity> findCoursesByUser(UserEntity user, String courseName);
	
	//coursename, status, blocked
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.status = ?3 AND c.blocked= ?4 AND c.deleted=False ")
	public List<CourseEntity> findCoursesByUser(UserEntity user, String courseName, boolean status, boolean blocked, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.status = ?3 AND c.blocked= ?4 AND c.deleted=False")
	public List<CourseEntity> findCoursesByUser(UserEntity user, String courseName, boolean status, boolean blocked);
	
	
	//coursename, status
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.status = ?3 AND c.deleted=False ")
	public List<CourseEntity> findCoursesByUserWithStatus(UserEntity user, String courseName, boolean status, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.status = ?3  AND c.deleted=False")
	public List<CourseEntity> findCoursesByUserWithStatus(UserEntity user, String courseName, boolean status);
	
	//coursename, blocked
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.blocked= ?3 AND c.deleted=False ")
	public List<CourseEntity> findCoursesByUserWithBlocked(UserEntity user, String courseName, boolean blocked, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.name LIKE %?2% AND c.blocked= ?3 AND c.deleted=False")
	public List<CourseEntity> findCoursesByUserWithBlocked(UserEntity user, String courseName, boolean blocked);
	
	// list course by user
	@Query(value = "SELECT c FROM CourseEntity c WHERE c.user = ?1 AND c.deleted=False")
	public List<CourseEntity> findCoursesByUser(UserEntity user);
	/*
	 * search courses with conditions: course name, author's name, status, blocked, pageable
	 */
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.status = ?3 AND c.blocked = ?4 AND c.deleted=False")
	public List<CourseEntity> findCourse(String courseName, String authorName, boolean status, boolean blocked, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.status = ?3 AND c.blocked = ?4 AND c.deleted=False")
	public List<CourseEntity> findCourse(String courseName, String authorName, boolean status, boolean blocked);
	
	//without blocked
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.status = ?3 AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthorAndStatus(String courseName, String authorName, boolean status, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.status = ?3 AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthorAndStatus(String courseName, String authorName, boolean status);
	
	//without status
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.blocked = ?3 AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthorAndBlocked(String courseName, String authorName, boolean blocked,Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.blocked = ?3 AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthorAndBlocked(String courseName, String authorName, boolean blocked);
	
	//without status and blocked
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthor(String courseName, String authorName, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.name LIKE %?1% AND c.user.name LIKE %?2% AND c.deleted=False")
	public List<CourseEntity> findCourseByNameAndAuthor(String courseName, String authorName);
	
	//get course for user, which is not deleted or blocked
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.id =?1 AND c.blocked=?2 AND c.deleted=False AND c.deleted=False")
	public CourseEntity getCourseByBlocked(Long courseid, boolean blockedStatus);
	
	public List<CourseEntity> findAllByUser(UserEntity user, Pageable pageable);
	public List<CourseEntity> findAllByUser(UserEntity user);
	public CourseEntity findOneById(Long id);
	
	/*
	 * get public courses
	 */
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.user != ?1 AND c.blocked = False AND c.deleted = False AND c.name LIKE %?2% AND c.status = True ")
	public List<CourseEntity> getPublicCourses(UserEntity user, String courseName, Pageable pageable);
	
	@Query(value = "SELECT c FROM CourseEntity c "
			+ " WHERE c.user != ?1 AND c.blocked = False AND c.deleted = False AND c.name LIKE %?2% AND c.status = True ")
	public List<CourseEntity> getPublicCourses(UserEntity user, String courseName);
}
