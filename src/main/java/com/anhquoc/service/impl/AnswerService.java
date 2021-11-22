package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AnswerEntity;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.repository.AnswerRepository;
import com.anhquoc.repository.QuestionRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.service.IAnswerService;

@Service
public class AnswerService implements IAnswerService{
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private TestRepository testRepository;
	
	
//	public AnswerEntity createAnswer(AnswerEntity answer) {
//		
//		answer.setId(null);
//		QuestionEntity question = answer.getQuestion();
//		
//		if(questionRepository.findAllById(question.getId()).size()>0)
//		{
//			return null;
//		}
//
//		//to save
//		question.setId(null);
//		question = questionRepository.save(question);
//		
//		answer.setQuestion(question);
//		answer = answerRepository.save(answer);
//		return answer;
//	}
//	@Override
//	public List<AnswerEntity> getQuestionAnswerByTest(Long testid) {
//		TestEntity answer = testRepository.findOneById(testid);
//		if (answer == null) {
//			return null;
//		}
//
//		List<AnswerEntity> answers = answerRepository.findAllByTest(answer);
//		return answers;
//	}
}
