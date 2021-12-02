package com.anhquoc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
@Repository
public  interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{
	public QuestionEntity save(QuestionEntity question);
	public List<QuestionEntity> findAllById(Long questionid);
	public List<QuestionEntity> findAllByTest(TestEntity test);
	public QuestionEntity findOneById(Long id);
//	public QuestionEntity findOneByQuestionNumber(int questionnumber);
	@Query(value = "SELECT q FROM QuestionEntity q WHERE q.test = ?1 AND q.deleted=False ORDER BY q.questionnumber ASC")
	public List<QuestionEntity> findQuestionsByTest(TestEntity test);
	
	@Query(value = "SELECT q FROM QuestionEntity q WHERE q.id = ?1 AND q.deleted=False")
	public QuestionEntity getQuestion(Long questionid);
}
