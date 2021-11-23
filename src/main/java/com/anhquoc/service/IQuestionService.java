package com.anhquoc.service;

import java.util.List;

import com.anhquoc.entity.QuestionEntity;

public interface IQuestionService {
//	public  List<QuestionEntity> findById(Long questionid);
	public List<QuestionEntity> getQuestionByTestId(Long testid);
	public QuestionEntity getQuestionByTestIDandQsNumber(Long testid, int numberquestion) ;
//	public QuestionEntity updateQuestion(QuestionEntity question, Long testid);
}
