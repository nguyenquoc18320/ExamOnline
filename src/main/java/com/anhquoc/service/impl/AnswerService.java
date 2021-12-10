package com.anhquoc.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anhquoc.entity.AnswerEntity;
import com.anhquoc.entity.CourseEntity;
import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.entity.TestEntity;
import com.anhquoc.entity.TestUserEntity;
import com.anhquoc.entity.UserEntity;
import com.anhquoc.entity.UserQuestionEmbeddable;
import com.anhquoc.repository.AnswerRepository;
import com.anhquoc.repository.QuestionRepository;
import com.anhquoc.repository.TestRepository;
import com.anhquoc.service.IAnswerService;

@Service
public class AnswerService implements IAnswerService {
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private TestUserService testUserService;

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

	/*
	 * answer a question in a test user is attending
	 */
	@Override
	public void answer(Long userid, Long questionid, String selection) {
		QuestionEntity question = questionRepository.getQuestion(questionid);

		if (question != null) {
			// check test is happening
			TestUserEntity testUser = testUserService.getContinuingTestUser(question.getTest().getId(), userid);

			if (testUser != null) {

				AnswerEntity answer = answerRepository.getAnswer(questionid, userid, testUser.getAttempt());
				if (answer != null) {
					answer.setSelection(selection);
				} else {
					// add question
					UserQuestionEmbeddable key = new UserQuestionEmbeddable();
					key.setQuestionId(questionid);
					key.setUserId(userid);
					key.setDate(Calendar.getInstance().getTime());

					answer = new AnswerEntity();
					answer.setId(key);
					answer.setAttemp(testUser.getAttempt());
					answer.setQuestion(question);
					answer.setSelection(selection);

					answer.setUser(testUser.getUser());

				}
				
				answerRepository.save(answer);
			}

		}
	}
	/*
	 * get all answers in a test user is attending
	 */
	@Override
	public List<AnswerEntity> getAnswersForAttendingTest(Long testid, Long userid) {
		List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
		//check test is happening
		TestUserEntity testUser = testUserService.getContinuingTestUser(testid, userid);
		
		if(testUser ==null) {
			return answers;
		}
		
		answers = answerRepository.getAnswers(testid, userid, testUser.getAttempt());
		
		return answers;
	}
	
	/*
	 * get answer for a test user done
	 */
	@Override
	public List<AnswerEntity> getAnswerForTest(Long userid, Long testid, int attempt) {
		List<AnswerEntity> answers = new ArrayList<AnswerEntity>();
		//check to make sure no test happening
		TestUserEntity testUser = testUserService.getContinuingTestUser(testid, userid);
		if(testUser!=null) {
			return answers;
		}
		
		//get answers
		answers = answerRepository.getAnswerForTest(userid, testid, attempt);
		return answers;		
	}
}
