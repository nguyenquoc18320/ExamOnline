package com.anhquoc.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anhquoc.entity.QuestionEntity;
import com.anhquoc.service.impl.QuestionService;

@CrossOrigin
@RestController
public class QuestionAPI {
		
	@Autowired
	private QuestionService questionService;

	@PostMapping("/question")
	public QuestionEntity createQuestion(@RequestBody QuestionEntity question) {
		question = questionService.createQuestion(question);
		return question;
	}
	@GetMapping("/test-question/{testid}")
	public List<QuestionEntity> getQuestionByTestId(@PathVariable("testid") Long testid){
		return questionService.getQuestionByTestId(testid);
	}
	@GetMapping("/question-exist/{testid}/{numberquestion}")
	public QuestionEntity getQuestionByTestIDandQsNumber(@PathVariable("testid") Long testid, @PathVariable("numberquestion") int numberquestion) {
		return questionService.getQuestionByTestIDandQsNumber(testid, numberquestion);
	}
	@PutMapping("/question")
	public QuestionEntity updateQuestion(@RequestBody QuestionEntity question) {
		return questionService.updateQuestion(question);
	}
	@PutMapping("/delete-question/{testid}/{numberquestion}")
	public QuestionEntity deleteQuestion(@PathVariable("testid") Long testid, @PathVariable("numberquestion") int numberquestion) {
		return questionService.deleteQuestion(testid, numberquestion);
	}
	
	//get questions in a test user is attending
	
	@GetMapping(value = "/question/attending-test")
	public List<QuestionEntity> getQuestionsInAttendingTest(@RequestParam("testid") Long testid, @RequestParam("userid") Long userid){
		List<QuestionEntity> questions = questionService.getQuestionsInAttendingTest(testid, userid);
		return questions;
	}
}
