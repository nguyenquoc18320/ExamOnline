package com.anhquoc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AnswerEntity;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
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
		List<QuestionEntity> questions = questionRepository.findAllByTest(test);
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
//	@Override
//	public QuestionEntity updateQuestion(QuestionEntity question, Long testid) {
//		QuestionEntity currentQuestion = questionRepository.findOneById(question.getId());
//		return null;
//	}
}
