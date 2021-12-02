package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.entity.TestUserKey;

@Repository
public interface TestUserRepository extends JpaRepository<TestUserEntity, TestUserKey>{

	//get test result, if attempt =0, user was removed in the test
	@Query(value = "SELECT t FROM TestUserEntity t "
			+ " WHERE t.id.testid=?1 AND t.id.userid=?2 AND t.attempt>0 AND t.score!=-1")
	public List<TestUserEntity> getTestUser(Long testid, Long userid);
	
	//get continuing test with score = -1,
	@Query(value = "SELECT t FROM TestUserEntity t"
			+ "	WHERE t.id.testid=?1 AND t.id.userid=?2 AND t.attempt!=0 AND t.score=-1")
	public TestUserEntity getMinusOneTestUser(Long testid, Long userid);
	
	//get max attempts,
	@Query(value = "SELECT MAX(t.attempt) FROM TestUserEntity t"
			+ "	WHERE t.id.testid=?1 AND t.id.userid=?2 AND t.attempt>0 AND t.score!=-1")
	public int getMaxAttempts(Long testid, Long userid);
	
	//get result,
	@Query(value = "SELECT tu FROM TestUserEntity tu"
			+ "	WHERE tu.id.testid=?1 AND tu.id.userid=?2 AND tu.attempt>0 AND tu.score!=-1 "
			+ " ORDER BY tu.attempt DESC ")
	public List<TestUserEntity> getResults(Long testid, Long userid, Pageable pageble);
}
