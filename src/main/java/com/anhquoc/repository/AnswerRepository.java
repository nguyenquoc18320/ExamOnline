package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.AnswerEntity;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>{
//	public AnswerEntity findOneById(Long id);
	@Query("SELECT a FROM AnswerEntity a "
			+ " WHERE a.id.questionId=?1 AND a.id.userId=?2 AND a.attemp=?3")
	public AnswerEntity getAnswer(Long questionid, Long userid, int attempt);
	
	/*
	 * get number of correct answers
	 */
	@Query(value = "SELECT COUNT(a) FROM  AnswerEntity a INNER JOIN QuestionEntity q ON a.id.questionId = q.id"
			+ " WHERE q.test.id = ?1 AND a.id.userId = ?2 AND a.attemp = ?3 AND a.selection = q.correctanswer  ")
	public int getNumberCorrectAnswers(Long testid, Long userid, int attempt);
	
//	public AnswerEntity findOneById(Long id);
	@Query("SELECT a FROM AnswerEntity a "
			+ " WHERE a.id.userId=?2 AND a.attemp=?3 "
			+ " AND a.id.questionId IN (SELECT q.id FROM QuestionEntity q WHERE q.test.id = ?1)")
	public List<AnswerEntity> getAnswers(Long testid, Long userid, int attempt);
}
