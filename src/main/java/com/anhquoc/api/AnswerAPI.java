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

	/*
	 * update answer in a test user is attending
	 */
	@PutMapping(value = "/answer")
	public void answer(@RequestParam("userid") Long userid, @RequestParam("questionid") Long questionid,
			@RequestParam("selection") String selection) {
		answerService.answer(userid, questionid, selection);
	}

	/*
	 * get answers in test user is attending
	 */
	@GetMapping(value = "/answer/attending-test")
	public String[][] getAnswersForAttendingTest(@RequestParam("testid") Long testid,
			@RequestParam("userid") Long userid) {
		List<AnswerEntity> answers = answerService.getAnswersForAttendingTest(testid, userid);
		String[][] ans = new String[answers.size()][2];

		for (int i = 0; i < answers.size(); i++) {
			ans[i][0] = answers.get(i).getId().getQuestionId().toString();
			ans[i][1] = answers.get(i).getSelection();
		}
		return ans;
	}

	/*
	 * get answers of user for a test
	 */
	@GetMapping(value = "test/answer")
	public List<AnswerEntity> getAnswersForTest(@RequestParam("userid") Long userid, @RequestParam("testid") Long testid,
			@RequestParam("attempt") int attempt) {
		List<AnswerEntity> answerList = answerService.getAnswerForTest(userid, testid, attempt);
		return answerList;
	}
}
