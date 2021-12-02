package com.anhquoc.service;


import java.util.List;

import com.anhquoc.entity.AnswerEntity;
//import com.anhquoc.service.impl.List;

public interface IAnswerService {
//	public AnswerEntity createAnswer(AnswerEntity answer);
//	public AnswerEntity createAnswer(AnswerEntity answer);
//	public List<AnswerEntity> getQuestionAnswerByTest(Long testid);
	
	public void answer(Long userid, Long questionid, String selection);
	public List<AnswerEntity> getAnswersForAttendingTest(Long testid, Long userid);
}
