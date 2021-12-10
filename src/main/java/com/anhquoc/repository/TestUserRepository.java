package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	//get userid attending test
	@Query(value = "SELECT DISTINCT tu.id.userid FROM TestUserEntity tu "
			+ " WHERE tu.id.testid=?1 AND tu.attempt>0 AND tu.score !=-1 ")
	public List<Long> getUserIdAttendingTest(Long testid, Pageable pageable); 
	
	@Query(value = "SELECT DISTINCT tu.id.userid FROM TestUserEntity tu "
			+ " WHERE tu.id.testid=?1 AND tu.attempt>0 AND tu.score !=-1 ")
	public List<Long> getUserIdAttendingTest(Long testid); 
	//get test result
	@Query(value = "SELECT t FROM TestUserEntity t "
			+ " WHERE t.id.testid=?1 AND t.attempt>0 AND t.score!=-1")
	public List<TestUserEntity> getTestResultbyTestid(Long testid);
	
	//set all attempt to zero
//	@Transactional
	@Modifying
	@Query(value = "UPDATE TestUserEntity t SET t.attempt=0 WHERE t.id.userid=?1 "
			+ " AND t.id.testid IN (SELECT c.id FROM TestEntity c WHERE c.course.id = ?2 )")
	@Transactional(rollbackFor=Exception.class)
	public void setAttemptsToZero(Long userid, Long courseid);
	
	//statistic score, min< and <=max
	@Query(value="SELECT COUNT(*) "
			+ " FROM test_user t inner join (SELECT userid, MAX(score) as score FROM test_user WHERE testid= :testid GROUP by userid ) Q ON t.userid = Q.userid"
			+ " WHERE t.testid=:testid AND t.score=Q.score AND t.attempt>0 AND t.score>:min_score AND t.score<=:max_score",
			nativeQuery = true)
	public int getStatisticForCourse(@Param("testid") Long testid, @Param("min_score") float minScore, @Param("max_score") float maxScore);
}
