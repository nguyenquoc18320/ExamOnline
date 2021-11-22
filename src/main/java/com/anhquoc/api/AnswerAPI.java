package com.anhquoc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.entity.AnswerEntity;
//import com.anhquoc.service.impl.AnswerService;
import com.anhquoc.service.impl.AnswerService;


@CrossOrigin
@RestController
public class AnswerAPI {
	@Autowired
	private AnswerService answerService;
	
//	@PostMapping("/question-answer")
//	public AnswerEntity createAnswer(@RequestBody AnswerEntity answer) {
//		answer = answerService.createAnswer(answer);
//		return answer;
//	}
//	@GetMapping("/question-answer/{testid}")
//	public List<AnswerEntity> getQuestionAnswerByTest(@PathVariable("testid") Long testid){
//		return answerService.getQuestionAnswerByTest(testid);
//	}
}
