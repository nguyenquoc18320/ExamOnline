package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AnswerEntity;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.QuestionRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.service.IQuestionService;

@Service
public class QuestionService implements IQuestionService{
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private TestUserService testUserService;
	
	public QuestionEntity createQuestion(QuestionEntity question) {
		try {
			TestEntity test = question.getTest();

			// check whether the test exists
			test = testRepository.findOneById(test.getId());
			if (test == null) {
				return null;
			}

			question.setId(null);
			questionRepository.save(question);
			
			return question;
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}
	
	@Override
	public List<QuestionEntity> getQuestionByTestId(Long testid) {
		TestEntity test = testRepository.findOneById(testid);
		if (test == null) {
			return null;
		}
		List<QuestionEntity> questions = questionRepository.findQuestionsByTest(test);
		return questions;
	}
	
	@Override
	public QuestionEntity getQuestionByTestIDandQsNumber(Long testid, int numberquestion) {
		TestEntity test = testRepository.findOneById(testid);
		QuestionEntity question = null;
		List<QuestionEntity> questions = questionRepository.findAllByTest(test);
		for(int i = 0; i< questions.size(); i++) {
			if(questions.get(i).getQuestionnumber() == numberquestion) {
				question = questions.get(i);
			}
		}
		//QuestionEntity question = questionRepository.findOneByQuestionNumber(questionnumber);
		return question;
	}
	@Override
	public QuestionEntity updateQuestion(QuestionEntity question) {
		TestEntity currentTest = testRepository.findOneById(question.getTest().getId())	;
		QuestionEntity currentQuestion = getQuestionByTestIDandQsNumber(question.getTest().getId(), question.getQuestionnumber());
		
		if ( currentQuestion ==null || currentTest == null) {
			return null;
		}
		question.setId(currentQuestion.getId());
		question = questionRepository.save(question);
		return question;
	}
	
	@Override
	public QuestionEntity deleteQuestion(Long testid, int numberquestion) {
		QuestionEntity question = getQuestionByTestIDandQsNumber(testid,numberquestion);
		
		if ( question ==null) {
			return null;
		}
		question.setDeleted(true);
		questionRepository.save(question);
		return question;
	}

	/*
	 * return questions list for attending test
	 */
	@Override
	public List<QuestionEntity> getQuestionsInAttendingTest(Long testid, Long userid) {
		//check test, attending
		TestUserEntity testUser = testUserService.getContinuingTestUser(testid, userid);
		List<QuestionEntity> questions = new ArrayList<QuestionEntity>();
		if(testUser == null) {
			return questions;
		}
		
		TestEntity test = testRepository.getTest(testid, true);
		
		questions = questionRepository.findQuestionsByTest(test);
		
		return questions;
	}
}
