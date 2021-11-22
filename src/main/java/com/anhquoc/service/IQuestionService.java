package com.anhquoc.service;

import java.util.List;

import com.anhquoc.entity.QuestionEntity;

public interface IQuestionService {
//	public  List<QuestionEntity> findById(Long questionid);
	public List<QuestionEntity> getQuestionByTestId(Long testid);
}
