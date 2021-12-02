package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.UserEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long>{
	public TestEntity findOneById(Long id);
	public List<TestEntity> findAllByCourse(CourseEntity course);
	// list course by user
	@Query(value = "SELECT c FROM TestEntity c WHERE c.course = ?1 AND c.deleted=False")
	public List<TestEntity> findTestsByCourse(CourseEntity course);

	// list tests by course
	@Query(value = "SELECT t FROM TestEntity t WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False")
	public List<TestEntity> getTestsByCourse(CourseEntity course, boolean status, Pageable pageable);
	
	// list tests by course
	@Query(value = "SELECT t FROM TestEntity t WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False")
	public List<TestEntity> getTestsByCourse(CourseEntity course, boolean status);
	
	//get tests user not done
	@Query(value = "SELECT DISTINCT t FROM TestEntity t "
			+ " WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False "
			+ " AND t.id NOT IN (SELECT tu.id.testid FROM TestUserEntity tu WHERE tu.id.userid = ?3)")
	public List<TestEntity> getNotDoneTestsByCourse(CourseEntity course, boolean status,  Long userid, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT t FROM TestEntity t "
			+ " WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False "
			+ " AND t.id NOT IN (SELECT tu.id.testid FROM TestUserEntity tu WHERE tu.id.userid = ?3)")
	public List<TestEntity> getNotDoneTestsByCourse(CourseEntity course, boolean status,  Long userid);
	
	//get tests user done
	@Query(value = "SELECT DISTINCT t FROM TestEntity t "
			+ " WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False "
			+ " AND t.id IN (SELECT tu.id.testid FROM TestUserEntity tu WHERE tu.id.userid = ?3)")
	public List<TestEntity> getDoneTestsByCourse(CourseEntity course, boolean status,  Long userid, Pageable pageable);
	
	@Query(value = "SELECT DISTINCT t FROM TestEntity t "
			+ " WHERE t.course = ?1 AND t.status=?2 AND t.deleted=False "
			+ " AND t.id IN (SELECT tu.id.testid FROM TestUserEntity tu WHERE tu.id.userid = ?3)")
	public List<TestEntity> getDoneTestsByCourse(CourseEntity course, boolean status,  Long userid);

	//get test
	@Query(value = "SELECT t FROM TestEntity t "
			+ " WHERE t.id = ?1 AND t.status = ?2 AND t.deleted=False")
	public TestEntity getTest(Long testid, Boolean status);
}
